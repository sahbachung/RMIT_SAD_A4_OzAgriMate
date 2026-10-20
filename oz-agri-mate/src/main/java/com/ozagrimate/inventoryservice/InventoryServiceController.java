package com.ozagrimate.inventoryservice;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@RestController
@RequestMapping("/inventory-service")
public class InventoryServiceController {
    private InventoryServiceBusinessLogic inventoryServiceBusinessLogic;

    public InventoryServiceController() {
        this.inventoryServiceBusinessLogic = new InventoryServiceBusinessLogic();
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
        catch (InventoryItemNotFoundException e) {
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
        catch (InventoryItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/inventory/{itemId}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable int itemId) {
        try {
            this.inventoryServiceBusinessLogic.deleteInventoryItem(itemId);
            return ResponseEntity.noContent().build();
        }
        catch (InventoryItemNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
