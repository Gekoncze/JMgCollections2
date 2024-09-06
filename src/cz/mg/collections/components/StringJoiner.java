package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.Collection;

import java.util.Objects;

public @Component class StringJoiner<T> {
    private final @Mandatory Collection<T> collection;
    private @Mandatory String delimiter = "";
    private @Mandatory Converter<T, String> converter = Objects::toString;

    public StringJoiner(@Mandatory Collection<T> collection) {
        this.collection = collection;
    }

    public @Mandatory StringJoiner<T> withDelimiter(@Mandatory String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public @Mandatory StringJoiner<T> withConverter(@Mandatory Converter<T, String> converter) {
        this.converter = converter;
        return this;
    }

    public @Mandatory String join() {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (T t : collection) {
            String part = converter.toString(t);
            builder.append(part);
            if (i < collection.count() - 1) {
                builder.append(delimiter);
            }
            i++;
        }

        return builder.toString();
    }
}
