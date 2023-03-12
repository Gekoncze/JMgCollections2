package cz.mg.collections.set;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.collections.utilities.CompareFunctions;
import cz.mg.collections.utilities.HashFunctions;
import cz.mg.test.Assert;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public @Test class SetTest {
    public static void main(String[] args) {
        System.out.print("Running " + SetTest.class.getSimpleName() + " ... ");

        SetTest test = new SetTest();
        test.testEmpty();
        test.testContainsAndSet();
        test.testConstructors();
        test.testClear();
        test.testIterator();
        test.testRemove();

        System.out.println("OK");
    }

    private void testEmpty() {
        Assert.assertExceptionThrown(IllegalArgumentException.class, () -> new Set<String>(-1));
        Assert.assertExceptionThrown(IllegalArgumentException.class, () -> new Set<String>(0));

        Set<String> map = new Set<>(1);
        Assert.assertEquals(0, map.count());
        Assert.assertEquals(true, map.isEmpty());
    }

    private void testContainsAndSet() {
        Set<String> set = new Set<>(10);
        Assert.assertEquals(false, set.contains("value"));
        set.set("value");
        Assert.assertEquals(true, set.contains("value"));
        Assert.assertEquals(false, set.isEmpty());
        Assert.assertEquals(1, set.count());
        set.set("value2");
        Assert.assertEquals(true, set.contains("value"));
        Assert.assertEquals(true, set.contains("value2"));
        Assert.assertEquals(false, set.isEmpty());
        Assert.assertEquals(2, set.count());
        set.set("value");
        Assert.assertEquals(true, set.contains("value"));
        Assert.assertEquals(2, set.count());
        set.set("value2");
        Assert.assertEquals(true, set.contains("value2"));
        Assert.assertEquals(2, set.count());
        set.set(null);
        Assert.assertEquals(true, set.contains(null));
        Assert.assertEquals(3, set.count());

        for(int i = 0; i < 12; i++) {
            set.set("i value" + i);
        }

        Assert.assertEquals(15, set.count());

        for(int i = 0; i < 12; i++) {
            Assert.assertEquals(true, set.contains("i value" + i));
        }

        TestClass v1 = new TestClass(1);
        TestClass v2 = new TestClass(1);

        Set<TestClass> set2 = new Set<>(10, CompareFunctions.REFERENCE(), HashFunctions.HASH_CODE());
        set2.set(v1);
        set2.set(v2);

        Assert.assertEquals(true, set2.contains(v1));
        Assert.assertEquals(true, set2.contains(v2));
        Assert.assertEquals(2, set2.count());

        Set<TestClass> set3 = new Set<>(10, CompareFunctions.EQUALS(), HashFunctions.HASH_CODE());
        set3.set(v1);
        set3.set(v2);

        Assert.assertEquals(true, set3.contains(v1));
        Assert.assertEquals(true, set3.contains(v2));
        Assert.assertEquals(1, set3.count());
    }

    private void testConstructors() {
        Set<String> set1 = new Set<>(5, "value1", "value2", null);
        Assert.assertEquals(3, set1.count());
        Assert.assertEquals(false, set1.isEmpty());
        Assert.assertEquals(true, set1.contains("value1"));
        Assert.assertEquals(true, set1.contains("value2"));
        Assert.assertEquals(true, set1.contains(null));

        List<String> listValues = new List<>("v1", "v2", null);
        Set<String> set2 = new Set<>(5, listValues);
        Assert.assertEquals(3, set2.count());
        Assert.assertEquals(false, set2.isEmpty());
        Assert.assertEquals(true, set2.contains("v1"));
        Assert.assertEquals(true, set2.contains("v2"));
        Assert.assertEquals(true, set2.contains(null));

        Set<String> map3 = new Set<>(5, set1);
        Assert.assertEquals(3, set2.count());
        Assert.assertEquals(false, map3.isEmpty());
        Assert.assertEquals(true, set1.contains("value1"));
        Assert.assertEquals(true, set1.contains("value2"));
        Assert.assertEquals(true, set1.contains(null));
    }

    private void testClear() {
        Set<String> set = new Set<>(5, "value", "v");
        Assert.assertEquals(false, set.isEmpty());
        Assert.assertEquals(2, set.count());

        set.clear();

        Assert.assertEquals(true, set.isEmpty());
        Assert.assertEquals(0, set.count());
        Assert.assertEquals(false, set.contains("value"));
        Assert.assertEquals(false, set.contains("v"));
    }

    private void testIterator() {
        Set<Integer> set = new Set<>(10, 0, 1, 2);
        Iterator<Integer> iterator = set.iterator();

        Assert.assertEquals(true, iterator.hasNext());
        Assert.assertEquals(0, iterator.next());

        Assert.assertEquals(true, iterator.hasNext());
        Assert.assertEquals(1, iterator.next());

        Assert.assertEquals(true, iterator.hasNext());
        Assert.assertEquals(2, iterator.next());

        Assert.assertEquals(false, iterator.hasNext());
        Assert.assertExceptionThrown(NoSuchElementException.class, iterator::next);
    }

    private void testRemove() {
        testRemove(new Set<>(1));
        testRemove(new Set<>(2));
        testRemove(new Set<>(3));
        testRemove(new Set<>(5));
        testRemove(new Set<>(7));
        testRemove(new Set<>(10));
        testRemove(new Set<>(50));
        testRemove(new Set<>(100));
    }

    private void testRemove(@Mandatory Set<Integer> set) {
        for (int i = 0; i < 50; i++) {
            set.set(i);
        }
        Assert.assertEquals(50, set.count());

        Assert.assertExceptionThrown(NoSuchElementException.class, () -> set.remove(null));
        Assert.assertExceptionThrown(NoSuchElementException.class, () -> set.remove(51));
        Assert.assertEquals(50, set.count());

        set.set(null);
        Assert.assertEquals(51, set.count());

        Assert.assertExceptionNotThrown(() -> set.remove(null));
        Assert.assertEquals(50, set.count());

        List<Integer> removedValues = new List<>();
        testRemove(set, 0, removedValues);
        testRemove(set, 1, removedValues);
        testRemove(set, 49, removedValues);
        testRemove(set, 20, removedValues);
        testRemove(set, 19, removedValues);
        testRemove(set, 18, removedValues);
        testRemove(set, 2, removedValues);
        testRemove(set, 27, removedValues);
        testRemove(set, 48, removedValues);

        for (int i = 0; i < 50; i++) {
            testRemove(set, i, removedValues);
        }
    }

    private void testRemove(
        @Mandatory Set<Integer> set,
        @Mandatory Integer value,
        @Mandatory List<Integer> removedValues
    ) {
        if (removedValues.contains(value)) {
            Assert.assertExceptionThrown(NoSuchElementException.class, () -> set.remove(value));
            return;
        }

        int countBefore = set.count();
        Assert.assertExceptionNotThrown(() -> set.remove(value));
        Assert.assertEquals(countBefore - 1, set.count());
        removedValues.addLast(value);

        for (int i = 0; i < 50; i++) {
            if (!removedValues.contains(i)) {
                Assert.assertEquals(true, set.contains(i));
            }
        }
    }

    private static class TestClass {
        private final int value;

        public TestClass(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return value == testClass.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
