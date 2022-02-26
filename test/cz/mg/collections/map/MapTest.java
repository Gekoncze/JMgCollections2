package cz.mg.collections.map;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.Assert;
import cz.mg.collections.list.List;
import cz.mg.collections.pair.Pair;
import cz.mg.collections.utilities.CompareFunctions;
import cz.mg.collections.utilities.HashFunctions;

import java.util.NoSuchElementException;
import java.util.Objects;

public @Test class MapTest {
    public static void main(String[] args) {
        System.out.print("Running " + MapTest.class.getSimpleName() + " ... ");

        MapTest test = new MapTest();
        test.testEmpty();
        test.testGetAndSet();
        test.testConstructors();
        test.testClear();
        test.testIterator();

        System.out.println("OK");
    }

    private void testEmpty() {
        Assert.assertExceptionThrown(IllegalArgumentException.class, () -> new Map<String, String>(-1));
        Assert.assertExceptionThrown(IllegalArgumentException.class, () -> new Map<String, String>(0));

        Map<String, String> map = new Map<>(1);
        Assert.assertEquals(0, map.count());
        Assert.assertEquals(true, map.isEmpty());
    }

    private void testGetAndSet() {
        Map<String, String> map = new Map<>(10);
        Assert.assertExceptionThrown(NoSuchElementException.class, () -> map.get("key"));
        map.set("key", "value");
        map.set("key2", "value2");
        Assert.assertExceptionThrown(NoSuchElementException.class, () -> map.get("value"));
        Assert.assertEquals("value", map.get("key"));
        Assert.assertEquals("value2", map.get("key2"));
        Assert.assertEquals(false, map.isEmpty());
        Assert.assertEquals(2, map.count());
        map.set("key", "x");
        Assert.assertEquals("x", map.get("key"));
        Assert.assertEquals(2, map.count());
        map.set("key2", "x");
        Assert.assertEquals("x", map.get("key2"));
        Assert.assertEquals(2, map.count());
        map.set("key", null);
        Assert.assertEquals(null, map.get("key"));
        Assert.assertEquals(2, map.count());
        map.set(null, "null");
        Assert.assertEquals("null", map.get(null));
        Assert.assertEquals(3, map.count());
        map.set(null, null);
        Assert.assertEquals(null, map.get(null));
        Assert.assertEquals(3, map.count());

        for(int i = 0; i < 12; i++) {
            map.set("i key" + i, "i value" + i);
        }

        Assert.assertEquals(15, map.count());

        for(int i = 0; i < 12; i++) {
            Assert.assertEquals("i value" + i, map.get("i key" + i));
        }

        map.set("key", "key");
        Assert.assertEquals("key", map.get("key"));
    }

    private void testConstructors() {
        Map<String, String> map1 = new Map<>(5, new Pair<>("key1", "value1"), new Pair<>("key2", "value2"));
        Assert.assertEquals(2, map1.count());
        Assert.assertEquals(false, map1.isEmpty());
        Assert.assertEquals("value1", map1.get("key1"));
        Assert.assertEquals("value2", map1.get("key2"));

        List<Pair<String, String>> pairs = new List<>(new Pair<>("k1", "v1"), new Pair<>("k2", "v2"));
        Map<String, String> map2 = new Map<>(5, pairs);
        Assert.assertEquals(false, map2.isEmpty());

        map2.set("k1", null);

        Assert.assertEquals(2, map2.count());
        Assert.assertEquals(null, map2.get("k1"));
        Assert.assertEquals("v2", map2.get("k2"));
        Assert.assertEquals("k1", pairs.getFirst().getKey());
        Assert.assertEquals("v1", pairs.getFirst().getValue());

        Map<String, String> map3 = new Map<>(5, map1);
        Assert.assertEquals(false, map3.isEmpty());

        map3.set("key1", "value1x");
        map3.set("key3", "value3");

        Assert.assertEquals(3, map3.count());
        Assert.assertEquals("value1x", map3.get("key1"));
        Assert.assertEquals("value2", map3.get("key2"));
        Assert.assertEquals("value3", map3.get("key3"));
        Assert.assertEquals(2, map1.count());
        Assert.assertEquals("value1", map1.get("key1"));
        Assert.assertEquals("value2", map1.get("key2"));
        Assert.assertExceptionThrown(NoSuchElementException.class, () -> map1.get("key3"));

        TestClass k1 = new TestClass(1);
        TestClass k2 = new TestClass(1);

        Map<TestClass, String> map4 = new Map<>(10, CompareFunctions.REFERENCE, HashFunctions.HASH_CODE);
        map4.set(k1, "v1");
        map4.set(k2, "v2");

        Assert.assertEquals("v1", map4.get(k1));
        Assert.assertEquals("v2", map4.get(k2));

        Map<TestClass, String> map5 = new Map<>(10, CompareFunctions.EQUALS, HashFunctions.HASH_CODE);
        map5.set(k1, "v1");
        map5.set(k2, "v2");

        Assert.assertEquals("v2", map5.get(k1));
        Assert.assertEquals("v2", map5.get(k2));
    }

    private void testClear() {
        Map<String, String> map = new Map<>(5, new Pair<>("key", "value"), new Pair<>("k", "v"));
        Assert.assertEquals(false, map.isEmpty());
        Assert.assertEquals(2, map.count());

        map.clear();

        Assert.assertEquals(true, map.isEmpty());
        Assert.assertEquals(0, map.count());
        Assert.assertExceptionThrown(NoSuchElementException.class, () -> map.get("key"));
        Assert.assertExceptionThrown(NoSuchElementException.class, () -> map.get("k"));
    }

    private void testIterator() {
        Map<String, Integer> map = new Map<>(10, new Pair<>("0", 0), new Pair<>("1", 1), new Pair<>("2", 2));

        int i = 0;
        for(MapPair<String, Integer> pair : map) {
            Assert.assertEquals(i, map.get("" + i));
            i++;
        }

        Assert.assertEquals(3, i);
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
