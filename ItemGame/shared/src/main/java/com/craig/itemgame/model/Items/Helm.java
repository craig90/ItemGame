package com.craig.itemgame.model.Items;

import com.craig.itemgame.model.ItemType;

public class Helm extends Item {

    public Helm(String itemName) {
        super(itemName, ItemType.HELM);
    }

    @Override
    public String getImageKey() {
        return HELM_KEY;
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.HELM_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.HELM_GRID_UNIT_HEIGHT;
    }

}
