package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.collections.utilities.Direction;
import cz.mg.test.Assert;

public @Test class SimpleListSortTest {
    public static void main(String[] args) {
        System.out.print("Running " + SimpleListSortTest.class.getSimpleName() + " ... ");

        SimpleListSortTest test = new SimpleListSortTest();
        test.testSort();

        System.out.println("OK");
    }

    private void testSort() {
        List<Integer> list = new List<>(3, 4, null, 0, 2, 1);
        SimpleListSort sort = SimpleListSort.getInstance();
        sort.sort(list, Integer::compareTo, Direction.ASCENDING);

        Assert.assertEquals(0, list.get(0));
        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(2, list.get(2));
        Assert.assertEquals(3, list.get(3));
        Assert.assertEquals(4, list.get(4));
        Assert.assertEquals(null, list.get(5));

        sort.sort(list, Integer::compare, Direction.DESCENDING);

        Assert.assertEquals(null, list.get(0));
        Assert.assertEquals(4, list.get(1));
        Assert.assertEquals(3, list.get(2));
        Assert.assertEquals(2, list.get(3));
        Assert.assertEquals(1, list.get(4));
        Assert.assertEquals(0, list.get(5));

    }
}
