package cz.mg.collections.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;
import cz.mg.collections.coponents.ToStringFunction;

public @Service class StringJoiner {
    private static volatile @Service StringJoiner instance;

    public static @Service StringJoiner getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new StringJoiner();
                }
            }
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

    public <T> @Mandatory String join(
        @Mandatory Collection<T> collection,
        @Mandatory String delimiter,
        @Mandatory ToStringFunction<T> f
    ) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (T t : collection) {
            String part = f.toString(t);
            builder.append(part);
            if (i < collection.count() - 1) {
                builder.append(delimiter);
            }
            i++;
        }

        return builder.toString();
    }
}
