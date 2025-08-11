public class ItemController {

    private ItemManager itemManager;
    private ItemPanel itemPanel;
    // private final GridPanel

    public ItemController() { }

    /**
     * sets the item panel
     * @param itemPanel
     */
    public void setItemPanel(ItemPanel itemPanel) {
        this.itemPanel = itemPanel;
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


}
