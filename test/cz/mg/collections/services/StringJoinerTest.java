package cz.mg.collections.services;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Test class StringJoinerTest {
    public static void main(String[] args) {
        System.out.print("Running " + StringJoinerTest.class.getSimpleName() + " ... ");

        StringJoinerTest test = new StringJoinerTest();
        test.testJoin();

        System.out.println("OK");
    }

    private void testJoin() {
        testJoin(new List<>(), ".", "");
        testJoin(new Array<>(), "", "");
        testJoin(new List<>("1"), " ", "1");
        testJoin(new List<>("A", "B"), "...", "A...B");
        testJoin(new List<>("1", "2", "3"), " ", "1 2 3");
        testJoin(new List<>("111", "22", "3"), ", ", "111, 22, 3");
    }

    private void testJoin(
        @Mandatory Collection<String> parts,
        @Mandatory String delimiter,
        @Mandatory String expectedResult
    ) {
        StringJoiner joiner = StringJoiner.getInstance();
        String actualResult = joiner.join(parts, delimiter);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
