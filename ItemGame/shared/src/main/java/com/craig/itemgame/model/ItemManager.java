package com.craig.itemgame.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.craig.itemgame.model.Items.Item;
import com.craig.itemgame.model.Items.Ring;

public class ItemManager {

    private static final int INVENTORY_WIDTH = 10, INVENTORY_HEIGHT = 5;

    private Map<String, Item> itemsByID;
    private Map<EquipmentSlot, String> equippedItems;
    
    private String deleteItemID;
    private String generatedItemID;
    private Inventory inventory;

    public ItemManager() {
        this.itemsByID = new HashMap<>();
        this.equippedItems = new EnumMap<>(EquipmentSlot.class);
        this.generatedItemID = null;
        this.deleteItemID = null;
        this.inventory = new Inventory(INVENTORY_WIDTH, INVENTORY_HEIGHT);
    }

    public Item getGeneratedItem() {
        return this.itemsByID.get(this.generatedItemID);
    }

    /**
     * Returns the Item based on the Slot Type, null if not equipped
     * @param slotType
     * @return
     */
    public Item getItem(EquipmentSlot slotType) {

        if (slotType == EquipmentSlot.NEW_ITEM) {
            return this.itemsByID.get(this.generatedItemID);
        } else if (slotType == EquipmentSlot.DELETE_ITEM) {
            return this.itemsByID.get(this.deleteItemID);
        } else {
            return this.itemsByID.get(this.equippedItems.get(slotType));
        }
    }

    public Item getItemById(String id) {
        return this.itemsByID.get(id);
    }

    public Map<Item, Point> getInventoryItems() {

        Map<Item,Point> inventoryItems = new HashMap<>();

        for (Map.Entry<String, Point> entry : this.inventory.getInventoryItems().entrySet()) {
            inventoryItems.put(this.itemsByID.get(entry.getKey()), entry.getValue());
        }

        return inventoryItems;
    }


    /**
     * returns an item from the grid at the specified column and row numbers
     * @param column
     * @param row
     * @return
     * @throws Exception
     */
    public Item getItem(int column, int row) throws Exception {
        // System.out.println("Get item at x: " + column + " y " + row);
        if (row >= Inventory.INVENTORY_ROWS || column >= Inventory.INVENTORY_COLUMNS) {
            throw new Exception("Can't get item because grid coordinates are out of bounds row " 
            + row + " column " + column);
        }
        
        InventorySlot[][] inventory = this.inventory.getGrid();
        
        return this.itemsByID.get(inventory[row][column].getItemId());
    }

    public void setGeneratedItem(Item generatedItem) {
        this.generatedItemID = generatedItem.getID();
        this.itemsByID.put(generatedItem.getID(), generatedItem);
    }

    public void removeGeneratedItem() {
        this.generatedItemID = null;
    }

    public Item getDeleteItem() {
        return this.itemsByID.get(this.deleteItemID);
    }
    
    // public void setDeleteItem(Item item) {
    //     this.deleteItem = item;
    // }
    
    public void removeDeleteItem() {
        this.itemsByID.remove(this.deleteItemID);
        this.deleteItemID = null;
    }
    

    /**
     * adds an item either to the spare item slot, or equips it to an item slot
     * @param slot
     * @param item
     */
    public void addItem(EquipmentSlot slot, Item item) {
        if (slot == EquipmentSlot.NEW_ITEM) {
            this.generatedItemID = item.getID();
        } else {
            this.equipItem(slot, item);
        }
    }

    /**
     * Adds an item to the grid at the specified x and y location
     * @param item
     * @param x
     * @param y
     */
    public void addItemToGrid(Item item, int x, int y) {
        // TODO: why make a new one each time?
        InventorySlot inventorySlot = new InventorySlot(y, x);
        this.inventory.placeItem(item, inventorySlot);
    }

    public InventorySlot[][] getInventoryGrid() {
        return this.inventory.getGrid();
    }

