package com.ozagrimate.inventoryservice;

public class InventoryItemAddRequest {

    private String category;
    private String type;
    private String unitOfMeasure;
    private float quantity;
    private float minQuantity;
    private float maxQuantity;

    public InventoryItemAddRequest() {}


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

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
