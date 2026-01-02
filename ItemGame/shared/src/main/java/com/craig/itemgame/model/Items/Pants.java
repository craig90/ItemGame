package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Pants extends Item {

    public Pants (String itemName) {
        super(itemName, ItemType.PANTS);
    }

    @Override
    public String getImageKey() {
        return "pants";
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
