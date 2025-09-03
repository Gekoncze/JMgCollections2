package cz.mg.collections.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Component interface MapFunction<S,T> {
    @Mandatory T map(@Mandatory S s);

    default @Optional T mapOptional(@Optional S s) {
        if (s == null) {
            return null;
        }

        return map(s);
    }
}
