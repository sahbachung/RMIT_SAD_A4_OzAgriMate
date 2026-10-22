package com.ozagrimate.inventoryservice;

import java.util.ArrayList;

public class OrderUpdateRequest {

    private ArrayList<OrderItemRequest> orderItemsToAdd;
    private ArrayList<Integer> orderItemIdsToRemove;

    public ArrayList<OrderItemRequest> getOrderItemsToAdd() {
        return orderItemsToAdd;
    }

    public void setOrderItemsToAdd(ArrayList<OrderItemRequest> orderItemsToAdd) {
        this.orderItemsToAdd = orderItemsToAdd;
    }

    public ArrayList<Integer> getOrderItemIdsToRemove() {
        return orderItemIdsToRemove;
    }

    public void setOrderItemIdsToRemove(ArrayList<Integer> orderItemIdsToRemove) {
        this.orderItemIdsToRemove = orderItemIdsToRemove;
    }
}
