import java.util.Arrays;
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
        return new MyListA<>((VALUE[]) Arrays.stream(values)
                .map(value -> (VALUE) value)
                .filter(filter::apply)
                .toArray());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        return new MyListA<>((MAPPED[]) Arrays.stream(values)
                .map(value -> (VALUE) value)
                .map(mapper)
                .toArray());
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
