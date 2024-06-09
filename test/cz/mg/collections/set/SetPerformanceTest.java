package cz.mg.collections.set;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.pair.Pair;
import cz.mg.test.Assert;

import java.text.DecimalFormat;
import java.util.HashSet;

public @Test class SetPerformanceTest {
    private static final int LEVELS = 6;
    private static final int ITERATIONS = 10;

    public static void main(String[] args) {
        SetPerformanceTest test = new SetPerformanceTest();
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
        System.out.print("Running " + SetPerformanceTest.class.getSimpleName() + " with count " + count + " ... ");
        SetPerformanceTest test = new SetPerformanceTest();
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

        Set<Integer> set = new Set<>();

        for (int i = 0; i < count; i++) {
            set.set(i);
        }

        Assert.assertEquals(count, set.count());

        for (int i = 0; i < count; i++) {
            set.unset(i);
        }

        Assert.assertEquals(0, set.count());

        long end = time();
        return end - begin;
    }

    private long testJavaPerformance(int count) {
        long begin = time();

        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < count; i++) {
            set.add(i);
        }

        Assert.assertEquals(count, set.size());

        for (int i = 0; i < count; i++) {
            set.remove(i);
        }

        Assert.assertEquals(0, set.size());

        long end = time();
        return end - begin;
    }

    private long time() {
        return System.nanoTime();
    }
}
