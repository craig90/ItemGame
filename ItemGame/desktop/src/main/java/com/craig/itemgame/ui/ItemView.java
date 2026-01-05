package com.craig.itemgame.ui;

import com.craig.itemgame.model.EquipmentSlot;
import com.craig.itemgame.model.Items.Item;

public interface ItemView {

    public void addItemToSlot(EquipmentSlot slot, Item item);
    public void removeItemFromSlot(EquipmentSlot slot);
    public void addItemToInventory(Item item, int x, int y);
    public void removeItemFromInventory(Item item);
    public void deleteItem(Item item);

}
