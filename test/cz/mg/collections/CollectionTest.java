package cz.mg.collections;

import cz.mg.annotations.classes.Data;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.Assert;

import java.util.Iterator;

public @Test class CollectionTest {
    public static void main(String[] args) {
        System.out.print("Running " + CollectionTest.class.getSimpleName() + " ... ");

        CollectionTest test = new CollectionTest();
        test.testIsEmpty();
        test.testContains();
        test.testContainsMatch();
        test.testDebug();

        System.out.println("OK");
    }

    private void testIsEmpty() {
        Assert.assertEquals(true, create().isEmpty());
        Assert.assertEquals(false, create((String) null).isEmpty());
        Assert.assertEquals(false, create("Foo").isEmpty());
    }

    private void testContains() {
        testContains(
            new String[]{},
            new String[]{null, "x"}
        );

        testContains(
            new String[]{ "Foo", "Bar", "FooBar" },
            new String[]{ "x", null }
        );

        testContains(
            new String[]{ null },
            new String[]{ "x" }
        );

        testContains(
            new String[]{ null, "Foo" },
            new String[]{ "x" }
        );

        testContains(
            new String[]{ "Foo", null },
            new String[]{ "x" }
        );

        testContains(
            new String[]{ null, null },
            new String[]{ "x" }
        );
    }

    private void testContains(String[] objects, String[] notContained) {
        Collection<String> collection = create(objects);

        for (String object : objects) {
            Assert.assertEquals(true, collection.contains(object));
        }

        for (String object : notContained) {
            Assert.assertEquals(false, collection.contains(object));
        }
    }

    private void testContainsMatch() {
        Collection<String> collection = create("one", "two", "three");
        Assert.assertEquals(true, collection.containsMatch(object -> object.startsWith("t")));
        Assert.assertEquals(false, collection.containsMatch(object -> object.startsWith("a")));
    }

    private @Mandatory Collection<String> create(String... objects) {
        return new TestCollection<>(objects);
    }

    private void testDebug() {
        Collection<String> collection = new TestCollection<>("a", "b", "c");
        Object[] debug = collection.debug();
        Assert.assertEquals(debug[0], "a");
        Assert.assertEquals(debug[1], "b");
        Assert.assertEquals(debug[2], "c");
    }

    private static @Data class TestCollection<T> extends Collection<T> {
        private final T[] objects;

        @SafeVarargs
        public TestCollection(T... objects) {
            this.objects = objects;
        }

        @Override
        public int count() {
            return objects.length;
        }

        @Override
        public @Mandatory Iterator<T> iterator() {
            return new Iterator<>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < objects.length;
                }

                @Override
                public T next() {
                    T object = objects[i];
                    i++;
                    return object;
                }
            };
        }
    }
}
