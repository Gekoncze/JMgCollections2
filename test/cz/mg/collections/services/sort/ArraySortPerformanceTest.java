package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.collections.utilities.Direction;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public @Test class ArraySortPerformanceTest {
    private static final int ITERATIONS = 10;

    public static void main(String[] args) {
        System.out.println("Running " + ArraySortPerformanceTest.class.getSimpleName() + " ... ");

        ArraySortPerformanceTest test = new ArraySortPerformanceTest();
        test.testSortPerformance();
    }

    private void testSortPerformance() {
        testSortPerformance(1);
        testSortPerformance(10);
        testSortPerformance(100);
        testSortPerformance(1000);
        testSortPerformance(10000);
    }

    private void testSortPerformance(int count) {
        SimpleArraySort simpleArraySort = SimpleArraySort.getInstance();

        long[] mgTimes = new long[ITERATIONS];
        long[] javaTimes = new long[ITERATIONS];

        for (int i = 0; i < ITERATIONS; i++) {
            if (Thread.interrupted()) {
                return;
            }

            Array<Integer> mgArray = createArray(count, i);
            Object[] javaArray = createArray(count, i).getData();

            check(mgArray, javaArray);

            mgTimes[i] = measure(() -> simpleArraySort.sort(mgArray, Integer::compareTo, Direction.ASCENDING));
            javaTimes[i] = measure(() -> Arrays.sort(javaArray));

            check(mgArray, javaArray);
        }

        System.out.println("##### " + count + " #####");
        System.out.println("Average mg time: " + average(mgTimes));
        System.out.println("Average java time: " + average(javaTimes));
        System.out.println();
    }

    private long average(long[] times) {
        long sum = 0;
        for (Long time : times) {
            sum += time;
        }
        return sum / times.length;
    }

    private void check(@Mandatory Array<Integer> mgArray, @Mandatory Object[] javaArray) {
        if (mgArray.count() != javaArray.length) {
            throw new IllegalStateException();
        }

        for (int i = 0; i < mgArray.count(); i++) {
            if (!Objects.equals(mgArray.get(i), javaArray[i])) {
                throw new IllegalStateException();
            }
        }
    }

    private long measure(@Mandatory Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }

    private @Mandatory Array<Integer> createArray(int count, int seed) {
        Random random = new Random(seed);
        Array<Integer> array = new Array<>(count);
        for (int i = 0; i < count; i++) {
            array.set(i, random.nextInt());
        }
        return array;
    }
}
