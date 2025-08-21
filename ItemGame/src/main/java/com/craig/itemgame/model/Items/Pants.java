package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Pants extends Item {

    public Pants (String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.PANTS;
    }

    public String getImageFile() {
        return IMAGE_DIR + PANTS_FILE;
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.PANTS_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.PANTS_GRID_UNIT_HEIGHT;
    }

}
