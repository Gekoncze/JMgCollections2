package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;

public @Component interface ToStringFunction<T> {
    @Mandatory String toString(@Mandatory T t);
}
