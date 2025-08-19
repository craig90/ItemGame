import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

public class ItemController {

    private Game game;

    private ItemManager itemManager;
    private ItemPanel itemPanel;
    private InventoryPanel inventoryPanel;

    public ItemController(Game game) {
        this.game = game;
    }

    /**
     * sets the item panel
     * @param itemPanel
     */
    public void setItemPanel(ItemPanel itemPanel) {
        this.itemPanel = itemPanel;
    }

    public void setInventoryPanel(InventoryPanel inventoryPanel) {
        this.inventoryPanel = inventoryPanel;
    }

    /**
     * Resets the itemManager, this needs to be called each time a new character is created
     * @param itemManager
     */
    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    /**
     * Moves an Item to an Equipment slot from an Equipment slot
     * @param targetSlot
     * @param sourceSlot
     * @param item
     */
    public void moveItemToSlot(EquipmentSlot targetSlot, EquipmentSlot sourceSlot, Item item) {
        
        if (this.itemManager == null || this.itemPanel == null) {
            throw new ExceptionInInitializerError("Controller not initialized with ItemManager or ItemPanel");
        }

        // backend calls
        this.itemManager.equipItem(targetSlot, item);
        this.itemManager.removeItem(sourceSlot);
        
        // GUI calls
        this.itemPanel.addItemToSlot(targetSlot, item);
        this.itemPanel.removeItemFromSlot(sourceSlot);
        
    }

    /**
     * Moves an Item to an EquipmentSlot from the InventoryPanel
     * @param targetSlot
     * @param inventoryPanel
     * @param item
     */
    public void moveItemToSlotFromInventory(EquipmentSlot targetSlot, Item item) {
        
        if (this.itemManager == null || this.itemPanel == null) {
            throw new ExceptionInInitializerError("Controller not initialized with ItemManager or ItemPanel");
        }

        // backend calls
        this.itemManager.equipItem(targetSlot, item);
        this.itemManager.removeItemFromInventory(item);
        
        // GUI calls
        this.itemPanel.addItemToSlot(targetSlot, item);
        this.inventoryPanel.removeItem(item);
        
    }


    public boolean canAddItem(Item item, int x, int y) {
        return this.itemManager.canAddItem(item, x, y);
    }

    /**
     * Moves an Item to the Inventory from an Equipment Slot
     * @param item
     * @param x
     * @param y
     * @param sourceSlot
     * @return
     */
    public boolean moveItemToInventory(Item item, int x, int y, EquipmentSlot sourceSlot) {

        // update backend
        this.itemManager.addItemToGrid(item, x, y);
        this.itemManager.removeItem(sourceSlot);

        // update GUI
        this.itemPanel.removeItemFromSlot(sourceSlot);
        this.inventoryPanel.addItem(item, x, y);
        this.inventoryPanel.repaint();

        return true;
    }

    /**
     * Moves an item to an Inventory spot from an existing Inventory spot
     * @param item
     * @param x
     * @param y
     * @param inventoryPanel
     * @return
     */
    public boolean moveItemToInventoryFromInventory(Item item, int x, int y) {

        // going to need old x y

        // update backend
        this.itemManager.removeItemFromInventory(item); // must be called before readding
        this.itemManager.addItemToGrid(item, x, y);
        

        // update GUI
        this.inventoryPanel.addItem(item, x, y);
        this.inventoryPanel.repaint();

        return true;
    }

    /**
     * Removes the Item in the remove Item slot
     */
    public void deleteItem() {

        // update backend
        this.itemManager.removeDeleteItem();

        // update GUI
        this.itemPanel.deleteItem();
        
    }

    /**
     * Gets the Equipped Item from the cooresponding slot.  Returns null if slot is empty
     * @param slot
     * @return
     */
    public Item getItem(EquipmentSlot slot) {
        return this.itemManager.getItem(slot);
    }


    /**
     * Gets the Item from the Inventory at corresponding to the column and row numbers
     * @param column
     * @param row
     * @return
     */
    public Item getItem(int column, int row) {
        InventorySlot[][] slots = this.itemManager.getInventoryGrid();
        return slots[row][column].getItem();
    }

    public Inventory getInventory() {
        return itemManager.getInventory();
    }

    public InventorySlot[][] getInventoryGrid() {
        return this.itemManager.getInventoryGrid();
    }

     public Map<Item, Point> getInventoryItems() {
        return this.itemManager.getInventoryItems();
    }


    public boolean canEquipItem(EquipmentSlot slot, Item item) {
        return this.itemManager.canEquipItem(slot, item);
    }


    /**
     * return all Items, including both equipped Items and inventory Items
     * @return
     */
    public ArrayList<Item> getAllItems() {
        
        // start with the equipped items
        ArrayList<Item> items = (ArrayList<Item>)this.itemManager.getAllEquippedItems();

        // append the inventory items
        for (Item item : this.itemManager.getInventoryItems().keySet()) {
            items.add(item);
        }

        return items;

    }

    public Item generateNewItem() throws Exception {
        
        Item newItem = this.game.getItemGenerator().createNewItem();
        this.itemManager.setGeneratedItem(newItem);
        return newItem;
        
    }

}
