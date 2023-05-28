package cz.mg.collections.services.sort;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.collections.components.Direction;
import cz.mg.test.Performance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public @Test class ArraySortPerformanceTest {
    private static final ArraySort SORT = MergeArraySort.getInstance();
    private static final int ITERATIONS = 25;
    private static final int BASE = 100;
    private static final int MULTIPLIER = 10;
    private static final int LEVELS = 5;

    public static void main(String[] args) {
        System.out.println("Running " + ArraySortPerformanceTest.class.getSimpleName() + " ... ");

        ArraySortPerformanceTest test = new ArraySortPerformanceTest();
        test.testSortPerformance();
    }

    private void testSortPerformance() {
        int count = BASE;
        for (int level = 0; level < LEVELS; level++) {
            testSortPerformance(count);
            count *= MULTIPLIER;
        }
    }

    private void testSortPerformance(int count) {
        long mgTime = 0;
        long javaTime = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            Array<Integer> mgArray = generate(count, i);
            Object[] javaArray = generate(count, i).getData();

            compare(mgArray, javaArray);

            mgTime += Performance.measure(() -> SORT.sort(mgArray, Integer::compareTo, Direction.ASCENDING));
            javaTime += Performance.measure(() -> Arrays.sort(javaArray, Comparator.comparingInt(o -> (Integer) o)));

            compare(mgArray, javaArray);
        }

        System.out.println();
        System.out.println("##### " + count + " #####");
        System.out.println("Average mg time: " + mgTime / ITERATIONS);
        System.out.println("Average java time: " + javaTime / ITERATIONS);
    }

    private void compare(@Mandatory Array<Integer> mgArray, @Mandatory Object[] javaArray) {
        if (mgArray.count() != javaArray.length) {
            throw new IllegalStateException("Results are not the same!");
        }

        for (int i = 0; i < mgArray.count(); i++) {
            if (!Objects.equals(mgArray.get(i), javaArray[i])) {
                throw new IllegalStateException("Results are not the same!");
            }
        }
    }

    private @Mandatory Array<Integer> generate(int count, int seed) {
        Random random = new Random(seed);
        Array<Integer> array = new Array<>(count);
        for (int i = 0; i < count; i++) {
            array.set(i, random.nextInt());
        }
        return array;
    }
}
