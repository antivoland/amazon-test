import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
public class ParallelMyList<VALUE> implements MyList<VALUE> {
    public static Factory factory(
            int chunkSize,
            Factory sublistFactory,
            ExecutorService executorService) {

        return new Factory() {
            @Override
            public <VALUE> MyList<VALUE> listFrom(VALUE[] values) {
                return new ParallelMyList<>(
                        chunkSize,
                        executorService,
                        sublistFactory,
                        values);
            }
        };
    }

    private final int chunkSize;
    private final ExecutorService executorService;
    private final MyList.Factory sublistFactory;
    private final Object[] sublists;

    private ParallelMyList(
            int chunkSize,
            ExecutorService executorService,
            Factory sublistFactory,
            VALUE[] values) {

        Queue<MyList<VALUE>> sublists = new Queue<>();
        Chunker.Builder<VALUE> builder = new Chunker.Builder<VALUE>()
                .chunkSize(chunkSize)
                .chunkConsumer(sublists::enqueue)
                .factory(sublistFactory);

        try (Chunker<VALUE> chunker = builder.build()) {
            for (VALUE value : values) {
                chunker.push(value);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        this.chunkSize = chunkSize;
        this.executorService = executorService;
        this.sublistFactory = sublistFactory;
        this.sublists = sublists.dequeueAll();
    }

    private ParallelMyList(
            int chunkSize,
            ExecutorService executorService,
            Factory sublistFactory,
            MyList[] sublists) {

        this.chunkSize = chunkSize;
        this.executorService = executorService;
        this.sublistFactory = sublistFactory;
        this.sublists = sublists;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MyList<VALUE> reverse() {
        Stack<Future<MyList>> futures = new Stack<>();
        for (int i = 0; i < sublists.length; ++i) {
            MyList sublist = (MyList) sublists[i];
            Callable<MyList> task = sublist::reverse;
            futures.push(executorService.submit(task));
        }

        MyList[] reversedSublists = new MyList[sublists.length];
        int i = 0;
        while (!futures.empty()) {
            try {
                reversedSublists[i] = futures.pop().get();
                ++i;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return new ParallelMyList<>(
                chunkSize,
                executorService,
                sublistFactory,
                reversedSublists);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        Queue<Future<MyList<VALUE>>> futures = new Queue<>();
        for (int i = 0; i < sublists.length; ++i) {
            MyList sublist = (MyList) sublists[i];
            Callable<MyList<VALUE>> task = () -> sublist.filter(filter);
            futures.enqueue(executorService.submit(task));
        }

        MyList[] filteredSublists = new MyList[sublists.length];
        int i = 0;
        while (!futures.empty()) {
            try {
                filteredSublists[i] = futures.dequeue().get();
                ++i;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return new ParallelMyList<>(
                chunkSize,
                executorService,
                sublistFactory,
                filteredSublists);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        Queue<Future<MyList<VALUE>>> futures = new Queue<>();
        for (int i = 0; i < sublists.length; ++i) {
            MyList sublist = (MyList) sublists[i];
            Callable<MyList<VALUE>> task = () -> sublist.map(mapper);
            futures.enqueue(executorService.submit(task));
        }

        MyList[] filteredSublists = new MyList[sublists.length];
        int i = 0;
        while (!futures.empty()) {
            try {
                filteredSublists[i] = futures.dequeue().get();
                ++i;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return new ParallelMyList<>(
                chunkSize,
                executorService,
                sublistFactory,
                filteredSublists);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        FOLDED folded = identity;
        for (Object sublist : sublists) {
            folded = (FOLDED) ((MyList) sublist).foldLeft(folded, reducer);
        }
        return folded;
    }

    @Override
    @SuppressWarnings("unchecked")
    public VALUE[] toArray() {
        return (VALUE[]) Arrays.stream(sublists)
                .map(sublist -> (MyList) sublist)
                .flatMap(sublist -> Arrays.stream(sublist.toArray()))
                .toArray();
    }

    private static <VALUE> Object[] sublists(
            int chunkSize,
            Factory sublistFactory,
            VALUE[] values) {

        Queue<MyList<VALUE>> sublists = new Queue<>();
        Chunker.Builder<VALUE> builder = new Chunker.Builder<VALUE>()
                .chunkSize(chunkSize)
                .chunkConsumer(sublists::enqueue)
                .factory(sublistFactory);

        try (Chunker<VALUE> chunker = builder.build()) {
            for (VALUE value : values) {
                chunker.push(value);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return sublists.dequeueAll();
    }
}
