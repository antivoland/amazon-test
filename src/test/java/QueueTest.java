import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class QueueTest {
    @Test
    public void testEmpty() {
        Assert.assertArrayEquals(new Object[0], new Queue<>().dequeueAll());
    }

    @Test
    public void testNull() {
        Assert.assertArrayEquals(new Object[]{null}, new Queue<>().enqueue(null).dequeueAll());
    }

    @Test
    public void test() {
        Queue<Number> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Assert.assertArrayEquals(new Number[]{1, 2, 3}, queue.dequeueAll());
        Assert.assertArrayEquals(new String[0], new Queue<String>().dequeueAll());

        queue.enqueue(4);
        queue.enqueue(5);
        Assert.assertArrayEquals(new Number[]{4, 5}, queue.dequeueAll());
        Assert.assertArrayEquals(new String[0], new Queue<String>().dequeueAll());

        queue.enqueue(6);
        Assert.assertArrayEquals(new Number[]{6}, queue.dequeueAll());
        Assert.assertArrayEquals(new String[0], new Queue<String>().dequeueAll());
    }
}
