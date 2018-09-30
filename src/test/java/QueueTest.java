import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class QueueTest {
    @Test
    public void testEmpty() {
        Queue<Object> queue = new Queue<>();
        Assert.assertTrue(queue.empty());
        Assert.assertArrayEquals(new Object[0], queue.dequeueAll());
    }

    @Test
    public void testNull() {
        Assert.assertArrayEquals(new Object[]{null}, new Queue<>().enqueue(null).dequeueAll());
    }

    @Test
    public void test() {
        Queue<Object> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Assert.assertEquals(1, queue.dequeue());
        Assert.assertEquals(2, queue.dequeue());
        Assert.assertEquals(3, queue.dequeue());
        Assert.assertTrue(queue.empty());

        queue.enqueue(4);
        queue.enqueue(5);
        Assert.assertArrayEquals(new Object[]{4, 5}, queue.dequeueAll());
        Assert.assertTrue(queue.empty());

        queue.enqueue(6);
        Assert.assertArrayEquals(new Object[]{6}, queue.dequeueAll());
        Assert.assertTrue(queue.empty());
    }
}
