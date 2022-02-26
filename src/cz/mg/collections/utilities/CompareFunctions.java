package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;

import java.util.Objects;

public @Utility class CompareFunctions {
    public static CompareFunction EQUALS = Objects::equals;
    public static CompareFunction REFERENCE = (o1, o2) -> o1 == o2;
}
