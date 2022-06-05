package cz.mg.collections.array;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class ArrayTest {
    public static void main(String[] args) {
        System.out.print("Running " + ArrayTest.class.getSimpleName() + " ... ");

        ArrayTest test = new ArrayTest();
        test.testEmptyArray();
        test.testNonEmptyArray();
        test.testGetAndSet();
        test.testWipe();
        test.testContains();
        test.testConstructors();
        test.testIterator();
        test.testGetData();

        System.out.println("OK");
    }

    private void testEmptyArray() {
        Assert.assertExceptionThrown(IllegalArgumentException.class, () -> new Array<>(-1));
        Array<String> array = new Array<>(0);
        Assert.assertEquals(0, array.count());
        Assert.assertEquals(true, array.isEmpty());
    }

    private void testNonEmptyArray() {
        Array<String> array = new Array<>(3);
        Assert.assertEquals(3, array.count());
        Assert.assertEquals(false, array.isEmpty());
    }

    private void testGetAndSet() {
        Array<String> array = new Array<>(3);

        array.set(0, "first");
        array.set(2, "last");

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        Assert.assertEquals("first", array.get(0));
        Assert.assertEquals(null, array.get(1));
        Assert.assertEquals("last", array.get(2));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> array.get(3));

        array.set(0, null);
        Assert.assertEquals(null, array.get(0));
    }

    private void testWipe() {
        Array<String> array = new Array<>(3);
        array.set(0, "first");
        array.set(1, null);
        array.set(2, "last");

        Assert.assertEquals("first", array.get(0));
        Assert.assertEquals(null, array.get(1));
        Assert.assertEquals("last", array.get(2));

        array.wipe();

        Assert.assertEquals(null, array.get(0));
        Assert.assertEquals(null, array.get(1));
        Assert.assertEquals(null, array.get(2));
    }

    private void testContains() {
        Array<String> array = new Array<>(3);
        array.set(0, "first");
        array.set(1, null);
        array.set(2, "last");

        Assert.assertEquals(true, array.contains("first"));
        Assert.assertEquals(true, array.contains(null));
        Assert.assertEquals(true, array.contains("last"));
        Assert.assertEquals(false, array.contains("second"));

        array.set(1, "second");

        Assert.assertEquals(false, array.contains(null));
        Assert.assertEquals(true, array.contains("second"));

        array.wipe();

        Assert.assertEquals(false, array.contains("first"));
        Assert.assertEquals(true, array.contains(null));
        Assert.assertEquals(false, array.contains("last"));
        Assert.assertEquals(false, array.contains("second"));
    }

    private void testConstructors() {
        Array<String> array1 = new Array<>("0", "1", "2");

        Assert.assertEquals("0", array1.get(0));
        Assert.assertEquals("1", array1.get(1));
        Assert.assertEquals("2", array1.get(2));

        Array<String> array2 = new Array<>(array1);
        array2.set(1, "11");

        Assert.assertEquals("0", array1.get(0));
        Assert.assertEquals("1", array1.get(1));
        Assert.assertEquals("2", array1.get(2));

        Assert.assertEquals("0", array2.get(0));
        Assert.assertEquals("11", array2.get(1));
        Assert.assertEquals("2", array2.get(2));
    }

    private void testIterator() {
        Array<Integer> array = new Array<>(0, 1, 2);

        int i = 0;
        for(Integer item : array) {
            Assert.assertEquals(i, item);
            i++;
        }

        Assert.assertEquals(i, array.count());
    }

    private void testGetData() {
        Array<String> array = new Array<>("0", null, "2");
        Assert.assertEquals("0", array.getData()[0]);
        Assert.assertEquals(null, array.getData()[1]);
        Assert.assertEquals("2", array.getData()[2]);

        Array<String> emptyArray = new Array<>();
        Assert.assertEquals(true, emptyArray.isEmpty());
        Assert.assertEquals(0, emptyArray.getData().length);
    }
}
