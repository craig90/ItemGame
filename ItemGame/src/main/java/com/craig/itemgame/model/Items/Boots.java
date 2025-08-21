package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Boots extends Item {

    public Boots (String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.BOOTS;
    }

    public String getImageFile() {
        return Item.IMAGE_DIR + Item.BOOTS_FILE;
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
