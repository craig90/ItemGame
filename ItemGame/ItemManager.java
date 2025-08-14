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

    public Item getGeneratedItem() {
        return this.generatedItem;
    }

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
     * adds and item either to the spare item slot, or equips it to an item slot
     * @param slot
     * @param item
     */
    public void addItem(EquipmentSlot slot, Item item) {
        if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
            this.generatedItem = item;
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
            System.out.println("Wrong slot");
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
     * Removes an item from the corresponding equipment slot
     * @param slot
     */
    public void removeItem(EquipmentSlot slot) {
        
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
        List<Item> allItems = new ArrayList<>(this.uniqueItems.values());
        //allItems.addAll(rings.values());
        return allItems;
    }


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
