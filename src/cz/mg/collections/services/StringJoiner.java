package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.Collection;

public @Service class StringJoiner {
    private static @Optional StringJoiner instance;

    public static @Mandatory StringJoiner getInstance() {
        if (instance == null) {
            instance = new StringJoiner();
        }
        return instance;
    }

    private StringJoiner() {
    }

    public @Mandatory String join(@Mandatory Collection<String> collection, @Mandatory String delimiter) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (String part : collection) {
            builder.append(part);
            if (i < collection.count() - 1) {
                builder.append(delimiter);
            }
            i++;
        }

        return builder.toString();
    }
}
