package com.ozagrimate.inventoryservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryServiceBusinessLogic {
    // Map<itemId, InventoryItem>
    private Map<Integer, InventoryItem> inventory = new ConcurrentHashMap<>();

    public InventoryServiceBusinessLogic() {
        // Populate some initial InventoryItems
        InventoryItem i1 = new InventoryItem("seed", "carrot", "packets", 1524f, 500f ,2500f);
        InventoryItem i2 = new InventoryItem("seed", "wheat", "packets", 5234f, 1000f, 7500f);
        InventoryItem i3 = new InventoryItem("seed", "radish", "packets", 864f, 150f, 1000f);
        InventoryItem i4 = new InventoryItem("fertilizer", "nitrogen", "kg", 1400f, 1000f, 5000f);
        InventoryItem i5 = new InventoryItem("fertilizer", "phosphate", "kg", 600.1f, 500f, 1000f);
        InventoryItem i6 = new InventoryItem("fertilizer", "potassium", "kg", 443.23f, 500f, 2300f);
        InventoryItem i7 = new InventoryItem("fertilizer", "calcium", "kg", 245f, 100f, 300f);
        InventoryItem i8 = new InventoryItem("pesticide", "insecticide", "L", 145.65f, 75f, 250f);
        InventoryItem i9 = new InventoryItem("pesticide", "herbicide", "L", 250.5f, 100f, 300f);
        this.inventory.put(i1.getItemId(), i1);
        this.inventory.put(i2.getItemId(), i2);
        this.inventory.put(i3.getItemId(), i3);
        this.inventory.put(i4.getItemId(), i4);
        this.inventory.put(i5.getItemId(), i5);
        this.inventory.put(i6.getItemId(), i6);
        this.inventory.put(i7.getItemId(), i7);
        this.inventory.put(i8.getItemId(), i8);
        this.inventory.put(i9.getItemId(), i9);
    }

    public Map<Integer, InventoryItem> getAllInventoryItems(){
        return this.inventory;
    }

    public InventoryItem getInventoryItem(int itemId) throws InventoryItemNotFoundException{
        InventoryItem item = this.inventory.get(itemId);
        if(item==null) {
            throw new InventoryItemNotFoundException("itemId=" + itemId + " not found.");
        }
        return item;
    }

    public InventoryItem addInventoryItem(InventoryItemAddRequest itemRequest) {
        // Check if category/type combination exists if inventory isn't empty
        if (!this.inventory.isEmpty()) {
            for (int itemId : this.inventory.keySet()) {
                InventoryItem item_check = this.inventory.get(itemId);
                String category = item_check.getCategory();
                String type = item_check.getType();
                if(category.equals(itemRequest.getCategory()) && type.equals(itemRequest.getType())) {
                    String message = item_check.getCategory() + "/" + item_check.getType() + " combination already exists for itemId=" + item_check.getItemId();
                    throw new DuplicateItemException(message);
                }
            }
        }
        InventoryItem item = new InventoryItem(itemRequest);
        this.inventory.put(item.getItemId(), item);
        return item;
    }

    public InventoryItem updateItemQuantity(int itemId, InventoryItemUpdateRequest itemRequest) throws InventoryItemNotFoundException {
        InventoryItem item = this.getInventoryItem(itemId);
        item.setQuantity(itemRequest.getQuantity());
        item.setMinQuantity(itemRequest.getMinQuantity());
        item.setMaxQuantity(itemRequest.getMaxQuantity());
        return item;
    }

    public void deleteInventoryItem(int itemId) throws InventoryItemNotFoundException {
        InventoryItem item = this.inventory.remove(itemId);
        if (item==null) {
            throw new InventoryItemNotFoundException("itemId=" + itemId);
        }
    }
}
