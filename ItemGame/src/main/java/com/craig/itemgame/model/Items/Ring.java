package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.EquipmentSlot;
import com.craig.itemgame.model.ItemType;

public class Ring extends Item {

    EquipmentSlot slot;

    public Ring(String itemName) {
        super(itemName);
    }

    public void setRingSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

    public EquipmentSlot getRingSlot() {
        return this.slot;
    }

    public String getImageFile() {
        return IMAGE_DIR + RING_FILE;
    }

    public ItemType getItemType() {
        return ItemType.RING;
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.RING_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.RING_GRID_UNIT_HEIGHT;
    }
}
