package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;

import java.util.Objects;

public @Utility class HashFunctions {
    public static HashFunction HASH_CODE = Objects::hashCode;
}
