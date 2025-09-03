package cz.mg.collections.map;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.collections.pair.Pair;
import cz.mg.collections.pair.ReadablePair;
import cz.mg.functions.EqualsFunctions;
import cz.mg.functions.HashFunctions;
import cz.mg.test.Assert;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public @Test class MapTest {
    public static void main(String[] args) {
        System.out.print("Running " + MapTest.class.getSimpleName() + " ... ");

        MapTest test = new MapTest();
        test.testEmpty();
        test.testGetAndSet();
        test.testGetOptionalDefault();
        test.testGetOrCreate();
        test.testConstructors();
        test.testClear();
        test.testIterator();
        test.testUnset();
        test.testResize();

        System.out.println("OK");
    }

    private void testEmpty() {
        Map<String, String> map = new Map<>();
        Assert.assertEquals(0, map.count());
        Assert.assertEquals(true, map.isEmpty());
    }

    private void testGetAndSet() {
        Map<String, String> map = new Map<>();
        Assert.assertException(() -> map.get("key"), NoSuchElementException.class);
        Assert.assertEquals(null, map.getOptional("key"));
        Assert.assertEquals("my default value", map.getOrDefault("key", "my default value"));
        map.set("key", "value");
        map.set("key2", "value2");
        Assert.assertException(() -> map.get("value"), NoSuchElementException.class);
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
            EqualsFunctions.REFERENCE(),
            HashFunctions.HASH_CODE()
        );
        map2.set(k1, "v1");
        map2.set(k2, "v2");

        Assert.assertEquals("v1", map2.get(k1));
        Assert.assertEquals("v2", map2.get(k2));
        Assert.assertEquals(2, map2.count());

        Map<TestClass, String> map3 = new Map<>(
            EqualsFunctions.EQUALS(),
            HashFunctions.HASH_CODE()
        );
        map3.set(k1, "v1");
        map3.set(k2, "v2");

        Assert.assertEquals("v2", map3.get(k1));
        Assert.assertEquals("v2", map3.get(k2));
        Assert.assertEquals(1, map3.count());
    }

    private void testGetOptionalDefault() {
        Map<Long, Pair<Long, Long>> map = new Map<>();
        Assert.assertEquals(true, map.isEmpty());
        Assert.assertNull(map.getOptional(1L));
        Assert.assertNotNull(map.getOrDefault(1L, new Pair<>()));
        Assert.assertEquals(true, map.isEmpty());
        Pair<Long, Long> pair = new Pair<>(7L, 77L);
        map.set(1L, pair);
        Assert.assertEquals(1, map.count());
        Assert.assertSame(pair, map.getOptional(1L));
        Assert.assertSame(pair, map.getOrDefault(1L, new Pair<>()));
        Assert.assertEquals(1, map.count());
    }

    private void testGetOrCreate() {
        Map<Long, Pair<Long, Long>> map = new Map<>();
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
        Map<String, String> map1 = Maps.create(
            new Pair<>("key1", "value1"),
            new Pair<>("key2", "value2")
        );
        Assert.assertEquals(2, map1.count());
        Assert.assertEquals(false, map1.isEmpty());
        Assert.assertEquals("value1", map1.get("key1"));
        Assert.assertEquals("value2", map1.get("key2"));

        List<Pair<String, String>> pairs = new List<>(new Pair<>("k1", "v1"), new Pair<>("k2", "v2"));
        Map<String, String> map2 = new Map<>(pairs);
        Assert.assertEquals(false, map2.isEmpty());

        map2.set("k1", null);

        Assert.assertEquals(2, map2.count());
        Assert.assertEquals(null, map2.get("k1"));
        Assert.assertEquals("v2", map2.get("k2"));
        Assert.assertEquals("k1", pairs.getFirst().getKey());
        Assert.assertEquals("v1", pairs.getFirst().getValue());

        Map<String, String> map3 = new Map<>(map1);
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
        Assert.assertException(() -> map1.get("key3"), NoSuchElementException.class);

        Map<String, String> map4 = new Map<>(
            new Pair<>("a", "1"),
            new Pair<>("b", "2"),
            new Pair<>("c", "3"),
            new Pair<>("d", "4")
        );

        Assert.assertEquals(4, map4.count());
        Assert.assertEquals("1", map4.get("a"));
        Assert.assertEquals("2", map4.get("b"));
        Assert.assertEquals("3", map4.get("c"));
        Assert.assertEquals("4", map4.get("d"));
    }

    private void testClear() {
        Map<String, String> map = Maps.create(
            new Pair<>("key", "value"),
            new Pair<>("k", "v")
        );
        Assert.assertEquals(false, map.isEmpty());
        Assert.assertEquals(2, map.count());

        map.clear();

        Assert.assertEquals(true, map.isEmpty());
        Assert.assertEquals(0, map.count());
        Assert.assertException(() -> map.get("key"), NoSuchElementException.class);
        Assert.assertException(() -> map.get("k"), NoSuchElementException.class);
    }

    private void testIterator() {
        Map<String, Integer> map = Maps.create(
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
        Assert.assertException(iterator::next, NoSuchElementException.class);
    }

    private void testUnset() {
        Map<String, Integer> map = new Map<>();
        for (int i = 0; i < 50; i++) {
            map.set("" + i, i);
        }
        Assert.assertEquals(50, map.count());

        Assert.assertException(() -> map.unset(null), NoSuchElementException.class);
        Assert.assertException(() -> map.unset("foo"), NoSuchElementException.class);
        Assert.assertEquals(50, map.count());

        map.set(null, -1);
        Assert.assertEquals(51, map.count());

        Assert.assertEquals(-1, map.unset(null));
        Assert.assertEquals(50, map.count());

        List<String> removedKeys = new List<>();
        testUnset(map, "0", removedKeys);
        testUnset(map, "1", removedKeys);
        testUnset(map, "49", removedKeys);
        testUnset(map, "20", removedKeys);
        testUnset(map, "19", removedKeys);
        testUnset(map, "18", removedKeys);
        testUnset(map, "2", removedKeys);
        testUnset(map, "27", removedKeys);
        testUnset(map, "48", removedKeys);

        for (int i = 0; i < 50; i++) {
            testUnset(map, "" + i, removedKeys);
        }
    }

    private void testUnset(
        @Mandatory Map<String, Integer> map,
        @Mandatory String key,
        @Mandatory List<String> removedKeys
    ) {
        if (removedKeys.contains(key)) {
            Assert.assertException(() -> map.unset(key), NoSuchElementException.class);
            return;
        }

        int countBefore = map.count();
        Assert.assertEquals(Integer.parseInt(key), map.unset(key));
        Assert.assertEquals(countBefore - 1, map.count());
        removedKeys.addLast(key);

        for (int i = 0; i < 50; i++) {
            if (removedKeys.contains("" + i)) {
                int ii = i;
                Assert.assertException(() -> map.get("" + ii), NoSuchElementException.class);
            } else {
                Assert.assertEquals(i, map.get("" + i));
            }
        }
    }

    private void testResize() {
        Map<Integer, String> map = new Map<>();
        Assert.assertEquals(0, map.load());

        map.set(0, "0");
        verifyLoad(map);

        int lastLoad = map.load();

        for (int i = 1; i < 50; i++) {
            map.set(i, "" + i);
            verifyExpand(map, lastLoad, i, "" + i);
            lastLoad = map.load();
        }

        for (int i = 0; i < 49; i++) {
            map.unset(i);
            verifyShrink(map, lastLoad, i, "" + i);
            lastLoad = map.load();
        }

        map.unset(49);
        Assert.assertEquals(0, map.load());
    }

    private void verifyExpand(@Mandatory Map<Integer, String> map, int lastLoad, int i, String s) {
        verifyLoad(map);

        int newLoad = map.load();
        if (newLoad < lastLoad) {
            map.unset(i);
            Assert.assertNotEquals(lastLoad, map.load());
            map.set(i, s);
            Assert.assertEquals(newLoad, map.load());
        }
    }

    private void verifyShrink(@Mandatory Map<Integer, String> map, int lastLoad, int i, String s) {
        verifyLoad(map);

        int newLoad = map.load();
        if (newLoad > lastLoad) {
            map.set(i, s);
            Assert.assertNotEquals(lastLoad, map.load());
            map.unset(i);
            Assert.assertEquals(newLoad, map.load());
        }
    }

    private void verifyLoad(@Mandatory Map<Integer, String> map) {
        Assert.assertEquals(true, map.load() >= Map.MIN_LOAD && map.load() <= Map.MAX_LOAD);
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
