package cz.mg.collections;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

public class Assert {
    public static void assertNotNull(@Optional Object object) {
        if(object == null) {
            throw new AssertException("Unexpected null value.");
        }
    }

    public static void assertEquals(@Optional Object expectation, @Optional Object reality) {
        if(expectation != reality) {
            if(expectation == null || reality == null) {
                throw new AssertException("Expected " + expectation + ", but got " + reality + ".");
            }

            if(!expectation.equals(reality)) {
                throw new AssertException("Expected " + expectation + ", but got " + reality + ".");
            }
        }
    }

    public static void assertExceptionThrown(@Mandatory Class<? extends Exception> type, @Mandatory Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if(e.getClass().equals(type)) {
                return;
            }
        }

        throw new AssertException("Expected an exception of type " + type.getSimpleName() + " to be thrown.");
    }
}
