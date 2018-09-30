import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
class EmptyMyList<VALUE> implements MyList<VALUE> {
    @Override
    public MyList<VALUE> reverse() {
        return new EmptyMyList<>();
    }

    @Override
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        return new EmptyMyList<>();
    }

    @Override
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        return new EmptyMyList<>();
    }

    @Override
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        return identity;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public String toString() {
        return "[]";
    }
}
