package cz.mg.collections.map;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.pair.Pair;
import cz.mg.collections.pair.ReadablePair;
import cz.mg.test.Assert;

public @Test class MapsTest {
    public static void main(String[] args) {
        System.out.print("Running " + MapsTest.class.getSimpleName() + " ... ");

        MapsTest test = new MapsTest();
        test.testCreate();

        System.out.println("OK");
    }

    private void testCreate() {
        Map<Integer, String> a = new Map<>();
        a.set(3, "3");
        a.set(1, "1");
        a.set(2, "2");
        Map<Integer, String> b = Maps.create(new Pair<>(2, "2"), new Pair<>(3, "3"), new Pair<>(1, "1"));
        verify(a, b);
    }

    private void verify(Map<Integer, String> expectations, Map<Integer, String> reality) {
        Assert.assertEquals(expectations.count(), reality.count());
        for (ReadablePair<Integer, String> expectation : expectations) {
            String value = reality.get(expectation.getKey());
            Assert.assertEquals(expectation.getValue(), value);
        }
    }
}
