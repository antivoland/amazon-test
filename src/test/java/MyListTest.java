import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author antivoland
 */
public class MyListTest {
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    @AfterClass
    public static void destroy() {
        THREAD_POOL.shutdownNow();
    }

    @Test
    public void testArrayMyList() {
        test(ArrayMyList.FACTORY);
    }

    @Test
    public void testLinkedMyList() {
        test(LinkedMyList.FACTORY);
    }

    @Test
    public void testParallelMyList() {
        test(ParallelMyList.factory(2, ArrayMyList.FACTORY, THREAD_POOL));
        test(ParallelMyList.factory(2, LinkedMyList.FACTORY, THREAD_POOL));
    }

    void test(MyList.Factory factory) {
        // test empty
        Assert.assertArrayEquals(
                new Object[0],
                factory.listOf().toArray());

        // test non-empty
        Assert.assertArrayEquals(
                new Number[]{1, 2, 3, 4},
                factory.listOf(1, 2, 3, 4).toArray());

        // test reversing
        Assert.assertArrayEquals(
                new Number[]{4, 3, 2, 1},
                factory.listOf(1, 2, 3, 4).reverse().toArray());

        // test filtering
        Assert.assertArrayEquals(
                new Number[]{2, 4},
                factory.listOf(1, 2, 3, 4).filter(x -> x % 2 == 0).toArray());

        // test mapping
        Assert.assertArrayEquals(
                new Number[]{3, 3, 3, 4},
                factory.listOf("foo", "bar", "baz", "boom").map(x -> x.length()).toArray());
        Assert.assertArrayEquals(
                new String[]{"FOO", "BAR", "BAZ"},
                factory.listOf("foo", "bar", "baz").map(x -> x.toUpperCase()).toArray());

        // test associative folding left
        Assert.assertEquals(
                13L,
                (long) factory.listOf("foo", "bar", "baz", "boom").foldLeft(0, (a, x) -> a + x.length()));
        Assert.assertEquals(
                "foobarbaz",
                factory.listOf("foo", "bar", "baz").foldLeft("", (a, x) -> a + x));

        // test non-associative folding left
        Assert.assertEquals(
                1L,
                (long) factory.listOf(20, 5).foldLeft(100, (a, x) -> a / x));
    }
}
