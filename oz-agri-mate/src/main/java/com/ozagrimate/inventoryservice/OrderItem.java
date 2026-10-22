package com.ozagrimate.inventoryservice;

public class OrderItem {

    private final int itemId; //Should match an itemId from InventoryItem
    private final int orderId;
    private float quantity;
    private String unitOfMeasure;

    public OrderItem(int itemId, int orderId) {
        this.itemId = itemId;
        this.orderId = orderId;
    }

    public OrderItem(int itemId, int orderId, float quantity, String unitOfMeasure) {
        this(itemId, orderId);
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
    }

    public int getItemId() {
        return itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity, String unitOfMeasure) {
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public String toString() {
        return "OrderItem:{" +
                "orderId:" + this.orderId +
                ", itemId:" + this.itemId +
                ", quantity:" + this.itemId +
                " " + this.unitOfMeasure +
                "}";
    }
}
