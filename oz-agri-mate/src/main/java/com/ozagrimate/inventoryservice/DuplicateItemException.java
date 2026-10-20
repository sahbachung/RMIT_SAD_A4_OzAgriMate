package com.ozagrimate.inventoryservice;

public class DuplicateItemException extends RuntimeException {
    public DuplicateItemException(String message) {
        super(message);
    }
}
