import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
public class MyList<VALUE> {
    private final Object[] values;

    private MyList(VALUE[] values) {
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public MyList<VALUE> reverse() {
        Object[] reversed = new Object[values.length];
        for (int i = 0, l = values.length; i < l; ++i) {
            reversed[i] = values[l - i - 1];
        }
        return new MyList<>((VALUE[]) reversed);
    }

    @SuppressWarnings("unchecked")
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        return new MyList<>((VALUE[]) Arrays.stream(values)
                .map(value -> (VALUE) value)
                .filter(filter::apply)
                .toArray());
    }

    @SuppressWarnings("unchecked")
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        return new MyList<>((MAPPED[]) Arrays.stream(values)
                .map(value -> (VALUE) value)
                .map(mapper)
                .toArray());
    }

    @SuppressWarnings("unchecked")
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        FOLDED folded = identity;
        for (int i = 0; i < values.length; ++i) {
            folded = reducer.apply(folded, (VALUE) values[i]);
        }
        return folded;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    public static <VALUE> MyList<VALUE> of(VALUE... values) {
        return new MyList<>(values);
    }

    public static void main(String[] args) {
        System.out.println(MyList.of(1, 2, 3, 4).reverse());
        System.out.println(MyList.of(1, 2, 3, 4).filter(x -> x % 2 == 0));
        System.out.println(MyList.of("foo", "bar", "baz", "boom").map(x -> x.length()));
        System.out.println(MyList.of("foo", "bar", "baz").map(x -> x.toUpperCase()));
        System.out.println(MyList.of("foo", "bar", "baz", "boom").foldLeft(0, (a, x) -> a + x.length()));
        System.out.println(MyList.of("foo", "bar", "baz").foldLeft("", (a, x) -> a + x));
    }
}
