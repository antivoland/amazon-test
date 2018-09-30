/**
 * @author antivoland
 */
class Queue<VALUE> {
    private Object[] values = new Object[1];
    private int last = -1;

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

    // O(N)
    @SuppressWarnings("unchecked")
    VALUE[] dequeueAll() {
        if (last < 0) {
            return (VALUE[]) new Object[0];
        }
        Object[] dequeued = new Object[last + 1];
        System.arraycopy(values, 0, dequeued, 0, dequeued.length);
        last = -1;
        return (VALUE[]) dequeued;
    }
}
