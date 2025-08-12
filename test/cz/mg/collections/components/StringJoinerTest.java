package cz.mg.collections.components;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

import java.util.Objects;

public @Test class StringJoinerTest {
    public static void main(String[] args) {
        System.out.print("Running " + StringJoinerTest.class.getSimpleName() + " ... ");

        StringJoinerTest test = new StringJoinerTest();
        test.testJoinStrings();
        test.testJoinObjects();
        test.testJoinMethods();
        test.testJoinGenerics();
        test.testJoinFilter();

        System.out.println("OK");
    }

    private void testJoinStrings() {
        testJoinStrings(new List<>(), ".", "");
        testJoinStrings(new Array<>(), "", "");
        testJoinStrings(new List<>("1"), " ", "1");
        testJoinStrings(new List<>("A", "B"), "...", "A...B");
        testJoinStrings(new List<>("1", "2", "3"), " ", "1 2 3");
        testJoinStrings(new List<>("111", "22", "3"), ", ", "111, 22, 3");
    }

    private void testJoinStrings(
        @Mandatory Collection<String> items,
        @Mandatory String delimiter,
        @Mandatory String expectedResult
    ) {
        String actualResult = new StringJoiner<>(items)
            .withDelimiter(delimiter)
            .join();

        Assert.assertEquals(expectedResult, actualResult);
    }

    private void testJoinObjects() {
        testJoinObjects(new List<>(), ".", "");
        testJoinObjects(new Array<>(), "", "");
        testJoinObjects(new List<>(1), " ", "1");
        testJoinObjects(new List<>(1, 2), "...", "1...2");
        testJoinObjects(new List<>(1, 2, 3), " ", "1 2 3");
        testJoinObjects(new List<>(111, 22, 3), ", ", "111, 22, 3");
    }

    private void testJoinObjects(
        @Mandatory Collection<Integer> items,
        @Mandatory String delimiter,
        @Mandatory String expectedResult
    ) {
        String actualResult = new StringJoiner<>(items)
            .withDelimiter(delimiter)
            .join();

        Assert.assertEquals(expectedResult, actualResult);
    }

    private void testJoinMethods() {
        Assert.assertEquals("foobar", new StringJoiner<>(new List<>("foo", "bar")).join());
        Assert.assertEquals("foobar", new StringJoiner<>(new List<>("foo", "bar")).withDelimiter("").join());
        Assert.assertEquals("foobar", new StringJoiner<>(new List<>("foo", "bar")).withDelimiter("").withConverter(Objects::toString).join());
    }

    private void testJoinGenerics() {
        Assert.assertEquals("3", new StringJoiner<>(new List<>("abc")).withConverter(s -> "" + s.length()).join());
    }

    private void testJoinFilter() {
        testJoinFilter("  ", o -> true, "", "", "");
        testJoinFilter("     ", o -> true, " ", " ", " ");
        testJoinFilter("a b c", o -> true, "a", "b", "c");
        testJoinFilter("", o -> false, "a", "b", "c");
        testJoinFilter("a c", o -> !o.equals("b"), "a", "b", "c");
        testJoinFilter("a b", o -> !o.equals("c"), "a", "b", "c");
        testJoinFilter("b c", o -> !o.equals("a"), "a", "b", "c");
        testJoinFilter("a null c", o -> true, "a", null, "c");
        testJoinFilter("a c", Objects::nonNull, "a", null, "c");
        testJoinFilter("a c", o -> !o.isEmpty(), "a", "", "c");
        testJoinFilter("a c", o -> !o.isBlank(), "a", "     ", "c");
        testJoinFilter("", o -> !o.isBlank());
        testJoinFilter("", o -> !o.isBlank(), "");
        testJoinFilter("a", o -> !o.isBlank(), "a");
        testJoinFilter("a", o -> !o.isBlank(), "a", "");
        testJoinFilter("a", o -> !o.isBlank(), "", "a");
    }

    private void testJoinFilter(String expectation, Predicate<String> filter, String... input) {
        Assert.assertEquals(
            expectation,
            new StringJoiner<>(input)
                .withDelimiter(" ")
                .withFilter(filter)
                .join()
        );
    }
}
