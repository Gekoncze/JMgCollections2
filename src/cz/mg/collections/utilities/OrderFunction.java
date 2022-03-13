package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Optional;

public @Utility interface OrderFunction {
    int order(@Optional Object a, @Optional Object b);
}
