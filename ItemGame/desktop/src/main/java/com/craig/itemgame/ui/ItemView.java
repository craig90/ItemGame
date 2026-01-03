package com.craig.itemgame.ui;

import com.craig.itemgame.model.EquipmentSlot;

public interface ItemView {

    public void addItemToSlot(EquipmentSlot slot, uiItem item);
    public void removeItemFromSlot(EquipmentSlot slot);
    public void addItemToInventory(uiItem item, int x, int y);
    public void removeItemFromInventory(uiItem item);
    public void deleteItem();

}
