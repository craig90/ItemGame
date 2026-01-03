package com.craig.itemgame.controller;

import com.craig.itemgame.model.EquipmentSlot;
import com.craig.itemgame.model.Inventory;
import com.craig.itemgame.model.InventorySlot;
import com.craig.itemgame.model.ItemManager;
import com.craig.itemgame.model.ItemService;
import com.craig.itemgame.model.Items.Item;
import com.craig.itemgame.model.Character;
import com.craig.itemgame.model.CharacterStat;
import com.craig.itemgame.model.CharacterStatNames;
import com.craig.itemgame.ui.CharacterView;
import com.craig.itemgame.ui.uiItem;
import com.craig.itemgame.ui.ItemView;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterController {

    Character character;
    ItemManager itemManager;
    
    CharacterView characterView;
    ItemView itemView;

    ItemService itemService;

    public CharacterController(ItemService itemService) {
        this.itemService = itemService;

    }

    public void setCharacter(Character character) {
        System.out.println("Setting character and item manager");
        this.character = character;
        this.itemManager = character.getItemManager();
        
        updateCharacterView();
        this.characterView.updateCharacterItems();
        
    }

    /**
     * Calls all necessary updates for the CharacterView
     */
    public void updateCharacterView() {

        if (this.characterView != null) {
         
            // System.out.println("Updating through the character view");

            this.characterView.updateCharacterName(this.character.getName());
            this.characterView.updateCharacterStat(CharacterStatNames.LEVEL, this.character.getLevel());
            this.characterView.updateCharacterStat(CharacterStatNames.EXPERIENCE, this.character.getExperience());
            
            Map<CharacterStatNames, CharacterStat> statMap = this.character.getCharacterStats();

            for (Map.Entry<CharacterStatNames, CharacterStat> entry : statMap.entrySet()) {
                this.characterView.updateCharacterStat(entry.getKey(), entry.getValue().getValue());
            }

        } else {
            System.out.println("Character view is null");
        }
    }

    public void setView(CharacterView characterView, ItemView itemView) {
        this.characterView = characterView;
        this.itemView = itemView;
    }


    /**
     * Moves an Item to an Equipment slot from an Equipment slot
     * @param targetSlot
     * @param sourceSlot
     * @param item
     */
    public void moveItemToSlot(EquipmentSlot targetSlot, EquipmentSlot sourceSlot, uiItem uiItem) {
        
        if (this.itemManager == null /*|| this.itemPanel == null*/) {
            throw new ExceptionInInitializerError("Controller not initialized with ItemManager or ItemPanel");
        }

        // backend calls
        this.itemManager.equipItem(targetSlot, uiItem.getItem());
        this.itemManager.removeItem(sourceSlot);
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }

        // View calls - we don't access the GUI directly
        this.itemView.addItemToSlot(targetSlot, uiItem);
        this.itemView.removeItemFromSlot(sourceSlot);
        


        // this.itemPanel.addItemToSlot(targetSlot, item);
        // this.itemPanel.removeItemFromSlot(sourceSlot);
        
    }

    /**
     * Moves an Item to an EquipmentSlot from the InventoryPanel
     * @param targetSlot
     * @param inventoryPanel
     * @param item
     */
    public void moveItemToSlotFromInventory(EquipmentSlot targetSlot, uiItem uiItem) {
        
        // backend calls
        this.itemManager.equipItem(targetSlot, uiItem.getItem());
        this.itemManager.removeItemFromInventory(uiItem.getItem());
        this.character.updateStats();

        updateCharacterView();
        
        if (this.itemView == null) {
            return;
        }

        // View calls
        this.itemView.addItemToSlot(targetSlot, uiItem);
        this.itemView.removeItemFromInventory(uiItem);

        // GUI calls
        // this.itemPanel.addItemToSlot(targetSlot, item);
        // this.inventoryPanel.removeItem(item);
        
    }


    public boolean canAddItem(uiItem uiItem, int x, int y) {
        return this.itemManager.canAddItem(uiItem.getItem(), x, y);
    }

    /**
     * Moves an Item to the Inventory from an Equipment Slot
     * @param item
     * @param x
     * @param y
     * @param sourceSlot
     * @return
     */
    public void moveItemToInventory(uiItem uiItem, int x, int y, EquipmentSlot sourceSlot) {

        // update backend
        this.itemManager.addItemToGrid(uiItem.getItem(), x, y);
        this.itemManager.removeItem(sourceSlot);
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }

        // View calls
        this.itemView.removeItemFromSlot(sourceSlot);
        this.itemView.addItemToInventory(uiItem, x, y);

        // update GUI
        // this.itemPanel.removeItemFromSlot(sourceSlot);
        // this.inventoryPanel.addItem(item, x, y);
        // this.inventoryPanel.repaint();

        // return true;
    }

    /**
     * Moves an item to an Inventory spot from an existing Inventory spot
     * @param item
     * @param x
     * @param y
     * @param inventoryPanel
     * @return
     */
    public void moveItemToInventoryFromInventory(uiItem uiItem, int x, int y) {

        // update backend
        this.itemManager.removeItemFromInventory(uiItem.getItem()); // must be called before readding
        this.itemManager.addItemToGrid(uiItem.getItem(), x, y);
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }
        
        // update view
        this.itemView.addItemToInventory(uiItem, x, y);

        // update GUI
        // this.inventoryPanel.addItem(item, x, y);
        // this.inventoryPanel.repaint();

    }

    /**
     * Removes the Item in the remove Item slot
     */
    public void deleteItem() {

        // update backend
        this.itemManager.removeDeleteItem();
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }

        // view call
        this.itemView.deleteItem();

    }

    /**
     * Gets the Equipped Item from the cooresponding slot.  Returns null if slot is empty
     * @param slot
     * @return
     */
    public uiItem getItem(EquipmentSlot slot) {
        
        Item item = this.itemManager.getItem(slot);

        if (item == null) {
            return null;
        }

        uiItem uiItem = new uiItem(this.itemManager.getItem(slot));
        
        return uiItem;
    }


    /**
     * Gets the Item from the Inventory corresponding to the column and row numbers
     * @param column
     * @param row
     * @return
     */
    public uiItem getItem(int column, int row) throws Exception {
        uiItem uiItem = new uiItem(this.itemManager.getItem(column, row));
        return uiItem;
    }

    public Inventory getInventory() {
        return this.itemManager.getInventory();
    }

    public InventorySlot[][] getInventoryGrid() {
        return this.itemManager.getInventoryGrid();
    }

     public Map<uiItem, Point> getInventoryItems() {
        
        Map<uiItem, Point> inventoryItems = new HashMap<uiItem,Point>();

        for (Map.Entry<Item, Point> entry : this.itemManager.getInventoryItems().entrySet()) {
            uiItem uiItem = new uiItem(entry.getKey());
            inventoryItems.put(uiItem, entry.getValue());
        }
        
        return inventoryItems;
    }


    public boolean canEquipItem(EquipmentSlot slot, uiItem uiItem) {
        return this.itemManager.canEquipItem(slot, uiItem.getItem());
    }


    /**
     * return all Items, including both equipped Items and inventory Items
     * @return
     */
    public List<uiItem> getAllItems() {
        
        List<uiItem> uiItems = new ArrayList<>();
        
        for (Item item : this.itemManager.getAllItems()) {
            uiItem uiItem = new uiItem(item);
            uiItems.add(uiItem);
        }
        
        return uiItems;
    
    }

    /**
     * Generates and returns a new item
     * @return
     * @throws Exception
     */
    public uiItem generateNewItem() throws Exception {
        
        // Item newItem = this.game.getItemGenerator().createNewItem();
        //this.itemManager.setGeneratedItem(newItem);
        // return newItem;
        Item newItem = this.itemService.generateItem();
        uiItem newUiItem = new uiItem(newItem);

        // backend call 
        this.itemManager.setGeneratedItem(newItem);
        
        // view call
        this.itemView.addItemToSlot(EquipmentSlot.NEW_ITEM_SLOT, newUiItem);

        return newUiItem;
        
    }

}

    

