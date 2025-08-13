import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.List;

public class ItemManager {

    private static final int INVENTORY_WIDTH = 10, INVENTORY_HEIGHT = 5;

    // private Map<String, Item> uniqueItems;
    // private Map<RingSlot, Ring> rings;
    private Map<EquipmentSlot, Item> uniqueItems;

    private Item generatedItem;
    private Inventory inventory;

    public ItemManager() {
        this.uniqueItems = new EnumMap<>(EquipmentSlot.class);
        this.generatedItem = null;
        this.inventory = new Inventory(INVENTORY_WIDTH, INVENTORY_HEIGHT);
    }


    public Item getWeapon() {
        return getUniqueItem(ItemType.WEAPON.getEquipmentSlot());
    }

    public Item getHelm() {
        return getUniqueItem(ItemType.HELM.getEquipmentSlot());
    }
    
    public Item getChest() {
        return getUniqueItem(ItemType.CHEST.getEquipmentSlot());
    }

    public Item getShield() {
        return getUniqueItem(ItemType.SHIELD.getEquipmentSlot());
    }

    public Item getAmulet() {
        return getUniqueItem(ItemType.AMULET.getEquipmentSlot());
    }

    public Item getLeftRing() {
        return getUniqueItem(EquipmentSlot.LEFT_RING);
    }

    public Item getRightRing() {
        return getUniqueItem(EquipmentSlot.RIGHT_RING);
    }

    public Item getGloves() {
        return getUniqueItem(ItemType.GLOVES.getEquipmentSlot());
    }
    
    public Item getBelt() {
        return getUniqueItem(ItemType.BELT.getEquipmentSlot());
    }

    public Item getPants() {
        return getUniqueItem(ItemType.PANTS.getEquipmentSlot());
    }
    
    public Item getBoots() {
        return getUniqueItem(ItemType.BOOTS.getEquipmentSlot());
    }

    public Item getGeneratedItem() {
        return this.generatedItem;
    }

    // public Item getInventoryItem(EquipmentSlot slot) throws Exception {
    //     return this.inventory[equipmentSlotToIndex(slot)];
    // }

    /**
     * Returns the item specified by item slot type
     * @param itemType
     * @return will return an item if it exists, otherwise returns null
     */
    public Item getUniqueItem(EquipmentSlot slotType) {
        return uniqueItems.get(slotType);
    }

    /**
     * Returns the Item based on the Slot Type, null if not equipped
     * @param slotType
     * @return
     */
    public Item getItem(EquipmentSlot slotType) {

        if (slotType == EquipmentSlot.NEW_ITEM_SLOT) {
            return this.generatedItem;
        } else {
            return getUniqueItem(slotType);
        }
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
        
        return inventory[row][column].getItem();
    }


    public void setGeneratedItem(Item generatedItem) {
        this.generatedItem = generatedItem;
    }

    public void removeGeneratedItem() {
        this.generatedItem = null;
    }

    

    /**
     * Adds an item to the inventory at the slot postition in Equipment slot
     * @param item
     * @param slot
     * @throws Exception
     */
    // public void addInventoryItem(Item item, EquipmentSlot slot) throws Exception {
        
    //     int inventoryIndex = 0;
    //     inventoryIndex = equipmentSlotToIndex(slot);

    //     this.inventory[inventoryIndex] = item;

    // }

    /**
     * Removes an item from the inventory
     * @param slot
     * @throws Exception
     */
    // private void removeInventoryItem(EquipmentSlot slot) throws Exception {
        
    //     int inventoryIndex = 0;
    //     inventoryIndex = equipmentSlotToIndex(slot);

    //     this.inventory[inventoryIndex] = null;

    // }

    // private int equipmentSlotToIndex(EquipmentSlot slot) throws Exception {
    //     if (slot == EquipmentSlot.INVENTORY_SLOT_ONE) {
    //         return 0;
    //     } else if (slot == EquipmentSlot.INVENTORY_SLOT_TWO) {
    //         return 1;
    //     } else if (slot == EquipmentSlot.INVENTORY_SLOT_THREE) {
    //         return 2;
    //     } else if (slot == EquipmentSlot.INVENTORY_SLOT_FOUR) {
    //         return 3;
    //     } else {
    //         throw new Exception("Error.. invalid equippment slot for inventory");
    //     }
    // }


