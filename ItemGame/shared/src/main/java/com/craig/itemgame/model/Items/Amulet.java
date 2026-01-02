package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Amulet extends Item {

    public Amulet (String itemName) {
        super(itemName, ItemType.AMULET);
    }

    @Override
    public String getImageKey() {
        return "amulet";
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.AMULET_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.AMULET_GRID_UNIT_HEIGHT;
    }

}
