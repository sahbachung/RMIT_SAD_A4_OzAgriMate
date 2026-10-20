package com.ozagrimate.inventoryservice;

import java.util.concurrent.atomic.AtomicInteger;

public class InventoryItem {
    private static final AtomicInteger idCounter = new AtomicInteger(0); // Thread-safe ID counter

    private final int itemId;
    private String category;
    private String type;
    private String unitOfMeasure;
    private float quantity;
    private float minQuantity;
    private float maxQuantity;

    public InventoryItem() {
        this.itemId = idCounter.incrementAndGet();
    }

    public InventoryItem(String category, String type, String unitOfMeasure,float quantity, float minQuantity, float maxQuantity) {
        this();
        this.category = category;
        this.type = type;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    public InventoryItem(InventoryItemAddRequest itemRequest) {
        this();
        this.category = itemRequest.getCategory();
        this.type = itemRequest.getType();
        this.unitOfMeasure = itemRequest.getUnitOfMeasure();
        this.quantity = itemRequest.getQuantity();
        this.minQuantity = itemRequest.getMinQuantity();
        this.maxQuantity = itemRequest.getMaxQuantity();
    }

    public int getItemId() {
        return itemId;
    }

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


    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public String toString() {
        return "InventoryItem: {" +
                "category: " + this.category +
                ", type: " + this.type +
                ", quantity: " + Float.toString(this.quantity) + " " + this.unitOfMeasure +
                ", minQuantity: " + Float.toString(this.minQuantity) + " " + this.unitOfMeasure +
                ", maxQuantity: " + Float.toString(this.maxQuantity) + " " + this.unitOfMeasure +
                "}";
    }
}
