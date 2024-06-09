package cz.mg.collections.set;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.CompareFunctions;
import cz.mg.collections.components.HashFunctions;
import cz.mg.collections.list.List;
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
        test.testUnset();
        test.testResize();

        System.out.println("OK");
    }

    private void testEmpty() {
        Set<String> map = new Set<>();
        Assert.assertEquals(0, map.count());
        Assert.assertEquals(true, map.isEmpty());
    }

    private void testContainsAndSet() {
        Set<String> set = new Set<>();
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

        Set<TestClass> set2 = new Set<>(CompareFunctions.REFERENCE(), HashFunctions.HASH_CODE());
        set2.set(v1);
        set2.set(v2);

        Assert.assertEquals(true, set2.contains(v1));
        Assert.assertEquals(true, set2.contains(v2));
        Assert.assertEquals(2, set2.count());

        Set<TestClass> set3 = new Set<>(CompareFunctions.EQUALS(), HashFunctions.HASH_CODE());
        set3.set(v1);
        set3.set(v2);

        Assert.assertEquals(true, set3.contains(v1));
        Assert.assertEquals(true, set3.contains(v2));
        Assert.assertEquals(1, set3.count());
    }

    private void testConstructors() {
        Set<String> set1 = new Set<>("value1", "value2", null);
        Assert.assertEquals(3, set1.count());
        Assert.assertEquals(false, set1.isEmpty());
        Assert.assertEquals(true, set1.contains("value1"));
        Assert.assertEquals(true, set1.contains("value2"));
        Assert.assertEquals(true, set1.contains(null));

        List<String> listValues = new List<>("v1", "v2", null);
        Set<String> set2 = new Set<>(listValues);
        Assert.assertEquals(3, set2.count());
        Assert.assertEquals(false, set2.isEmpty());
        Assert.assertEquals(true, set2.contains("v1"));
        Assert.assertEquals(true, set2.contains("v2"));
        Assert.assertEquals(true, set2.contains(null));

        Set<String> map3 = new Set<>(set1);
        Assert.assertEquals(3, set2.count());
        Assert.assertEquals(false, map3.isEmpty());
        Assert.assertEquals(true, set1.contains("value1"));
        Assert.assertEquals(true, set1.contains("value2"));
        Assert.assertEquals(true, set1.contains(null));
    }

    private void testClear() {
        Set<String> set = new Set<>("value", "v");
        Assert.assertEquals(false, set.isEmpty());
        Assert.assertEquals(2, set.count());

        set.clear();

        Assert.assertEquals(true, set.isEmpty());
        Assert.assertEquals(0, set.count());
        Assert.assertEquals(false, set.contains("value"));
        Assert.assertEquals(false, set.contains("v"));
    }

    private void testIterator() {
        Set<Integer> set = new Set<>(0, 1, 2);
        Iterator<Integer> iterator = set.iterator();

        Assert.assertEquals(true, iterator.hasNext());
        Assert.assertEquals(0, iterator.next());

        Assert.assertEquals(true, iterator.hasNext());
        Assert.assertEquals(1, iterator.next());

        Assert.assertEquals(true, iterator.hasNext());
        Assert.assertEquals(2, iterator.next());

        Assert.assertEquals(false, iterator.hasNext());
        Assert.assertThatCode(iterator::next).throwsException(NoSuchElementException.class);
    }

    private void testUnset() {
        Set<Integer> set = new Set<>();
        for (int i = 0; i < 50; i++) {
            set.set(i);
        }
        Assert.assertEquals(50, set.count());

        Assert.assertThatCode(() -> set.unset(null)).throwsException(NoSuchElementException.class);
        Assert.assertThatCode(() -> set.unset(51)).throwsException(NoSuchElementException.class);
        Assert.assertEquals(50, set.count());

        set.set(null);
        Assert.assertEquals(51, set.count());

        Assert.assertThatCode(() -> set.unset(null)).doesNotThrowAnyException();
        Assert.assertEquals(50, set.count());

        List<Integer> removedValues = new List<>();
        testUnset(set, 0, removedValues);
        testUnset(set, 1, removedValues);
        testUnset(set, 49, removedValues);
        testUnset(set, 20, removedValues);
        testUnset(set, 19, removedValues);
        testUnset(set, 18, removedValues);
        testUnset(set, 2, removedValues);
        testUnset(set, 27, removedValues);
        testUnset(set, 48, removedValues);

        for (int i = 0; i < 50; i++) {
            testUnset(set, i, removedValues);
        }
    }

    private void testUnset(
        @Mandatory Set<Integer> set,
        @Mandatory Integer value,
        @Mandatory List<Integer> removedValues
    ) {
        if (removedValues.contains(value)) {
            Assert.assertThatCode(() -> set.unset(value)).throwsException(NoSuchElementException.class);
            return;
        }

        int countBefore = set.count();
        Assert.assertThatCode(() -> set.unset(value)).doesNotThrowAnyException();
        Assert.assertEquals(countBefore - 1, set.count());
        removedValues.addLast(value);

        for (int i = 0; i < 50; i++) {
            if (!removedValues.contains(i)) {
                Assert.assertEquals(true, set.contains(i));
            }
        }
    }

    private void testResize() {
        Set<Integer> set = new Set<>();
        Assert.assertEquals(0, set.load());

        set.set(0);
        verifyLoad(set);

        int lastLoad = set.load();

        for (int i = 1; i < 50; i++) {
            set.set(i);
            verifyExpand(set, lastLoad, i);
            lastLoad = set.load();
        }

        for (int i = 0; i < 49; i++) {
            set.unset(i);
            verifyShrink(set, lastLoad, i);
            lastLoad = set.load();
        }

        set.unset(49);
        Assert.assertEquals(0, set.load());
    }

    private void verifyExpand(@Mandatory Set<Integer> set, int lastLoad, int i) {
        verifyLoad(set);

        int newLoad = set.load();
        if (newLoad < lastLoad) {
            set.unset(i);
            Assert.assertNotEquals(lastLoad, set.load());
            set.set(i);
            Assert.assertEquals(newLoad, set.load());
        }
    }

    private void verifyShrink(@Mandatory Set<Integer> set, int lastLoad, int i) {
        verifyLoad(set);

        int newLoad = set.load();
        if (newLoad > lastLoad) {
            set.set(i);
            Assert.assertNotEquals(lastLoad, set.load());
            set.unset(i);
            Assert.assertEquals(newLoad, set.load());
        }
    }

    private void verifyLoad(@Mandatory Set<Integer> set) {
        Assert.assertEquals(true, set.load() >= Set.MIN_LOAD && set.load() <= Set.MAX_LOAD);
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
