package cz.mg.collections.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public @Utility interface HashFunction<T> {
    int hash(@Mandatory T o);

    default int hashOptional(@Optional T o) {
        if (o == null) {
            return 0;
        }

        return hash(o);
    }
}
