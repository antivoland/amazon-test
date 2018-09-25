import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
public class MyListA<VALUE> implements MyList<VALUE> {
    private final Object[] values;

    private MyListA(VALUE[] values) {
        this.values = values;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MyList<VALUE> reverse() {
        Object[] reversed = new Object[values.length];
        for (int i = 0, l = values.length; i < l; ++i) {
            reversed[i] = values[l - i - 1];
        }
        return new MyListA<>((VALUE[]) reversed);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        Queue<VALUE> filtered = new Queue<>();
        for (Object value : values) {
            if (filter.apply((VALUE) value)) {
                filtered.enqueue((VALUE) value);
            }
        }
        return new MyListA<>(filtered.dequeueAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        Object[] mapped = new Object[values.length];
        for (int i = 0, l = values.length; i < l; ++i) {
            mapped[i] = mapper.apply((VALUE) values[i]);
        }
        return new MyListA<>((MAPPED[]) mapped);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        FOLDED folded = identity;
        for (Object value : values) {
            folded = reducer.apply(folded, (VALUE) value);
        }
        return folded;
    }

    @Override
    @SuppressWarnings("unchecked")
    public VALUE[] asArray() {
        return (VALUE[]) values;
    }

    public static <VALUE> MyListA<VALUE> of(VALUE... values) {
        return new MyListA<>(values);
    }
}
