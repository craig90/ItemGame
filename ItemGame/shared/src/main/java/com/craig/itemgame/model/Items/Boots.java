package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Boots extends Item {

    public Boots (String itemName) {
        super(itemName, ItemType.BOOTS);
    }

    @Override
    public String getImageKey() {
        return "boots";
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.BOOTS_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.BOOTS_GRID_UNIT_HEIGHT;
    }

}
