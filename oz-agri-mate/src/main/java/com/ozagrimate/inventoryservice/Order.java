package com.ozagrimate.inventoryservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger idCounter = new AtomicInteger(0); // Thread safe ID Counter
    private final int orderId;
    private boolean ordered;
    private String createDttm;
    private String orderDttm;
    private Map<Integer, OrderItem> orderItems = new ConcurrentHashMap<>();


    public Order(String createDttm) {
        this.orderId = idCounter.incrementAndGet();
        this.createDttm = createDttm;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public Map<Integer, OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void addOrderItem(int itemId, float quantity, String unitOfMeasure) throws DuplicateItemException {
        if(this.checkItemExists(itemId)) {
            throw new DuplicateItemException("itemId=" + itemId + " already exists in orderId=" + this.orderId);
        }
        OrderItem item = new OrderItem(itemId, this.orderId, quantity, unitOfMeasure);
        this.orderItems.put(itemId, item);
    }

    public void removeOrderItem(int itemId) {
        this.orderItems.remove(itemId);
    }

    public OrderItem getOrderItem(int itemId) throws ItemNotFoundException {
        if(!this.checkItemExists(itemId)) {
            throw new ItemNotFoundException("itemId=" + itemId + " not found in orderId=" + this.orderId);
        }
        return this.orderItems.get(itemId);
    }

    public void setOrderItemStock(int itemId, float quantity, String unitOfMeasure) throws ItemNotFoundException {
        if(!this.checkItemExists(itemId)) {
            throw new ItemNotFoundException("itemId=" + itemId + " not found in orderId=" + this.orderId);
        }
        this.orderItems.get(itemId).setQuantity(quantity, unitOfMeasure);
    }

    public boolean isOrdered() {
        return this.ordered;
    }

    public void setOrdered(String orderDttm) {
        this.ordered = true;
        this.orderDttm = orderDttm;
    }

    public String getOrderDttm() {
        return this.orderDttm;
    }

    public String getCreateDttm() {return this.createDttm; }


    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        for (int itemId : this.orderItems.keySet()) {
            items.append(this.orderItems.get(itemId));
            items.append(", ");
        }
        if (!items.isEmpty()) {
            items.delete(items.length()-2, items.length()-1);
        }
        return "Order:{" +
                "orderId:" + this.orderId +
                ". createDttm:" + this.createDttm +
                ", ordered:" + this.ordered +
                ", orderDttm:" + this.createDttm +
                ", items:[" + items.toString() + "]" +
                "}";
    }

    private boolean checkItemExists(int itemId) {
        return this.orderItems.get(itemId)!=null;
    }
}
