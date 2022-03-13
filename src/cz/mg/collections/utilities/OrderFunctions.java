package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Utility class OrderFunctions {
    public static OrderFunction<Boolean> BOOLEAN_NULL_LAST = (a, b) -> nullSafeLast(a, b, Boolean::compareTo);
    public static OrderFunction<Character> CHARACTER_NULL_LAST = (a, b) -> nullSafeLast(a, b, Character::compareTo);
    public static OrderFunction<String> STRING_NULL_LAST = (a, b) -> nullSafeLast(a, b, String::compareTo);
    public static OrderFunction<Integer> INTEGER_NULL_LAST = (a, b) -> nullSafeLast(a, b, Integer::compareTo);
    public static OrderFunction<Long> LONG_NULL_LAST = (a, b) -> nullSafeLast(a, b, Long::compareTo);
    public static OrderFunction<Float> FLOAT_NULL_LAST = (a, b) -> nullSafeLast(a, b, Float::compareTo);
    public static OrderFunction<Double> DOUBLE_NULL_LAST = (a, b) -> nullSafeLast(a, b, Double::compareTo);

    public static OrderFunction<Boolean> BOOLEAN_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, Boolean::compareTo);
    public static OrderFunction<Character> CHARACTER_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, Character::compareTo);
    public static OrderFunction<String> STRING_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, String::compareTo);
    public static OrderFunction<Integer> INTEGER_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, Integer::compareTo);
    public static OrderFunction<Long> LONG_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, Long::compareTo);
    public static OrderFunction<Float> FLOAT_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, Float::compareTo);
    public static OrderFunction<Double> DOUBLE_NULL_FIRST = (a, b) -> nullSafeFirst(a, b, Double::compareTo);

    private static <T> int nullSafeFirst(@Optional T a, @Optional T b, @Mandatory MandatoryOrderFunction<T> orderFunction) {
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return -1;
        } else if (b == null) {
            return 1;
        } else {
            return orderFunction.order(a, b);
        }
    }

    private static <T> int nullSafeLast(@Optional T a, @Optional T b, @Mandatory MandatoryOrderFunction<T> orderFunction) {
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return 1;
        } else if (b == null) {
            return -1;
        } else {
            return orderFunction.order(a, b);
        }
    }

    public interface MandatoryOrderFunction<T> {
        int order(@Mandatory T a, @Mandatory T b);
    }
}