    public InventorySlot getGridSlot(int x, int y) {
        return this.inventory.getGridSlot(x, y);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * returns true if the item can be equiped in the slot
     * @param slot
     * @param item
     * @return
     */
    public boolean canEquipItem(EquipmentSlot slot, Item item) {
        
        // rings are a special case
        if (item.getItemType() == ItemType.RING) {
            
            if (slot == EquipmentSlot.RING_LEFT || slot == EquipmentSlot.RING_RIGHT) {
                return !equippedItems.containsKey(slot);    
            }
            return false;
        }

        // delete Item slot is a special case
        if (slot == EquipmentSlot.DELETE_ITEM) {
            if (this.deleteItemID == null) {
                return true;
            }
            return false;
        }

        // make sure Item Type matches slot type
        if (item.getItemType().getEquipmentSlot() != slot) {
            // System.out.println("Wrong slot type");
            return false;
        }

        return !equippedItems.containsKey(item.getItemType().getEquipmentSlot());

    }

    // TODO: why am I creating a new inventory slot here?
    public boolean canAddItem(Item item, int x, int y) {
        return (this.inventory.canPlaceItem(item, new InventorySlot(y, x)));
    }


    /**
     * this will add the item to the list and remove any existing item of the same item type
     * @param item
     */
    public void equipItem(EquipmentSlot slot, Item item) {
        
        if (slot == EquipmentSlot.DELETE_ITEM) {
            this.deleteItemID = item.getID();
            return;
        }

        if (slot != null) {
            this.equippedItems.put(slot, item.getID());
        }

        // logic is slightly different for Rings since there are two of them
        if (item instanceof Ring) {
            Ring ring = (Ring) item;
            ring.setRingSlot(slot);
        }
    }


    /**
     * Removes an item from the corresponding equipment slot
     * @param slot
     */
    public void removeItem(EquipmentSlot slot) {
        
        if (slot == EquipmentSlot.NEW_ITEM) {
            this.generatedItemID = null;
        } else if (slot == EquipmentSlot.DELETE_ITEM) {
            this.deleteItemID = null;
        } else {
            System.out.println("Removing item from " + slot.toString());
            this.equippedItems.remove(slot);
        }
    }



    /**
     * Removes an item from the inventory
     * @param item
     * @param x
     * @param y
     */
    public void removeItemFromInventory(Item item) {
        this.inventory.removeItem(item);
    }

    // public void removeRing(RingSlot slot) {
    //     this.rings.remove(slot);
    // }


    /**
     * Returns an array list of all the equipped items
     * @return
     */
    public List<Item> getAllEquippedItems() {

        List<Item> equippedItems = new ArrayList<>();

        for (Map.Entry<EquipmentSlot, String> entry : this.equippedItems.entrySet()) {
            equippedItems.add(this.itemsByID.get(entry.getValue()));
        }

        return equippedItems;
    }

    /**
     * Returns an array list off all the Items
     * @return
     */
    public List<Item> getAllItems() {

        List<Item> allItems = getAllEquippedItems();

        for (Map.Entry<Item, Point> entry : getInventoryItems().entrySet()) {
            allItems.add(entry.getKey());
        }

        if (this.getGeneratedItem() != null) {
            allItems.add(this.getGeneratedItem());
        }

        if (this.getDeleteItem() != null) {
            allItems.add(this.getDeleteItem());
        }

        return allItems;

    }

    public EquipmentSlot findEquipmentSlot(String itemId) {

        for (Map.Entry<EquipmentSlot, String> entry: equippedItems.entrySet()) {
            if (itemId.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;

    }


    /**
     * Method for loading Image Labels after loading on Items since there are not stored in JSON
     */
    // public void initAfterLoad() {
    //     List<Item> allItems = getAllItems();

    //     for (Item item: allItems) {
    //         item.setImageLabel();
    //     }
    // }


    /**
     * Displays the character's items
     */
    public void displayItems() {
        
        List<Item> items = getAllEquippedItems();

        if (items.size() == 0) {
            System.out.println("No items equipped");
            return;
        }

        System.out.println("Items:");

        for (Item item : items)  {
            item.displayItem();
        }
    }

}
