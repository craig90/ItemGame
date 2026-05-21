package com.craig.itemgame.model;

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
    RING_LEFT("Ring"),
    RING_RIGHT("Ring"),
    NEW_ITEM("New Item"),
    DELETE_ITEM("Delete Item");

    private final String displayName;

    EquipmentSlot (String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
