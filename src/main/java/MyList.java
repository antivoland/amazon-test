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

    VALUE[] asArray();

    interface Factory {
        <VALUE> MyList<VALUE> create(VALUE... values);
    }
}
