package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
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

        System.out.println("OK");
    }

    private final @Service StringJoiner joiner = StringJoiner.getInstance();

    private void testJoinStrings() {
        testJoinStrings(new List<>(), ".", "");
        testJoinStrings(new Array<>(), "", "");
        testJoinStrings(new List<>("1"), " ", "1");
        testJoinStrings(new List<>("A", "B"), "...", "A...B");
        testJoinStrings(new List<>("1", "2", "3"), " ", "1 2 3");
        testJoinStrings(new List<>("111", "22", "3"), ", ", "111, 22, 3");
    }

    private void testJoinStrings(
        @Mandatory Collection<String> parts,
        @Mandatory String delimiter,
        @Mandatory String expectedResult
    ) {
        String actualResult = joiner.join(parts, delimiter);
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
        String actualResult = joiner.join(items, delimiter, Objects::toString);
        Assert.assertEquals(expectedResult, actualResult);
    }

    private void testJoinMethods() {
        Assert.assertEquals("foobar", joiner.join(new List<>("foo", "bar")));
        Assert.assertEquals("foobar", joiner.join(new List<>("foo", "bar"), ""));
        Assert.assertEquals("foobar", joiner.join(new List<>("foo", "bar"), "", Objects::toString));
    }
}
