package cz.mg.collections;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.array.ArrayTest;
import cz.mg.collections.list.ListTest;
import cz.mg.collections.map.MapTest;
import cz.mg.collections.pair.PairTest;
import cz.mg.collections.services.*;
import cz.mg.collections.services.sort.SimpleArraySortTest;
import cz.mg.collections.services.sort.SimpleListSortTest;

public @Test class AllTests {
    public static void main(String[] args) {
        CollectionTest.main(args);
        ArrayTest.main(args);
        ListTest.main(args);
        MapTest.main(args);
        PairTest.main(args);

        SimpleArraySortTest.main(args);
        SimpleListSortTest.main(args);
        CollectionComparatorTest.main(args);
        StringJoinerTest.main(args);
    }
}
