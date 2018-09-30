/**
 * @author antivoland
 */
class Queue<VALUE> {
    private Object[] values = new Object[1];
    private int first = 0;
    private int last = -1;

    boolean empty() {
        return last < first;
    }

    // amortized O(1)
    Queue<VALUE> enqueue(VALUE value) {
        ++last;
        if (last == values.length) {
            Object[] doubled = new Object[values.length * 2];
            System.arraycopy(values, 0, doubled, 0, values.length);
            values = doubled;
        }
        values[last] = value;
        return this;
    }

    @SuppressWarnings("unchecked")
    VALUE dequeue() {
        VALUE dequeued = (VALUE) values[first];
        ++first;
        return dequeued;
    }

    // O(N)
    @SuppressWarnings("unchecked")
    VALUE[] dequeueAll() {
        if (last < first) {
            return (VALUE[]) new Object[0];
        }
        Object[] dequeued = new Object[last - first + 1];
        System.arraycopy(values, first, dequeued, 0, dequeued.length);
        first = 0;
        last = -1;
        return (VALUE[]) dequeued;
    }
}
