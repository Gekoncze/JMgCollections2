package cz.mg.collections.array;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.Assert;

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

        System.out.println("OK");
    }

    private void testEmptyArray() {
        Array<String> array = new Array<>(String.class, 0);
        Assert.assertEquals(0, array.count());
        Assert.assertEquals(true, array.isEmpty());
    }

    private void testNonEmptyArray() {
        Array<String> array = new Array<>(String.class, 3);
        Assert.assertEquals(3, array.count());
        Assert.assertEquals(false, array.isEmpty());
    }

    private void testGetAndSet() {
        Array<String> array = new Array<>(String.class, 3);

        array.set("first", 0);
        array.set("last", 2);

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        Assert.assertEquals("first", array.get(0));
        Assert.assertEquals(null, array.get(1));
        Assert.assertEquals("last", array.get(2));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> array.get(3));

        array.set(null, 0);
        Assert.assertEquals(null, array.get(0));
    }

    private void testWipe() {
        Array<String> array = new Array<>(String.class, 3);
        array.set("first", 0);
        array.set(null, 1);
        array.set("last", 2);

        Assert.assertEquals("first", array.get(0));
        Assert.assertEquals(null, array.get(1));
        Assert.assertEquals("last", array.get(2));

        array.wipe();

        Assert.assertEquals(null, array.get(0));
        Assert.assertEquals(null, array.get(1));
        Assert.assertEquals(null, array.get(2));
    }

    private void testContains() {
        Array<String> array = new Array<>(String.class, 3);
        array.set("first", 0);
        array.set(null, 1);
        array.set("last", 2);

        Assert.assertEquals(true, array.contains("first"));
        Assert.assertEquals(true, array.contains(null));
        Assert.assertEquals(true, array.contains("last"));
        Assert.assertEquals(false, array.contains("second"));

        array.set("second", 1);

        Assert.assertEquals(false, array.contains(null));
        Assert.assertEquals(true, array.contains("second"));

        array.wipe();

        Assert.assertEquals(false, array.contains("first"));
        Assert.assertEquals(true, array.contains(null));
        Assert.assertEquals(false, array.contains("last"));
        Assert.assertEquals(false, array.contains("second"));
    }

    private void testConstructors() {
        Array<String> array1 = new Array<>(String.class, "0", "1", "2");

        Assert.assertEquals("0", array1.get(0));
        Assert.assertEquals("1", array1.get(1));
        Assert.assertEquals("2", array1.get(2));

        Array<String> array2 = new Array<>(String.class, array1);
        array2.set("11", 1);

        Assert.assertEquals("0", array1.get(0));
        Assert.assertEquals("1", array1.get(1));
        Assert.assertEquals("2", array1.get(2));

        Assert.assertEquals("0", array2.get(0));
        Assert.assertEquals("11", array2.get(1));
        Assert.assertEquals("2", array2.get(2));
    }

    private void testIterator() {
        Array<Integer> array = new Array<>(Integer.class, 0, 1, 2);

        int i = 0;
        for(Integer item : array) {
            Assert.assertEquals(i, item);
            i++;
        }

        Assert.assertEquals(i, array.count());
    }
}