    /**
     * return an item at the specified index
     * @param index
     * @return
     */
    /*public Item getItem(int index) {
        return this.itemList.get(index);
    }*/


    // public Ring getRing(RingSlot ringType) {
    //     return rings.get(ringType);
    // }


    /**
     * determines if we are working with an inventory slot or not
     * @param slot
     * @return
     */
    public static boolean isInventorySlot(EquipmentSlot slot) {
        
        if (slot == EquipmentSlot.INVENTORY_SLOT_ONE || slot == EquipmentSlot.INVENTORY_SLOT_TWO ||
            slot == EquipmentSlot.INVENTORY_SLOT_THREE || slot == EquipmentSlot.INVENTORY_SLOT_FOUR) {
                return true;
        } else {
            return false;
        }
    }

    /**
     * adds and item either to the spare item slot, or equips it to an item slot
     * @param slot
     * @param item
     */
    public void addItem(EquipmentSlot slot, Item item) {
    
        // see if we're moving an item to the inventory slot
        // if (isInventorySlot(slot) == true) {
        //     System.out.println("Adding to inventory " + slot.toString());
        //     try {
        //         addInventoryItem(item, slot);
        //     } catch (Exception e) {
        //         System.out.println("Error adding to inventroy"); // TODO: move this shit somewhere else 
        //     }

        // } else {
            // System.out.println("Target Panel: " + targetPanel.equipmentSlot.toString());
        this.equipItem(slot, item);
        // }
    }

    public void addItemToGrid(Item item, int x, int y) {
        InventorySlot inventorySlot = new InventorySlot(x, y);
        this.inventory.placeItem(item, inventorySlot);
    }

    public InventorySlot[][] getInventoryGrid() {
        return this.inventory.getGrid();
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
            
            if (slot == EquipmentSlot.LEFT_RING || slot == EquipmentSlot.RIGHT_RING) {
                return !uniqueItems.containsKey(slot);    
            }
            return false;
        }

        // make sure Item Type matches slot type
        if (item.getItemType().getEquipmentSlot() != slot) {
            return false;
        }

        return !uniqueItems.containsKey(item.getItemType().getEquipmentSlot());

    }

    public boolean canAddItem(Item item, int x, int y) {
        return (this.inventory.canPlaceItem(item, new InventorySlot(x, y)));
    }


    /**
     * this will add the item to the list and remove any existing item of the same item type
     * @param item
     */
    public void equipItem(EquipmentSlot slot, Item item) {
        
        if (slot != null) {
            uniqueItems.put(slot, item);
            return;
        }

        // logic is slightly different for Rings since there are two of them
        if (item instanceof Ring) {
            Ring ring = (Ring) item;
            uniqueItems.put(ring.getRingSlot(), item);
        }
    }

    /**
     * Rings are a special case, since there are two of them, they need to be treated differently
     * @param ring
     * @param slot
     */
    // public void equipRing(Ring ring, RingSlot slot) {
    //     ring.setRingSlot(slot);
    //     this.rings.put(slot, ring);
    // }


    /**
     * Removes an item from the corresponding equipment slot
     * @param slot
     */
    public void removeItem(EquipmentSlot slot) {
        
        // if (isInventorySlot(slot)) {
        //     System.out.println("Removing item from " + slot.toString());
        //     try {
        //         removeInventoryItem(slot);
        //     } catch (Exception e) {
        //         System.out.println("Error removing item from inventory"); // TODO: move this shit somewhere else 
        //     }
        // } else 
        if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
            this.generatedItem = null;
        } else {
            System.out.println("Removing item from " + slot.toString());
            this.uniqueItems.remove(slot);
        }
    }



    /**
     * Removes an item from the inventory
     * @param item
     * @param x
     * @param y
     */
    public void removeItem(Item item, int x, int y) {
        InventorySlot slot = new InventorySlot(x, y); // this is not great.. 
        this.inventory.removeItem(item, slot);
    }

    // public void removeRing(RingSlot slot) {
    //     this.rings.remove(slot);
    // }


    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>(this.uniqueItems.values());
        //allItems.addAll(rings.values());
        return allItems;
    }

    /**
     * This will return wheather a specific item type is already equipped
     * @return boolean
     */
    /*public boolean isEquipped(String itemType) {

        if (this.getItem(itemType) != null) {
            return true;
        }
        return false;
    }*/


    /**
     * Displays the character's items
     */
    public void displayItems() {
        
        List<Item> items = getAllItems();

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
