import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class MyListTest {
    @Test
    public void testArrayMyList() {
        test(ArrayMyList.FACTORY);
    }

    @Test
    public void testLinkedMyList() {
        test(LinkedMyList.FACTORY);
    }

    void test(MyList.Factory factory) {
        // test empty
        Assert.assertArrayEquals(
                new Object[0],
                factory.create().toArray());
        
        // test non-empty
        Assert.assertArrayEquals(
                new Number[]{1, 2, 3, 4},
                factory.create(1, 2, 3, 4).toArray());
        
        // test reversing
        Assert.assertArrayEquals(
                new Number[]{4, 3, 2, 1},
                factory.create(1, 2, 3, 4).reverse().toArray());

        // test filtering
        Assert.assertArrayEquals(
                new Number[]{2, 4},
                factory.create(1, 2, 3, 4).filter(x -> x % 2 == 0).toArray());

        // test mapping
        Assert.assertArrayEquals(
                new Number[]{3, 3, 3, 4},
                factory.create("foo", "bar", "baz", "boom").map(x -> x.length()).toArray());
        Assert.assertArrayEquals(
                new String[]{"FOO", "BAR", "BAZ"},
                factory.create("foo", "bar", "baz").map(x -> x.toUpperCase()).toArray());

        // test associative folding left
        Assert.assertEquals(
                13L,
                (long) factory.create("foo", "bar", "baz", "boom").foldLeft(0, (a, x) -> a + x.length()));
        Assert.assertEquals(
                "foobarbaz",
                factory.create("foo", "bar", "baz").foldLeft("", (a, x) -> a + x));

        // test non-associative folding left
        Assert.assertEquals(
                1L,
                (long) factory.create(20, 5).foldLeft(100, (a, x) -> a / x));
    }
}
