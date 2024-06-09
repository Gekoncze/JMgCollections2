package cz.mg.collections.map;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.pair.Pair;
import cz.mg.test.Assert;

import java.text.DecimalFormat;
import java.util.HashMap;

public @Test class MapPerformanceTest {
    private static final int LEVELS = 6;
    private static final int ITERATIONS = 10;

    public static void main(String[] args) {
        MapPerformanceTest test = new MapPerformanceTest();
        test.testPerformanceLevels();
    }

    private void testPerformanceLevels() {
        int count = 1;
        for (int i = 0; i < LEVELS; i++) {
            testPerformanceLevel(count);
            count *= 10;
        }
    }

    private void testPerformanceLevel(int count) {
        System.out.print("Running " + MapPerformanceTest.class.getSimpleName() + " with count " + count + " ... ");
        MapPerformanceTest test = new MapPerformanceTest();
        Pair<Long, Long> results = test.testPerformance(count);
        double percent = (double)results.getKey() / (double)results.getValue();
        String percentString = new DecimalFormat("#.##").format(percent);
        System.out.println(results.getKey() + " vs " + results.getValue() + " " + percentString + "x slower");
    }

    private Pair<Long, Long> testPerformance(int count) {
        long totalMg = 0;
        long totalJava = 0;

        testMgPerformance(count);
        testJavaPerformance(count);

        for (int i = 0; i < ITERATIONS; i++) {
            totalMg += testMgPerformance(count);
            totalJava += testJavaPerformance(count);
        }

        return new Pair<>(totalMg / ITERATIONS, totalJava / ITERATIONS);
    }

    private long testMgPerformance(int count) {
        long begin = time();

        Map<Integer, String> map = new Map<>();

        for (int i = 0; i < count; i++) {
            map.set(i, "" + i);
        }

        Assert.assertEquals(count, map.count());

        for (int i = 0; i < count; i++) {
            map.unset(i);
        }

        Assert.assertEquals(0, map.count());

        long end = time();
        return end - begin;
    }

    private long testJavaPerformance(int count) {
        long begin = time();

        HashMap<Integer, String> map = new HashMap<>();

        for (int i = 0; i < count; i++) {
            map.put(i, "" + i);
        }

        Assert.assertEquals(count, map.size());

        for (int i = 0; i < count; i++) {
            map.remove(i);
        }

        Assert.assertEquals(0, map.size());

        long end = time();
        return end - begin;
    }

    private long time() {
        return System.nanoTime();
    }
}
