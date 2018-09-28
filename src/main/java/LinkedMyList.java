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

    private final Function<Node, Node> next;
    private Node first;
    private Node last;

    @SuppressWarnings("unchecked")
    private LinkedMyList(VALUE[] values) {
        this.next = node -> node.next;
        if (values.length == 0) return;

        Node[] nodes = (Node[]) new Object[values.length];
        for (int i = 0; i < values.length; ++i) {
            nodes[i] = new Node(values[i]);
            if (i > 0) {
                nodes[i].previous = nodes[i - 1];
                nodes[i - 1].next = nodes[i];
            }
        }
        // nodes[nodes.length]

        this.first = nodes[0];
        this.last = nodes[nodes.length - 1];
    }

    @Override
    public MyList<VALUE> reverse() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MyList<VALUE> filter(Function<VALUE, Boolean> filter) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public <MAPPED> MyList<MAPPED> map(Function<VALUE, MAPPED> mapper) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public <FOLDED> FOLDED foldLeft(FOLDED identity, BiFunction<FOLDED, VALUE, FOLDED> reducer) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public VALUE[] asArray() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @SafeVarargs
    public static <VALUE> LinkedMyList<VALUE> of(VALUE... values) {
        return new LinkedMyList<>(values);
    }
}
