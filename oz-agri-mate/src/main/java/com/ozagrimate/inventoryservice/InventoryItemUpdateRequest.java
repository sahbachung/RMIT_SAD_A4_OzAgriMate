package com.ozagrimate.inventoryservice;

public class InventoryItemUpdateRequest {

    private float quantity;
    private float minQuantity;
    private float maxQuantity;

    public InventoryItemUpdateRequest() {}


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(float minQuantity) {
        this.minQuantity = minQuantity;
    }

    public float getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(float maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
