package com.ozagrimate.inventoryservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBusinessLogic {

    private Map<Integer, Order> orders = new ConcurrentHashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderBusinessLogic() {}

    public Map<Integer, Order> getAllOrders(Boolean ordered) {
        if (ordered==null || this.orders.isEmpty()) {
            return this.orders;
        }
        Map<Integer, Order> filtered_orders = new HashMap<>();
        for(int orderId : this.orders.keySet()) {
            Order order = this.orders.get(orderId);
            if(ordered.equals(order.isOrdered())) {
                filtered_orders.put(orderId, order);
            }
        }
        return filtered_orders;
    }

    public Order getOrder(int orderId) {
        Order order = this.orders.get(orderId);
        if (order==null) {
            throw new OrderNotFoundException("orderId=" + orderId + " not found");
        }
        return order;
    }

    public Order createOrder(OrderCreationRequest orderRequest) throws DuplicateItemException{
        Order order = new Order(LocalDateTime.now().format(this.formatter));
        if (orderRequest != null && !orderRequest.getOrderItems().isEmpty()) {
            for (OrderItemRequest item : orderRequest.getOrderItems()) {
                order.addOrderItem(item.getItemId(), item.getQuantity(), item.getUnitOfMeasure());
            }
        }
        this.orders.put(order.getOrderId(), order);
        return order;
    }

    public void deleteOrder(int orderId){
        this.orders.remove(orderId);
    }

    public Order placeOrder(int orderId) throws OrderNotFoundException, EmptyOrderException{
        Order order = this.getOrder(orderId); // Throws OrderNotFoundException
        if (order.getOrderItems().isEmpty()) {
            throw new EmptyOrderException("orderId=" + orderId + " is empty");
        }
        order.setOrdered(LocalDateTime.now().format(this.formatter));
        return order;
    }

    public Order updateOrderItems(int orderId, OrderUpdateRequest orderRequest) throws OrderNotFoundException, DuplicateItemException{
        Order order = this.getOrder(orderId); // Throws OrderNotFoundException
        if(orderRequest!=null && orderRequest.getOrderItemIdsToRemove()!= null && !orderRequest.getOrderItemIdsToRemove().isEmpty()) {
            for (int itemId : orderRequest.getOrderItemIdsToRemove()) {
                order.removeOrderItem(itemId);
            }
        }
        if(orderRequest!=null && orderRequest.getOrderItemsToAdd()!=null && !orderRequest.getOrderItemsToAdd().isEmpty()) {
            for (OrderItemRequest itemRequest : orderRequest.getOrderItemsToAdd()) {
                try {
                    OrderItem orderItem = order.getOrderItem(itemRequest.getItemId());
                    order.setOrderItemStock(itemRequest.getItemId(), itemRequest.getQuantity(), itemRequest.getUnitOfMeasure());
                }
                catch (ItemNotFoundException e) {
                    order.addOrderItem(itemRequest.getItemId(), itemRequest.getQuantity(), itemRequest.getUnitOfMeasure());
                }
            }
        }
        return order;
    }
}
