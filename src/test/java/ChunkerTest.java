import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * In this test we will count the sum of the natural numbers less than or equal
 * to some given value.
 *
 * @author antivoland
 */
public class ChunkerTest {
    private static final int CHUNK_SIZE = 9;
    private static final Consumer<MyList<Object>> DUMMY_CHUNK_CONSUMER = chunk -> {
        // do nothing
    };
    private static final MyList.Factory UNSUPPORTED_FACTORY = new MyList.Factory() {
        @Override
        public <VALUE> MyList<VALUE> create(VALUE[] values) {
            throw new UnsupportedOperationException("Method is not supported");
        }
    };

    @Test(expected = NullPointerException.class)
    public void testMissingChunkSize() {
        new Chunker.Builder<>()
                .chunkConsumer(DUMMY_CHUNK_CONSUMER)
                .factory(UNSUPPORTED_FACTORY)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeChunkSize() {
        new Chunker.Builder<>()
                .chunkSize(-1)
                .chunkConsumer(DUMMY_CHUNK_CONSUMER)
                .factory(UNSUPPORTED_FACTORY)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroChunkSize() {
        new Chunker.Builder<>()
                .chunkSize(0)
                .chunkConsumer(DUMMY_CHUNK_CONSUMER)
                .factory(UNSUPPORTED_FACTORY)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testMissingChunkConsumer() {
        new Chunker.Builder<>()
                .chunkSize(1)
                .factory(UNSUPPORTED_FACTORY)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testMissingFactory() {
        new Chunker.Builder<>()
                .chunkSize(1)
                .chunkConsumer(DUMMY_CHUNK_CONSUMER)
                .build();
    }

    @Test
    public void testLesser() throws Exception {
        test(CHUNK_SIZE - 1);
        test(CHUNK_SIZE / 2);
    }

    @Test
    public void testExact() throws Exception {
        test(CHUNK_SIZE);
    }

    @Test
    public void testGreater() throws Exception {
        test(CHUNK_SIZE + 1);
        test(CHUNK_SIZE * 2);
    }

    void test(int value) throws Exception {
        test(value, ArrayMyList.FACTORY);
        test(value, LinkedMyList.FACTORY);
    }

    void test(int value, MyList.Factory factory) throws Exception {
        List<Integer> numbers = IntStream
                .rangeClosed(1, value)
                .boxed()
                .collect(toList());
        Collections.shuffle(numbers);

        final AtomicInteger sum = new AtomicInteger();
        Chunker.Builder<Integer> builder = new Chunker.Builder<Integer>()
                .chunkSize(CHUNK_SIZE)
                .chunkConsumer(chunk -> sum.addAndGet(chunk.foldLeft(0, (a, b) -> a + b)))
                .factory(factory);

        try (Chunker<Integer> chunker = builder.build()) {
            numbers.forEach(chunker::push);
        }

        Assert.assertEquals(value * (value + 1) / 2, sum.get());
    }
}
