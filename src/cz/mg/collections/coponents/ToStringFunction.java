package cz.mg.collections.coponents;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;

public @Component interface ToStringFunction<T> {
    @Mandatory String toString(@Mandatory T t);
}
