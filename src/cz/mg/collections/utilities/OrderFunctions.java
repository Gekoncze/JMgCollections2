package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

import java.util.Comparator;

public @Utility class OrderFunctions {
    public static OrderFunction BOOLEAN = (a, b) -> {
        return nullSafeOrder((Boolean)a, (Boolean) b, Boolean::compareTo);
    };

    public static OrderFunction CHARACTER = (a, b) -> {
        return nullSafeOrder((Character)a, (Character) b, Character::compareTo);
    };

    public static OrderFunction STRING = (a, b) -> {
        return nullSafeOrder((String)a, (String) b, String::compareTo);
    };

    public static OrderFunction INTEGER = (a, b) -> {
        return nullSafeOrder((Integer)a, (Integer) b, Integer::compareTo);
    };

    public static OrderFunction LONG = (a, b) -> {
        return nullSafeOrder((Long)a, (Long) b, Long::compareTo);
    };

    public static OrderFunction FLOAT = (a, b) -> {
        return nullSafeOrder((Float)a, (Float) b, Float::compareTo);
    };

    public static OrderFunction DOUBLE = (a, b) -> {
        return nullSafeOrder((Double)a, (Double) b, Double::compareTo);
    };

    private static <T> int nullSafeOrder(@Optional T a, @Optional T b, @Mandatory Comparator<? super T> comparator) {
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return 1;
        } else if (b == null) {
            return -1;
        } else {
            return comparator.compare(a, b);
        }
    }
}
