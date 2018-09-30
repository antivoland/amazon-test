import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
public class ArrayMyList<VALUE> implements MyList<VALUE> {
    public static final Factory FACTORY = ArrayMyList::of;

    private final Object[] values;

    // O(1)
    private ArrayMyList(VALUE[] values) {
        this.values = values;
    }

    // O(N)
    @Override
    @SuppressWarnings("unchecked")
    public MyList<VALUE> reverse() {
        Object[] reversed = new Object[values.length];
        for (int i = 0, l = values.length; i < l; ++i) {
            reversed[i] = values[l - i - 1];
        }
        return new ArrayMyList<>((VALUE[]) reversed);
    }

    // O(N)
    @Override
    @SuppressWarnings("unchecked")
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        Queue<VALUE> filtered = new Queue<>();
        for (Object value : values) {            // N times
            if (filter.apply((VALUE) value)) {
                filtered.enqueue((VALUE) value); // N times in worst case
            }
        }
        return new ArrayMyList<>(filtered.dequeueAll());
    }

    // O(N)
    @Override
    @SuppressWarnings("unchecked")
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        Object[] mapped = new Object[values.length];
        for (int i = 0, l = values.length; i < l; ++i) {
            mapped[i] = mapper.apply((VALUE) values[i]);
        }
        return new ArrayMyList<>((MAPPED[]) mapped);
    }

    // O(N)
    @Override
    @SuppressWarnings("unchecked")
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        FOLDED folded = identity;
        for (Object value : values) {
            folded = reducer.apply(folded, (VALUE) value);
        }
        return folded;
    }

    // O(N)
    @Override
    public Object[] toArray() {
        Object[] copy = new Object[values.length];
        System.arraycopy(values, 0, copy, 0, values.length);
        return copy;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    @SafeVarargs
    public static <VALUE> ArrayMyList<VALUE> of(VALUE... values) {
        return new ArrayMyList<>(values);
    }
}
