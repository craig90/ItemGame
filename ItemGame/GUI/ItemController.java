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

    public void moveItemToGrid() {

    }

    public boolean canAddItem(Item item, int x, int y) {
        return this.itemManager.canAddItem(item, x, y);
    }

    public boolean addItem(Item item, int x, int y) {

        // update backend
        this.itemManager.addItem(item, x, y);

        System.out.println("Added item to backend.. now updating gui");
        // update GUI
        this.inventoryPanel.repaint();

        return true;
    }


    public Inventory getInventory() {
        return itemManager.getInventory();
    }

    public InventorySlot[][] getInventoryGrid() {
        return this.itemManager.getInventoryGrid();
    }

}
