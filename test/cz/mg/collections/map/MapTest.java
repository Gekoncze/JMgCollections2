package cz.mg.collections.map;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.Capacity;
import cz.mg.collections.pair.ReadablePair;
import cz.mg.test.Assert;
import cz.mg.collections.list.List;
import cz.mg.collections.pair.Pair;
import cz.mg.collections.components.CompareFunctions;
import cz.mg.collections.components.HashFunctions;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public @Test class MapTest {
    public static void main(String[] args) {
        System.out.print("Running " + MapTest.class.getSimpleName() + " ... ");

        MapTest test = new MapTest();
        test.testEmpty();
        test.testGetAndSet();
        test.testGetOrCreate();
        test.testConstructors();
        test.testClear();
        test.testIterator();
        test.testRemove();

        System.out.println("OK");
    }

    private void testEmpty() {
        Assert
            .assertThatCode(() -> new Map<String, String>(new Capacity(-1)))
            .throwsException(IllegalArgumentException.class);

        Assert
            .assertThatCode(() -> new Map<String, String>(new Capacity(0)))
            .throwsException(IllegalArgumentException.class);

        Map<String, String> map = new Map<>(new Capacity(1));
        Assert.assertEquals(0, map.count());
        Assert.assertEquals(true, map.isEmpty());
    }

    private void testGetAndSet() {
        Map<String, String> map = new Map<>(new Capacity(10));
        Assert.assertThatCode(() -> map.get("key")).throwsException(NoSuchElementException.class);
        Assert.assertEquals(null, map.getOptional("key"));
        Assert.assertEquals("my default value", map.getOptional("key", "my default value"));
        map.set("key", "value");
        map.set("key2", "value2");
        Assert.assertThatCode(() -> map.get("value")).throwsException(NoSuchElementException.class);
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

        TestClass k1 = new TestClass(1);
        TestClass k2 = new TestClass(1);

        Map<TestClass, String> map2 = new Map<>(
            new Capacity(10),
            CompareFunctions.REFERENCE(),
            HashFunctions.HASH_CODE()
        );
        map2.set(k1, "v1");
        map2.set(k2, "v2");

        Assert.assertEquals("v1", map2.get(k1));
        Assert.assertEquals("v2", map2.get(k2));
        Assert.assertEquals(2, map2.count());

        Map<TestClass, String> map3 = new Map<>(
            new Capacity(10),
            CompareFunctions.EQUALS(),
            HashFunctions.HASH_CODE()
        );
        map3.set(k1, "v1");
        map3.set(k2, "v2");

        Assert.assertEquals("v2", map3.get(k1));
        Assert.assertEquals("v2", map3.get(k2));
        Assert.assertEquals(1, map3.count());
    }

    private void testGetOrCreate() {
        Map<Long, Pair<Long, Long>> map = new Map<>(new Capacity(10));
        Assert.assertEquals(true, map.isEmpty());
        Pair<Long, Long> pair = map.getOrCreate(1L, Pair::new);
        Assert.assertEquals(false, map.isEmpty());
        Assert.assertEquals(null, pair.getKey());
        Assert.assertEquals(null, pair.getValue());
        pair.setKey(7L);
        pair.setValue(77L);
        Assert.assertEquals(7L, map.getOrCreate(1L, Pair::new).getKey());
        Assert.assertEquals(77L, map.getOrCreate(1L, Pair::new).getValue());
    }

    private void testConstructors() {
        Map<String, String> map1 = new Map<>(
            new Capacity(5),
            new Pair<>("key1", "value1"), new Pair<>("key2", "value2")
        );
        Assert.assertEquals(2, map1.count());
        Assert.assertEquals(false, map1.isEmpty());
        Assert.assertEquals("value1", map1.get("key1"));
        Assert.assertEquals("value2", map1.get("key2"));

        List<Pair<String, String>> pairs = new List<>(new Pair<>("k1", "v1"), new Pair<>("k2", "v2"));
        Map<String, String> map2 = new Map<>(new Capacity(5), pairs);
        Assert.assertEquals(false, map2.isEmpty());

        map2.set("k1", null);

        Assert.assertEquals(2, map2.count());
        Assert.assertEquals(null, map2.get("k1"));
        Assert.assertEquals("v2", map2.get("k2"));
        Assert.assertEquals("k1", pairs.getFirst().getKey());
        Assert.assertEquals("v1", pairs.getFirst().getValue());

        Map<String, String> map3 = new Map<>(new Capacity(5), map1);
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
        Assert.assertThatCode(() -> map1.get("key3")).throwsException(NoSuchElementException.class);
    }

    private void testClear() {
        Map<String, String> map = new Map<>(
            new Capacity(5),
            new Pair<>("key", "value"),
            new Pair<>("k", "v")
        );
        Assert.assertEquals(false, map.isEmpty());
        Assert.assertEquals(2, map.count());

        map.clear();

        Assert.assertEquals(true, map.isEmpty());
        Assert.assertEquals(0, map.count());
        Assert.assertThatCode(() -> map.get("key")).throwsException(NoSuchElementException.class);
        Assert.assertThatCode(() -> map.get("k")).throwsException(NoSuchElementException.class);
    }

    private void testIterator() {
        Map<String, Integer> map = new Map<>(
            new Capacity(10),
            new Pair<>("0", 0), new Pair<>("1", 1), new Pair<>("2", 2)
        );
        Iterator<ReadablePair<String, Integer>> iterator = map.iterator();

        Assert.assertEquals(true, iterator.hasNext());
        ReadablePair<String, Integer> first = iterator.next();
        Assert.assertEquals("0", first.getKey());
        Assert.assertEquals(0, first.getValue());

        Assert.assertEquals(true, iterator.hasNext());
        ReadablePair<String, Integer> second = iterator.next();
        Assert.assertEquals("1", second.getKey());
        Assert.assertEquals(1, second.getValue());

        Assert.assertEquals(true, iterator.hasNext());
        ReadablePair<String, Integer> third = iterator.next();
        Assert.assertEquals("2", third.getKey());
        Assert.assertEquals(2, third.getValue());

        Assert.assertEquals(false, iterator.hasNext());
        Assert.assertThatCode(iterator::next).throwsException(NoSuchElementException.class);
    }

    private void testRemove() {
        testRemove(new Map<>(new Capacity(1)));
        testRemove(new Map<>(new Capacity(2)));
        testRemove(new Map<>(new Capacity(3)));
        testRemove(new Map<>(new Capacity(5)));
        testRemove(new Map<>(new Capacity(7)));
        testRemove(new Map<>(new Capacity(10)));
        testRemove(new Map<>(new Capacity(50)));
        testRemove(new Map<>(new Capacity(100)));
    }

    private void testRemove(@Mandatory Map<String, Integer> map) {
        for (int i = 0; i < 50; i++) {
            map.set("" + i, i);
        }
        Assert.assertEquals(50, map.count());

        Assert.assertThatCode(() -> map.remove(null)).throwsException(NoSuchElementException.class);
        Assert.assertThatCode(() -> map.remove("foo")).throwsException(NoSuchElementException.class);
        Assert.assertEquals(50, map.count());

        map.set(null, -1);
        Assert.assertEquals(51, map.count());

        Assert.assertEquals(-1, map.remove(null));
        Assert.assertEquals(50, map.count());

        List<String> removedKeys = new List<>();
        testRemove(map, "0", removedKeys);
        testRemove(map, "1", removedKeys);
        testRemove(map, "49", removedKeys);
        testRemove(map, "20", removedKeys);
        testRemove(map, "19", removedKeys);
        testRemove(map, "18", removedKeys);
        testRemove(map, "2", removedKeys);
        testRemove(map, "27", removedKeys);
        testRemove(map, "48", removedKeys);

        for (int i = 0; i < 50; i++) {
            testRemove(map, "" + i, removedKeys);
        }
    }

    private void testRemove(
        @Mandatory Map<String, Integer> map,
        @Mandatory String key,
        @Mandatory List<String> removedKeys
    ) {
        if (removedKeys.contains(key)) {
            Assert.assertThatCode(() -> map.remove(key)).throwsException(NoSuchElementException.class);
            return;
        }

        int countBefore = map.count();
        Assert.assertEquals(Integer.parseInt(key), map.remove(key));
        Assert.assertEquals(countBefore - 1, map.count());
        removedKeys.addLast(key);

        for (int i = 0; i < 50; i++) {
            if (removedKeys.contains("" + i)) {
                int ii = i;
                Assert.assertThatCode(() -> map.get("" + ii)).throwsException(NoSuchElementException.class);
            } else {
                Assert.assertEquals(i, map.get("" + i));
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
