package cz.mg.collections.set;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class SetsTest {
    public static void main(String[] args) {
        System.out.print("Running " + SetsTest.class.getSimpleName() + " ... ");

        SetsTest test = new SetsTest();
        test.testCreate();
        test.testUnion();

        System.out.println("OK");
    }

    private void testCreate() {
        Set<Integer> a = new Set<>();
        a.set(3);
        a.set(1);
        a.set(2);
        Set<Integer> b = Sets.create(2, 3, 1);
        verify(a, b);
    }

    private void testUnion() {
        verify(Sets.create(), Sets.union(Sets.create(), Sets.create()));
        verify(Sets.create(1), Sets.union(Sets.create(1), Sets.create()));
        verify(Sets.create(1), Sets.union(Sets.create(), Sets.create(1)));
        verify(Sets.create(1), Sets.union(Sets.create(1, 1, 1), Sets.create(1, 1)));
        verify(Sets.create(1, 2, 3), Sets.union(Sets.create(3), Sets.create(1, 2)));
        verify(Sets.create(1, 2, 3, 4), Sets.union(Sets.create(1, 3), Sets.create(2, 4)));
        verify(Sets.create(1, null), Sets.union(Sets.create((Integer)null), Sets.create(1)));
        verify(Sets.create((Integer) null), Sets.union(Sets.create((Integer)null), Sets.create(null, null)));
    }

    private void verify(Set<Integer> expectations, Set<Integer> reality) {
        Assert.assertEquals(expectations.count(), reality.count());
        for (Integer expectation : expectations) {
            Assert.assertEquals(true, reality.contains(expectation));
        }
    }
}
