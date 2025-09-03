package cz.mg.collections.services;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.functions.EqualsFunctions;
import cz.mg.test.Assert;

public @Test class CollectionComparatorTest {
    public static void main(String[] args) {
        System.out.print("Running " + CollectionComparatorTest.class.getSimpleName() + " ... ");

        CollectionComparatorTest test = new CollectionComparatorTest();
        test.testEquals();

        System.out.println("OK");
    }

    private void testEquals() {
        CollectionComparator comparator = CollectionComparator.getInstance();

        Assert.assertEquals(true, comparator.equals(
            new List<>(),
            new List<>(),
            EqualsFunctions.EQUALS()
        ));


        Assert.assertEquals(false, comparator.equals(
            new List<>(),
            new List<>("a"),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>("a"),
            new List<>(),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>("a"),
            new List<>("a"),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>("a", "b"),
            new List<>("a", "b"),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>("a", "b"),
            new List<>("b", "a"),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>("a", "b"),
            new List<>("a", "c"),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>("a", "b"),
            new List<>("a", "b")
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>("a", "b"),
            new List<>("b", "a")
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>("a", "b"),
            new List<>("a", "c")
        ));

        TestClass a = new TestClass(1);
        TestClass b = new TestClass(2);
        TestClass aa = new TestClass(1);

        Assert.assertEquals(true, comparator.equals(
            new List<>(a),
            new List<>(a),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(a),
            new List<>(b),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a),
            new List<>(aa),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a),
            new List<>(a),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(a),
            new List<>(b),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(a),
            new List<>(aa),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a, a, a),
            new List<>(a, a, a),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(a, b, aa),
            new List<>(aa, b, a),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a, b, aa),
            new List<>(aa, b, a),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>((Object) null),
            new List<>((Object) null),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>((Object) null),
            new List<>((Object) null)
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>((Object) null),
            new List<>((Object) null),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a, null),
            new List<>(a, null),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a, null),
            new List<>(a, null),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(a, null),
            new List<>(null, a),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(a, null),
            new List<>(null, a)
        ));

        Assert.assertEquals(false, comparator.equals(
            new List<>(null, null),
            new List<>(null, null, null),
            EqualsFunctions.REFERENCE()
        ));

        Assert.assertEquals(true, comparator.equals(
            new List<>(a),
            new List<>(a),
            (first, second) -> first.value == second.value
        ));

        List<String> list = new List<>();

        Assert.assertEquals(true, comparator.equals(
            list,
            list,
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            list,
            null,
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            null,
            list,
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            null,
            null,
            EqualsFunctions.EQUALS()
        ));

        list.addLast("7");

        Assert.assertEquals(true, comparator.equals(
            list,
            list,
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(true, comparator.equals(
            list,
            new Array<>("7"),
            EqualsFunctions.EQUALS()
        ));

        Assert.assertEquals(false, comparator.equals(
            list,
            new Array<>("x"),
            EqualsFunctions.EQUALS()
        ));
    }

    private static class TestClass {
        private final int value;

        public TestClass(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestClass) {
                TestClass t = (TestClass) o;
                return value == t.value;
            } else {
                return false;
            }
        }
    }
}
