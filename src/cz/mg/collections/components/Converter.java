package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;

public @Component interface Converter<S,T> {
    @Mandatory T toString(@Mandatory S s);
}
