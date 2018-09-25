import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class MyListTest {
    @Test
    public void testReversing() {
        Assert.assertArrayEquals(
                new Number[]{4, 3, 2, 1},
                MyListA.of(1, 2, 3, 4).reverse().asArray());
    }

    @Test
    public void testFiltering() {
        Assert.assertArrayEquals(
                new Number[]{2, 4},
                MyListA.of(1, 2, 3, 4).filter(x -> x % 2 == 0).asArray());
    }

    @Test
    public void testMapping() {
        Assert.assertArrayEquals(
                new Number[]{3, 3, 3, 4},
                MyListA.of("foo", "bar", "baz", "boom").map(x -> x.length()).asArray());
        Assert.assertArrayEquals(
                new String[]{"FOO", "BAR", "BAZ"},
                MyListA.of("foo", "bar", "baz").map(x -> x.toUpperCase()).asArray());
    }

    @Test
    public void testFoldingLeft() {
        // associative
        Assert.assertEquals(
                13L,
                (long) MyListA.of("foo", "bar", "baz", "boom").foldLeft(0, (a, x) -> a + x.length()));
        Assert.assertEquals(
                "foobarbaz",
                MyListA.of("foo", "bar", "baz").foldLeft("", (a, x) -> a + x));

        // non-associative
        Assert.assertEquals(
                1L,
                (long) MyListA.of(20, 5).foldLeft(100, (a, x) -> a / x));
    }
}
