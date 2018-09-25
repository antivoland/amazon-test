import org.junit.Assert;
import org.junit.Test;

/**
 * @author antivoland
 */
public class MyListTest {
    @Test
    public void testReversing() {
        Assert.assertArrayEquals(
                MyListA.of(1, 2, 3, 4).reverse().asArray(),
                new Number[]{4, 3, 2, 1});
    }

    @Test
    public void testFiltering() {
        Assert.assertArrayEquals(
                MyListA.of(1, 2, 3, 4).filter(x -> x % 2 == 0).asArray(),
                new Number[]{2, 4});
    }

    @Test
    public void testMapping() {
        Assert.assertArrayEquals(
                MyListA.of("foo", "bar", "baz", "boom").map(x -> x.length()).asArray(),
                new Number[]{3, 3, 3, 4});
        Assert.assertArrayEquals(
                MyListA.of("foo", "bar", "baz").map(x -> x.toUpperCase()).asArray(),
                new String[]{"FOO", "BAR", "BAZ"});
    }

    @Test
    public void testFoldingLeft() {
        Assert.assertEquals(
                (long) MyListA.of("foo", "bar", "baz", "boom").foldLeft(0, (a, x) -> a + x.length()),
                13L);
        Assert.assertEquals(
                MyListA.of("foo", "bar", "baz").foldLeft("", (a, x) -> a + x),
                "foobarbaz");
    }
}
