package cz.mg.collections.services;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.collections.utilities.OrderFunctions;
import cz.mg.test.Assert;

public @Test class ListSorterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ListSorterTest.class.getSimpleName() + " ... ");

        ListSorterTest test = new ListSorterTest();
        test.testSort();
        test.testSortCopy();

        System.out.println("OK");
    }

    private void testSort() {
        List<Integer> list = new List<>(3, 4, null, 0, 2, 1);
        ListSorter sorter = ListSorter.getInstance();
        sorter.sort(list, OrderFunctions.INTEGER_NULL_LAST);

        Assert.assertEquals(0, list.get(0));
        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(2, list.get(2));
        Assert.assertEquals(3, list.get(3));
        Assert.assertEquals(4, list.get(4));
        Assert.assertEquals(null, list.get(5));
    }

    private void testSortCopy() {
        List<Integer> list = new List<>(3, 4, null, 0, 2, 1);
        ListSorter sorter = ListSorter.getInstance();
        List<Integer> copy = sorter.sortCopy(list, OrderFunctions.INTEGER_NULL_LAST);

        Assert.assertEquals(3, list.get(0));
        Assert.assertEquals(4, list.get(1));
        Assert.assertEquals(null, list.get(2));
        Assert.assertEquals(0, list.get(3));
        Assert.assertEquals(2, list.get(4));
        Assert.assertEquals(1, list.get(5));

        Assert.assertEquals(0, copy.get(0));
        Assert.assertEquals(1, copy.get(1));
        Assert.assertEquals(2, copy.get(2));
        Assert.assertEquals(3, copy.get(3));
        Assert.assertEquals(4, copy.get(4));
        Assert.assertEquals(null, copy.get(5));
    }
}
