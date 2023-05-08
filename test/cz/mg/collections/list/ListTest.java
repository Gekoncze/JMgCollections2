package cz.mg.collections.list;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.Assert;

import java.util.NoSuchElementException;
import java.util.Objects;

public @Test class ListTest {
    public static void main(String[] args) {
        System.out.print("Running " + ListTest.class.getSimpleName() + " ... ");

        ListTest test = new ListTest();
        test.testEmpty();
        test.testAdd();
        test.testAddCollectionFirst();
        test.testAddCollectionLast();
        test.testAddNext();
        test.testAddPrevious();
        test.testConstructors();
        test.testGet();
        test.testSet();
        test.testSetFirst();
        test.testSetLast();
        test.testRemove();
        test.testRemoveItem();
        test.testRemovePreviousNextItem();
        test.testRemoveIf();
        test.testRemoveItemIf();
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

        Assert.assertThatCode(() -> list.add(-1, "x")).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertThatCode(() -> list.add(1000, "x")).throwsException(ArrayIndexOutOfBoundsException.class);
    }

    private void testAddCollectionFirst() {
        List<Integer> list = new List<>();
        List<Integer> collection = new List<>(9, 1, null, 0);

        Assert.assertEquals(0, list.count());

        list.addCollectionFirst(collection);

        Assert.assertEquals(4, list.count());

        Assert.assertEquals(9, list.get(0));
        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(null, list.get(2));
        Assert.assertEquals(0, list.get(3));

        list.clear();

        Assert.assertEquals(0, list.count());

        list.addLast(null);
        list.addLast(44);
        list.addCollectionFirst(collection);

        Assert.assertEquals(6, list.count());

        Assert.assertEquals(9, list.get(0));
        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(null, list.get(2));
        Assert.assertEquals(0, list.get(3));
        Assert.assertEquals(null, list.get(4));
        Assert.assertEquals(44, list.get(5));
    }

    private void testAddCollectionLast() {
        List<Integer> list = new List<>();
        List<Integer> collection = new List<>(9, 1, null, 0);

        Assert.assertEquals(0, list.count());

        list.addCollectionLast(collection);

        Assert.assertEquals(4, list.count());

        Assert.assertEquals(9, list.get(0));
        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(null, list.get(2));
        Assert.assertEquals(0, list.get(3));

        list.clear();

        Assert.assertEquals(0, list.count());

        list.addLast(44);
        list.addLast(null);
        list.addCollectionLast(collection);

        Assert.assertEquals(6, list.count());

        Assert.assertEquals(44, list.get(0));
        Assert.assertEquals(null, list.get(1));
        Assert.assertEquals(9, list.get(2));
        Assert.assertEquals(1, list.get(3));
        Assert.assertEquals(null, list.get(4));
        Assert.assertEquals(0, list.get(5));
    }

    private void testAddNext() {
        List<String> list = new List<>("a");

        list.addNext(list.getFirstItem(), "b");
        Assert.assertEquals(2, list.count());
        Assert.assertEquals("a", list.get(0));
        Assert.assertEquals("b", list.get(1));

        list.addNext(list.getFirstItem(), "foo");
        Assert.assertEquals(3, list.count());
        Assert.assertEquals("a", list.get(0));
        Assert.assertEquals("foo", list.get(1));
        Assert.assertEquals("b", list.get(2));
    }

    private void testAddPrevious() {
        List<String> list = new List<>("z");

        list.addPrevious(list.getLastItem(), "y");
        Assert.assertEquals(2, list.count());
        Assert.assertEquals("y", list.get(0));
        Assert.assertEquals("z", list.get(1));

        list.addPrevious(list.getLastItem(), "foo");
        Assert.assertEquals(3, list.count());
        Assert.assertEquals("y", list.get(0));
        Assert.assertEquals("foo", list.get(1));
        Assert.assertEquals("z", list.get(2));
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

        Assert.assertThatCode(() -> emptyList.get(-1)).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertThatCode(() -> emptyList.get(0)).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertThatCode(() -> emptyList.get(1)).throwsException(ArrayIndexOutOfBoundsException.class);

        List<String> list = new List<>("a", "b", "c", "d", "e");

        Assert.assertThatCode(() -> list.get(-2)).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertThatCode(() -> list.get(-1)).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertEquals("a", list.getFirst());
        Assert.assertEquals("a", list.get(0));
        Assert.assertEquals("b", list.get(1));
        Assert.assertEquals("c", list.get(2));
        Assert.assertEquals("d", list.get(3));
        Assert.assertEquals("e", list.get(4));
        Assert.assertEquals("e", list.getLast());
        Assert.assertThatCode(() -> list.get(5)).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertThatCode(() -> list.get(6)).throwsException(ArrayIndexOutOfBoundsException.class);

        Assert.assertEquals(list.getItem(0), list.getFirstItem());
        Assert.assertEquals(list.getItem(1), list.getFirstItem().getNextItem());
        Assert.assertEquals(list.getItem(list.count() - 2), list.getLastItem().getPreviousItem());
        Assert.assertEquals(list.getItem(list.count() - 1), list.getLastItem());
    }

    private void testSetFirst() {
        List<String> list = new List<>("a", "b", "c");

        list.setFirst("aa");
        Assert.assertEquals("aa", list.getFirst());

        list.setFirst(null);
        Assert.assertEquals(null, list.getFirst());

        list.setFirst(null);
        Assert.assertEquals(null, list.getFirst());

        list.setFirst("aaa");
        Assert.assertEquals("aaa", list.getFirst());
    }

    private void testSetLast() {
        List<String> list = new List<>("a", "b", "c");

        list.setLast("cc");
        Assert.assertEquals("cc", list.getLast());

        list.setLast(null);
        Assert.assertEquals(null, list.getLast());

        list.setLast(null);
        Assert.assertEquals(null, list.getLast());

        list.setLast("ccc");
        Assert.assertEquals("ccc", list.getLast());
    }

    private void testSet() {
        List<String> list = new List<>(null, "1", null);

        Assert.assertThatCode(() -> list.set(-1, "x")).throwsException(ArrayIndexOutOfBoundsException.class);
        list.set(2, "2");
        list.set(1, null);
        Assert.assertThatCode(() -> list.set(3, "y")).throwsException(ArrayIndexOutOfBoundsException.class);

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

        Assert.assertThatCode(() -> list.remove(-1)).throwsException(ArrayIndexOutOfBoundsException.class);
        Assert.assertEquals("1", list.remove(1));
        Assert.assertEquals(4, list.count());
        Assert.assertThatCode(() -> list.remove(5)).throwsException(ArrayIndexOutOfBoundsException.class);

        Assert.assertNull(list.removeFirst());
        Assert.assertEquals(3, list.count());
        Assert.assertEquals("2", list.getFirst());
        Assert.assertNotNull(list.getFirstItem());
        Assert.assertEquals("2", list.getFirstItem().get());
        Assert.assertNull(list.getFirstItem().getPreviousItem());

        Assert.assertEquals("4", list.removeLast());
        Assert.assertEquals(2, list.count());
        Assert.assertEquals("2", list.getFirst());
        Assert.assertEquals("3", list.getLast());
        Assert.assertNotNull(list.getLastItem());
        Assert.assertEquals("3", list.getLastItem().get());
        Assert.assertNull(list.getLastItem().getNextItem());

        Assert.assertEquals("2", list.removeFirst());
        Assert.assertEquals(1, list.count());
        Assert.assertEquals(list.getFirstItem(), list.getLastItem());
        Assert.assertEquals(list.getFirstItem().get(), list.getLastItem().get());

        Assert.assertEquals("3", list.removeLast());
        Assert.assertEquals(0, list.count());
        Assert.assertEquals(true, list.isEmpty());
        Assert.assertNull(list.getFirstItem());
        Assert.assertNull(list.getLastItem());

        Assert.assertThatCode(() -> list.remove(0)).throwsException(ArrayIndexOutOfBoundsException.class);
    }

    private void testRemoveItem() {
        List<String> list = new List<>(null, "1", "2", "3", "4");
        List<String> unrelatedList = new List<>("x");
        ListItem<String> item0 = list.getFirstItem();
        ListItem<String> item1 = item0.getNextItem();
        ListItem<String> item2 = item1.getNextItem();
        ListItem<String> item3 = item2.getNextItem();
        ListItem<String> item4 = item3.getNextItem();

        Assert.assertThatCode(() -> list.removeItem(unrelatedList.getFirstItem()))
            .throwsException(IllegalArgumentException.class);
        Assert.assertEquals("1", list.removeItem(item1));
        Assert.assertEquals(4, list.count());

        Assert.assertNull(list.removeItem(item0));
        Assert.assertEquals(3, list.count());
        Assert.assertEquals("2", list.getFirst());
        Assert.assertNotNull(list.getFirstItem());
        Assert.assertEquals("2", list.getFirstItem().get());
        Assert.assertNull(list.getFirstItem().getPreviousItem());

        Assert.assertEquals("4", list.removeItem(item4));
        Assert.assertEquals(2, list.count());
        Assert.assertEquals("2", list.getFirst());
        Assert.assertEquals("3", list.getLast());
        Assert.assertNotNull(list.getLastItem());
        Assert.assertEquals("3", list.getLastItem().get());
        Assert.assertNull(list.getLastItem().getNextItem());

        Assert.assertEquals("2", list.removeItem(item2));
        Assert.assertEquals(1, list.count());
        Assert.assertEquals(list.getFirstItem(), list.getLastItem());
        Assert.assertEquals(list.getFirstItem().get(), list.getLastItem().get());

        Assert.assertEquals("3", list.removeItem(item3));
        Assert.assertEquals(0, list.count());
        Assert.assertEquals(true, list.isEmpty());
        Assert.assertNull(list.getFirstItem());
        Assert.assertNull(list.getLastItem());

        Assert.assertThatCode(() -> list.remove(0)).throwsException(ArrayIndexOutOfBoundsException.class);
    }

    private void testRemovePreviousNextItem() {
        List<String> list = new List<>(null, "1", "2", "3", "4");

        Assert.assertThatCode(() -> list.getFirstItem().removePrevious()).throwsException(NoSuchElementException.class);
        Assert.assertThatCode(() -> list.getLastItem().removeNext()).throwsException(NoSuchElementException.class);

        Assert.assertEquals("2", list.getItem(3).removePrevious());
        Assert.assertEquals(4, list.count());
        Assert.assertEquals(null, list.getFirst());
        Assert.assertEquals("4", list.getLast());

        Assert.assertEquals("4", list.getItem(2).removeNext());
        Assert.assertEquals(3, list.count());
        Assert.assertEquals(null, list.getFirst());
        Assert.assertEquals("3", list.getLast());

        Assert.assertEquals(null, list.getItem(1).removePrevious());
        Assert.assertEquals(2, list.count());
        Assert.assertEquals("1", list.getFirst());
        Assert.assertEquals("3", list.getLast());

        Assert.assertEquals("3", list.getFirstItem().removeNext());
        Assert.assertEquals(1, list.count());
        Assert.assertEquals("1", list.getFirst());
        Assert.assertEquals("1", list.getLast());

        Assert.assertThatCode(() -> list.getFirstItem().removePrevious()).throwsException(NoSuchElementException.class);
        Assert.assertThatCode(() -> list.getLastItem().removeNext()).throwsException(NoSuchElementException.class);
    }

    private void testRemoveIf() {
        String first = "first";
        String middle = "middle";
        String first2 = "first";
        String last = "last";

        List<String> list = new List<>(first, null, middle, first2, last);
        checkList(list, first, null, middle, first2, last);

        list.removeIf(Objects::isNull);
        checkList(list, first, middle, first2, last);

        list.removeIf(object -> false);
        checkList(list, first, middle, first2, last);

        list.removeIf(object -> object.equals(first));
        checkList(list, middle, last);

        list.removeIf(object -> object == first);
        checkList(list, middle, last);

        list.removeIf(object -> true);
        Assert.assertEquals(true, list.isEmpty());

        Assert.assertThatCode(() -> {
            list.removeIf(object -> true);
            Assert.assertEquals(true, list.isEmpty());
        }).doesNotThrowAnyException();
    }

    private void testRemoveItemIf() {
        String monday = "monday";
        String tuesday = "tuesday";
        String wednesday = "wednesday";
        String friday = "friday";

        List<String> list = new List<>(monday, tuesday, wednesday, null, friday);
        checkList(list, monday, tuesday, wednesday, null, friday);

        list.removeItemIf(Objects::isNull);
        checkList(list, monday, tuesday, wednesday, null, friday);

        list.removeItemIf(item -> item.get() == null);
        checkList(list, monday, tuesday, wednesday, friday);

        list.removeItemIf(item -> false);
        checkList(list, monday, tuesday, wednesday, friday);

        list.removeItemIf(item -> item.get().equals("tuesday"));
        checkList(list, monday, wednesday, friday);

        list.removeItemIf(item -> item.getNextItem() != null && item.getNextItem().get().equals("friday"));
        checkList(list, monday, friday);

        list.removeItemIf(item -> true);
        Assert.assertEquals(true, list.isEmpty());

        Assert.assertThatCode(() -> {
            list.removeItemIf(item -> true);
            Assert.assertEquals(true, list.isEmpty());
        }).doesNotThrowAnyException();
    }

    private void checkList(@Mandatory List<String> list, String... expectedItems) {
        Assert
            .assertThatCollections(new List<>(expectedItems), list)
            .areEqual();
    }

    private void testClear() {
        List<String> list = new List<>("0", "1", "2");
        Assert.assertEquals(3, list.count());

        list.clear();

        Assert.assertEquals(0, list.count());
        Assert.assertEquals(true, list.isEmpty());
        Assert.assertNull(list.getFirstItem());
        Assert.assertNull(list.getLastItem());
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
