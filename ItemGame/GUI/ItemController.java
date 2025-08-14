public class ItemController {

    private ItemManager itemManager;
    private ItemPanel itemPanel;
    private InventoryPanel inventoryPanel;

    public ItemController() { }

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

        System.out.println("Added item to backend.. now updating gui");
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
        

        System.out.println("Added item to backend.. now updating gui");
        
        // update GUI
        this.inventoryPanel.addItem(item, x, y);
        this.inventoryPanel.repaint();

        return true;
    }

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

}
