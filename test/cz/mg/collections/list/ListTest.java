package cz.mg.collections.list;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class ListTest {
    public static void main(String[] args) {
        System.out.print("Running " + ListTest.class.getSimpleName() + " ... ");

        ListTest test = new ListTest();
        test.testEmpty();
        test.testAdd();
        test.testConstructors();
        test.testGet();
        test.testSet();
        test.testRemove();
        test.testClear();
        test.testIterator();
        test.testForwardIteration();
        test.testBackwardIteration();

        System.out.println("OK");
    }

    private void testEmpty() {
        List<String> list = new List<>();

        Assert.assertEquals(true, list.isEmpty());
        Assert.assertEquals(0, list.count());
        Assert.assertEquals(null, list.getFirstItem());
        Assert.assertEquals(null, list.getLastItem());
    }

    private void testAdd() {
        List<String> list = new List<>();
        list.addLast("first");

        Assert.assertNotNull(list.getFirstItem());
        Assert.assertNotNull(list.getLastItem());
        Assert.assertEquals("first", list.getFirstItem().get());
        Assert.assertEquals("first", list.getLastItem().get());
        Assert.assertEquals(null, list.getFirstItem().getPreviousItem());
        Assert.assertEquals(null, list.getFirstItem().getNextItem());

        list.addLast("last");

        Assert.assertEquals(2, list.count());
        Assert.assertEquals(false, list.isEmpty());
        Assert.assertEquals("first", list.getFirst());
        Assert.assertEquals("last", list.getLast());
        Assert.assertEquals("first", list.getFirstItem().get());
        Assert.assertEquals("last", list.getLastItem().get());
        Assert.assertEquals(null, list.getFirstItem().getPreviousItem());
        Assert.assertEquals(list.getLastItem(), list.getFirstItem().getNextItem());

        list.addFirst("0");

        Assert.assertEquals(3, list.count());
        Assert.assertEquals("0", list.get(0));
        Assert.assertEquals("first", list.get(1));
        Assert.assertEquals("last", list.get(2));
        Assert.assertEquals("0", list.getFirstItem().get());
        Assert.assertNotNull(list.getFirstItem().getNextItem());
        Assert.assertEquals(list.getFirstItem(), list.getFirstItem().getNextItem().getPreviousItem());

        list.add(2, "2");

        Assert.assertEquals(4, list.count());
        Assert.assertEquals("0", list.get(0));
        Assert.assertEquals("first", list.get(1));
        Assert.assertEquals("2", list.get(2));
        Assert.assertEquals("last", list.get(3));

        list.addLast(null);

        Assert.assertEquals(5, list.count());
        Assert.assertEquals(null, list.getLast());
        Assert.assertEquals(null, list.getLastItem().get());
    }


    private void testConstructors() {
        List<String> list1 = new List<>("0", "1", "2");

        Assert.assertNotNull(list1.getFirstItem());
        Assert.assertNotNull(list1.getLastItem());

        Assert.assertEquals(3, list1.count());
        Assert.assertEquals("0", list1.get(0));
        Assert.assertEquals("1", list1.get(1));
        Assert.assertEquals("2", list1.get(2));

        List<String> list2 = new List<>(list1);

        Assert.assertNotNull(list2.getFirstItem());
        Assert.assertNotNull(list2.getLastItem());

        list2.addLast("3");

        Assert.assertEquals(3, list1.count());
        Assert.assertEquals(4, list2.count());

        Assert.assertEquals("0", list2.get(0));
        Assert.assertEquals("1", list2.get(1));
        Assert.assertEquals("2", list2.get(2));
        Assert.assertEquals("3", list2.get(3));
    }

    private void testGet() {
        List<String> emptyList = new List<>();

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> emptyList.get(-1));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> emptyList.get(0));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> emptyList.get(1));

        List<String> list = new List<>("0", "1", "2");

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.get(-1));
        Assert.assertEquals("0", list.get(0));
        Assert.assertEquals("1", list.get(1));
        Assert.assertEquals("2", list.get(2));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.get(3));
    }

    private void testSet() {
        List<String> list = new List<>(null, "1", null);

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.set(-1, "x"));
        list.set(2, "2");
        list.set(1, null);
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.set(3, "y"));

        Assert.assertEquals(3, list.count());
        Assert.assertEquals(null, list.get(0));
        Assert.assertEquals(null, list.get(1));
        Assert.assertEquals("2", list.get(2));

        list.set(0, "0");
        list.set(2, "2");

        Assert.assertEquals("0", list.getFirst());
        Assert.assertEquals("2", list.getLast());
        Assert.assertNotNull(list.getFirstItem());
        Assert.assertNotNull(list.getLastItem());
        Assert.assertEquals("0", list.getFirstItem().get());
        Assert.assertEquals("2", list.getLastItem().get());

        list.getFirstItem().set("x");
        Assert.assertEquals("x", list.getFirst());
        Assert.assertEquals("x", list.getFirstItem().get());
    }

    private void testRemove() {
        List<String> list = new List<>(null, "1", "2", "3", "4");

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.remove(-1));
        Assert.assertEquals("1", list.remove(1));
        Assert.assertEquals(4, list.count());
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.remove(5));

        Assert.assertEquals(null, list.removeFirst());
        Assert.assertEquals(3, list.count());
        Assert.assertEquals("2", list.getFirst());
        Assert.assertNotNull(list.getFirstItem());
        Assert.assertEquals("2", list.getFirstItem().get());
        Assert.assertEquals(null, list.getFirstItem().getPreviousItem());

        Assert.assertEquals("4", list.removeLast());
        Assert.assertEquals(2, list.count());
        Assert.assertEquals("2", list.getFirst());
        Assert.assertEquals("3", list.getLast());
        Assert.assertNotNull(list.getLastItem());
        Assert.assertEquals("3", list.getLastItem().get());
        Assert.assertEquals(null, list.getLastItem().getNextItem());

        Assert.assertEquals("2", list.removeFirst());
        Assert.assertEquals(1, list.count());
        Assert.assertEquals(list.getFirstItem(), list.getLastItem());
        Assert.assertEquals(list.getFirstItem().get(), list.getLastItem().get());

        Assert.assertEquals("3", list.removeLast());
        Assert.assertEquals(0, list.count());
        Assert.assertEquals(true, list.isEmpty());
        Assert.assertEquals(null, list.getFirstItem());
        Assert.assertEquals(null, list.getLastItem());

        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> list.remove(0));
    }

    private void testClear() {
        List<String> list = new List<>("0", "1", "2");
        Assert.assertEquals(3, list.count());

        list.clear();

        Assert.assertEquals(0, list.count());
        Assert.assertEquals(true, list.isEmpty());
        Assert.assertEquals(null, list.getFirstItem());
        Assert.assertEquals(null, list.getLastItem());
    }

    private void testIterator() {
        List<Integer> list = new List<>(0, 1, 2);

        int i = 0;
        for(Integer item : list) {
            Assert.assertEquals(i, item);
            i++;
        }

        Assert.assertEquals(i, list.count());
    }

    private void testForwardIteration() {
        List<Integer> list = new List<>(0, 1, 2, 3, 4, 5);

        int i = 0;
        for(ListItem<Integer> item = list.getFirstItem(); item != null; item = item.getNextItem()) {
            Assert.assertEquals(i, item.get());
            i++;
        }

        Assert.assertEquals(6, i);
    }

    private void testBackwardIteration() {
        List<Integer> list = new List<>(0, 1, 2, 3, 4, 5);

        int i = 5;
        for(ListItem<Integer> item = list.getLastItem(); item != null; item = item.getPreviousItem()) {
            Assert.assertEquals(i, item.get());
            i--;
        }

        Assert.assertEquals(-1, i);
    }
}
