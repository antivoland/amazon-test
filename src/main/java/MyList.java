import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
public interface MyList<VALUE> {
    MyList<VALUE> reverse();

    MyList<VALUE> filter(Function<VALUE, Boolean> filter);

    <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper);

    <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer);

    Object[] toArray();

    interface Factory {
        <VALUE> MyList<VALUE> listFrom(VALUE[] values);

        default <VALUE> MyList<VALUE> listOf(VALUE... values) {
            return listFrom(values);
        }
    }
}
