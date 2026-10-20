package com.ozagrimate.inventoryservice;

public class InventoryItemNotFoundException extends RuntimeException {
    public InventoryItemNotFoundException(String message) {
        super(message);
    }
}
