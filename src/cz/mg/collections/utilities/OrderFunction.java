package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Optional;

public @Utility interface OrderFunction<T> {
    int order(@Optional T a, @Optional T b);
}
