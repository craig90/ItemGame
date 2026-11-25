package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Belt extends Item {

    public Belt (String itemName) {
        super(itemName, ItemType.BELT);
    }

    @Override
    public String getImageKey() {
        return BELT_KEY;
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.BELT_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.BELT_GRID_UNIT_HEIGHT;
    }
}
