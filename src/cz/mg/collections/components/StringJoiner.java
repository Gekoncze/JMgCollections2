package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;
import cz.mg.collections.array.Array;

import java.util.Objects;

public @Component class StringJoiner<T> {
    private final @Mandatory Collection<T> collection;
    private @Mandatory String delimiter = "";
    private @Mandatory MapFunction<T, String> converter = Objects::toString;
    private @Mandatory Predicate<T> filter = item -> true;

    public StringJoiner(@Mandatory Collection<T> collection) {
        this.collection = collection;
    }

    @SafeVarargs
    public StringJoiner(@Mandatory T... items) {
        this.collection = new Array<>(items);
    }

    public @Mandatory StringJoiner<T> withDelimiter(@Mandatory String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public @Mandatory StringJoiner<T> withConverter(@Mandatory MapFunction<T, String> converter) {
        this.converter = converter;
        return this;
    }

    public @Mandatory StringJoiner<T> withFilter(@Mandatory Predicate<T> filter) {
        this.filter = filter;
        return this;
    }

    public @Mandatory String join() {
        StringBuilder builder = new StringBuilder();

        boolean addPadding = false;

        for (T t : collection) {
            if (filter.match(t))
            {
                if (addPadding) {
                    builder.append(delimiter);
                }
                builder.append(converter.map(t));
                addPadding = true;
            }
        }

        return builder.toString();
    }
}
