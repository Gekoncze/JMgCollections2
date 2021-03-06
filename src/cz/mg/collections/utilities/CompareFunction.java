package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Optional;

public @Utility interface CompareFunction {
    boolean equals(@Optional Object a, @Optional Object b);
}
