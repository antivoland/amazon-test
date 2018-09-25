import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class QueueTest {
    @Test
    public void testEmpty() {
        Assert.assertArrayEquals(new String[0], new Queue<String>().dequeueAll());
    }

    @Test
    public void test() {
        Queue<Number> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Assert.assertArrayEquals(new Number[]{1, 2, 3}, queue.dequeueAll());
    }
}
