package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.array.Array;
import cz.mg.collections.utilities.Direction;
import cz.mg.test.Assert;

public @Test class SimpleArraySortTest {
    public static void main(String[] args) {
        System.out.print("Running " + SimpleArraySortTest.class.getSimpleName() + " ... ");

        SimpleArraySortTest test = new SimpleArraySortTest();
        test.testSort();

        System.out.println("OK");
    }

    private void testSort() {
        Array<Integer> array = new Array<>(3, 4, null, 0, 2, 1);
        SimpleArraySort sort = SimpleArraySort.getInstance();

        sort.sort(array, Integer::compare, Direction.ASCENDING);

        Assert.assertEquals(0, array.get(0));
        Assert.assertEquals(1, array.get(1));
        Assert.assertEquals(2, array.get(2));
        Assert.assertEquals(3, array.get(3));
        Assert.assertEquals(4, array.get(4));
        Assert.assertEquals(null, array.get(5));

        sort.sort(array, Integer::compare, Direction.DESCENDING);

        Assert.assertEquals(null, array.get(0));
        Assert.assertEquals(4, array.get(1));
        Assert.assertEquals(3, array.get(2));
        Assert.assertEquals(2, array.get(3));
        Assert.assertEquals(1, array.get(4));
        Assert.assertEquals(0, array.get(5));
    }
}
