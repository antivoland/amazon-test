import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author antivoland
 */
public class LinkedMyList<VALUE> implements MyList<VALUE> {
    public static final Factory FACTORY = LinkedMyList::of;

    private class Node {
        final VALUE value;
        Node next;
        Node previous;

        Node(VALUE value) {
            this.value = value;
        }
    }

    private abstract class Traverser {
        abstract Node first();

        abstract Node next(Node node);

        abstract Traverser reversed();
    }

    private class LTRTraverser extends Traverser {
        @Override
        Node first() {
            return left;
        }

        @Override
        Node next(Node node) {
            return node.next;
        }

        @Override
        Traverser reversed() {
            return new RTLTraverser();
        }
    }

    private class RTLTraverser extends Traverser {
        @Override
        Node first() {
            return right;
        }

        @Override
        Node next(Node node) {
            return node.previous;
        }

        @Override
        Traverser reversed() {
            return new LTRTraverser();
        }
    }

    private final int size;
    private Node left;
    private Node right;
    private Traverser traverser;

    // O(N)
    @SuppressWarnings("unchecked")
    private LinkedMyList(VALUE[] values) {
        this.size = values.length;
        if (values.length == 0) return;

        Object[] nodes = new Object[values.length];
        for (int i = 0; i < values.length; ++i) {
            nodes[i] = new Node(values[i]);
            if (i > 0) {
                ((Node) nodes[i]).previous = ((Node) nodes[i - 1]);
                ((Node) nodes[i - 1]).next = ((Node) nodes[i]);
            }
        }

        this.left = ((Node) nodes[0]);
        this.right = ((Node) nodes[nodes.length - 1]);
        this.traverser = new LTRTraverser();
    }

    // O(1)
    private LinkedMyList(int size, Node left, Node right, Traverser traverser) {
        this.size = size;
        this.left = left;
        this.right = right;
        this.traverser = traverser;
    }

    // O(1)
    @Override
    public MyList<VALUE> reverse() {
        return new LinkedMyList<>(size, left, right, traverser.reversed());
    }

    // O(N)
    @Override
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        Queue<VALUE> filtered = new Queue<>();
        Node node = traverser.first();
        do {
            if (filter.apply(node.value)) {
                filtered.enqueue(node.value);            // N times in worst case
            }
        } while ((node = traverser.next(node)) != null); // N times
        return new LinkedMyList<>(filtered.dequeueAll());
    }

    // O(N)
    @Override
    @SuppressWarnings("unchecked")
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        Object[] mapped = new Object[size];
        if (size > 0) {
            int i = 0;
            Node node = traverser.first();
            do {
                mapped[i] = mapper.apply(node.value);
                ++i;
            } while ((node = traverser.next(node)) != null);
        }
        return new LinkedMyList<>((MAPPED[]) mapped);
    }

    // O(N)
    @Override
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        FOLDED folded = identity;
        Node node = traverser.first();
        do {
            folded = reducer.apply(folded, node.value);
        } while ((node = traverser.next(node)) != null);
        return folded;
    }

    // O(N)
    @Override
    @SuppressWarnings("unchecked")
    public VALUE[] toArray() {
        Object[] values = new Object[size];
        if (size > 0) {
            int i = 0;
            Node node = traverser.first();
            do {
                values[i] = node.value;
                ++i;
            } while ((node = traverser.next(node)) != null);
        }
        return (VALUE[]) values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (size > 0) {
            int i = 0;
            Node node = traverser.first();
            do {
                sb.append(node.value);
                ++i;
                if (i < size) {
                    sb.append(", ");
                }
            } while ((node = traverser.next(node)) != null);
        }
        sb.append(']');
        return sb.toString();
    }

    @SafeVarargs
    public static <VALUE> LinkedMyList<VALUE> of(VALUE... values) {
        return new LinkedMyList<>(values);
    }
}
