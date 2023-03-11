package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

public @Utility interface ToStringFunction<T> {
    @Mandatory String toString(@Mandatory T t);
}
