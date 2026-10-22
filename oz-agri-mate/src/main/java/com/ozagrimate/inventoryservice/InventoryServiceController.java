package com.ozagrimate.inventoryservice;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventory-service")
public class InventoryServiceController {
    private InventoryServiceBusinessLogic inventoryServiceBusinessLogic;
    private OrderBusinessLogic orderBusinessLogic;

    public InventoryServiceController() {
        this.inventoryServiceBusinessLogic = new InventoryServiceBusinessLogic();
        this.orderBusinessLogic = new OrderBusinessLogic();
    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<Integer, InventoryItem>> getAllInventoryItems() {
        return ResponseEntity.ok(this.inventoryServiceBusinessLogic.getAllInventoryItems());
    }

    @GetMapping("/inventory/{itemId}")
    public ResponseEntity<InventoryItem> getInventoryItem(@PathVariable int itemId) {
        try {
            return ResponseEntity.ok(this.inventoryServiceBusinessLogic.getInventoryItem(itemId));
        }
        catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryItem> createNewInventoryItem(@RequestBody InventoryItemAddRequest itemRequest) {
        try {
            return ResponseEntity.ok(this.inventoryServiceBusinessLogic.addInventoryItem(itemRequest));
        }
        catch (DuplicateItemException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/inventory/{itemId}")
    public ResponseEntity<InventoryItem> changeStock(@PathVariable int itemId, @RequestBody InventoryItemUpdateRequest itemRequest) {
        try {
            return ResponseEntity.ok(this.inventoryServiceBusinessLogic.updateItemQuantity(itemId, itemRequest));
        }
        catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/inventory/{itemId}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable int itemId) {
        try {
            this.inventoryServiceBusinessLogic.deleteInventoryItem(itemId);
            return ResponseEntity.noContent().build();
        }
        catch (ItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order")
    public ResponseEntity<Map<Integer, Order>> getAllOrders(@RequestParam(required = false) Boolean ordered) {
        return ResponseEntity.ok(this.orderBusinessLogic.getAllOrders(ordered));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId) {
        try {
            return ResponseEntity.ok(this.orderBusinessLogic.getOrder(orderId));
        }
        catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreationRequest orderRequest) {
        try {
            return ResponseEntity.ok(this.orderBusinessLogic.createOrder(orderRequest));
        }
        catch (DuplicateItemException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable int orderId, @RequestBody OrderUpdateRequest orderRequest) {
        try {
            return ResponseEntity.ok(this.orderBusinessLogic.updateOrderItems(orderId, orderRequest));
        }
        catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (DuplicateItemException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        this.orderBusinessLogic.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
