import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class StackTest {
    @Test
    public void testEmpty() {
        Assert.assertTrue(new Stack<>().empty());
    }

    @Test
    public void testNull() {
        Stack<Object> stack = new Stack<>();
        stack.push(null);
        Assert.assertFalse(stack.empty());
        Assert.assertNull(stack.pop());
        Assert.assertTrue(stack.empty());
    }

    @Test
    public void test() {
        Stack<Object> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Assert.assertEquals(3, stack.pop());
        Assert.assertEquals(2, stack.pop());
        Assert.assertEquals(1, stack.pop());
        Assert.assertTrue(stack.empty());

        stack.push(4);
        stack.push(5);
        Assert.assertEquals(5, stack.pop());
        Assert.assertEquals(4, stack.pop());
        Assert.assertTrue(stack.empty());
    }
}
