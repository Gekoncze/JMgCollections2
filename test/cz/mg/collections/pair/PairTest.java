package cz.mg.collections.pair;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.Assert;

public @Test class PairTest {
    public static void main(String[] args) {
        System.out.print("Running " + PairTest.class.getSimpleName() + " ... ");

        PairTest test = new PairTest();
        test.constructors();
        test.testGetAndSet();

        System.out.println("OK");
    }

    private void constructors() {
        Pair<String, String> pair1 = new Pair<>();
        Assert.assertEquals(null, pair1.getKey());
        Assert.assertEquals(null, pair1.getValue());

        Pair<String, String> pair2 = new Pair<>("a", "b");
        Assert.assertEquals("a", pair2.getKey());
        Assert.assertEquals("b", pair2.getValue());

        Pair<String, String> pair3 = new Pair<>(null, "b");
        Assert.assertEquals(null, pair3.getKey());
        Assert.assertEquals("b", pair3.getValue());

        Pair<String, String> pair4 = new Pair<>("a", null);
        Assert.assertEquals("a", pair4.getKey());
        Assert.assertEquals(null, pair4.getValue());

        Pair<String, String> pair5 = new Pair<>(null, null);
        Assert.assertEquals(null, pair5.getKey());
        Assert.assertEquals(null, pair5.getValue());
    }

    private void testGetAndSet() {
        Pair<String, String> pair = new Pair<>();

        pair.setKey("key");

        Assert.assertEquals("key", pair.getKey());
        Assert.assertEquals(null, pair.getValue());

        pair.setValue("value");

        Assert.assertEquals("key", pair.getKey());
        Assert.assertEquals("value", pair.getValue());

        pair.setKey(null);

        Assert.assertEquals(null, pair.getKey());
        Assert.assertEquals("value", pair.getValue());

        pair.setValue(null);

        Assert.assertEquals(null, pair.getKey());
        Assert.assertEquals(null, pair.getValue());
    }
}
