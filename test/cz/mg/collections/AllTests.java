package cz.mg.collections;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.array.ArrayTest;
import cz.mg.collections.list.ListTest;
import cz.mg.collections.map.MapTest;
import cz.mg.collections.pair.PairTest;

public @Test class AllTests {
    public static void main(String[] args) {
        ArrayTest.main(args);
        ListTest.main(args);
        MapTest.main(args);
        PairTest.main(args);
    }
}
