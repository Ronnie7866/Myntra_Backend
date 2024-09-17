package com.backend.ecommerce.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String string) {
        super("Duplicate Entry Found in the Database for " + string);
    }
    public DuplicateEntryException(String string, Long id) {
        super("Duplicate Entry Found in the Database for " + string + " " + id);
    }
}
