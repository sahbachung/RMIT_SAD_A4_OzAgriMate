package com.ozagrimate.inventoryservice;

import java.util.ArrayList;

public class OrderCreationRequest {
    private ArrayList<OrderItemRequest> orderItems;

    public ArrayList<OrderItemRequest> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(ArrayList<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
