package cz.mg.collections.services;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.array.Array;
import cz.mg.collections.utilities.OrderFunctions;
import cz.mg.test.Assert;

public @Test class ArraySorterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ArraySorterTest.class.getSimpleName() + " ... ");

        ArraySorterTest test = new ArraySorterTest();
        test.testSort();
        test.testSortCopy();

        System.out.println("OK");
    }

    private void testSort() {
        Array<Integer> array = new Array<>(Integer.class, 3, 4, null, 0, 2, 1);
        ArraySorter sorter = ArraySorter.getInstance();
        sorter.sort(array, OrderFunctions.INTEGER_NULL_LAST);

        Assert.assertEquals(0, array.get(0));
        Assert.assertEquals(1, array.get(1));
        Assert.assertEquals(2, array.get(2));
        Assert.assertEquals(3, array.get(3));
        Assert.assertEquals(4, array.get(4));
        Assert.assertEquals(null, array.get(5));
    }

    private void testSortCopy() {
        Array<Integer> array = new Array<>(Integer.class, 3, 4, null, 0, 2, 1);
        ArraySorter sorter = ArraySorter.getInstance();
        Array<Integer> copy = sorter.sortCopy(array, OrderFunctions.INTEGER_NULL_LAST);

        Assert.assertEquals(3, array.get(0));
        Assert.assertEquals(4, array.get(1));
        Assert.assertEquals(null, array.get(2));
        Assert.assertEquals(0, array.get(3));
        Assert.assertEquals(2, array.get(4));
        Assert.assertEquals(1, array.get(5));

        Assert.assertEquals(0, copy.get(0));
        Assert.assertEquals(1, copy.get(1));
        Assert.assertEquals(2, copy.get(2));
        Assert.assertEquals(3, copy.get(3));
        Assert.assertEquals(4, copy.get(4));
        Assert.assertEquals(null, copy.get(5));
    }
}
