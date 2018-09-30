/**
 * @author antivoland
 */
class Stack<VALUE> {
    private Object[] values = new Object[1];
    private int top = -1;

    boolean empty() {
        return top < 0;
    }

    Stack<VALUE> push(VALUE value) {
        ++top;
        if (top == values.length) {
            Object[] doubled = new Object[values.length * 2];
            System.arraycopy(values, 0, doubled, 0, values.length);
            values = doubled;
        }
        values[top] = value;
        return this;
    }

    @SuppressWarnings("unchecked")
    VALUE pop() {
        VALUE value = (VALUE) values[top];
        --top;
        return value;
    }
}
