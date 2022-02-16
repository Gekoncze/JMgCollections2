package cz.mg.collections;

import cz.mg.annotations.requirement.Mandatory;

public class AssertException extends RuntimeException {
    public AssertException(@Mandatory String message) {
        super(message);
    }
}
