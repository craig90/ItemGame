public enum EquipmentSlot {
    AMULET("Amulet"),
    BELT("Belt"),
    BOOTS("Boots"),
    CHEST("Chest"),
    GLOVES("Gloves"),
    HELM("Helm"),
    PANTS("Pants"),
    SHIELD("Shield"),
    WEAPON("Weapon"),
    LEFT_RING("Ring"),
    RIGHT_RING("Ring"),
    NEW_ITEM_SLOT("New Item"),
    INVENTORY_SLOT_ONE("Inventory"),
    INVENTORY_SLOT_TWO("Inventory"),
    INVENTORY_SLOT_THREE("Inventory"),
    INVENTORY_SLOT_FOUR("Inventory");

    private final String displayName;

    EquipmentSlot (String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
