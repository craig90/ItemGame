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
import com.craig.itemgame.ui.ItemView;

import java.awt.Point;
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
        // System.out.println("Setting character and item manager");
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
    public void moveItemToSlot(EquipmentSlot targetSlot, EquipmentSlot sourceSlot, Item item) {
        
        if (this.itemManager == null /*|| this.itemPanel == null*/) {
            throw new ExceptionInInitializerError("Controller not initialized with ItemManager or ItemPanel");
        }

        // backend calls
        this.itemManager.equipItem(targetSlot, item);
        this.itemManager.removeItem(sourceSlot);
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }

        // View calls - we don't access the GUI directly
        this.itemView.addItemToSlot(targetSlot, item);
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
    public void moveItemToSlotFromInventory(EquipmentSlot targetSlot, Item item) {
        
        // backend calls
        this.itemManager.equipItem(targetSlot, item);
        this.itemManager.removeItemFromInventory(item);
        this.character.updateStats();

        updateCharacterView();
        
        if (this.itemView == null) {
            return;
        }

        // View calls
        this.itemView.addItemToSlot(targetSlot, item);
        this.itemView.removeItemFromInventory(item);

        // GUI calls
        // this.itemPanel.addItemToSlot(targetSlot, item);
        // this.inventoryPanel.removeItem(item);
        
    }


    public boolean canAddItem(Item item, int x, int y) {
        return this.itemManager.canAddItem(item, x, y);
    }

    /**
     * Moves an Item to the Inventory from an Equipment Slot
     * @param item
     * @param x
     * @param y
     * @param sourceSlot
     * @return
     */
    public void moveItemToInventory(Item item, int x, int y, EquipmentSlot sourceSlot) {

        // update backend
        this.itemManager.addItemToGrid(item, x, y);
        this.itemManager.removeItem(sourceSlot);
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }

        // View calls
        this.itemView.removeItemFromSlot(sourceSlot);
        this.itemView.addItemToInventory(item, x, y);

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
    public void moveItemToInventoryFromInventory(Item item, int x, int y) {

        // update backend
        this.itemManager.removeItemFromInventory(item); // must be called before readding
        this.itemManager.addItemToGrid(item, x, y);
        this.character.updateStats();

        updateCharacterView();

        if (this.itemView == null) {
            return;
        }
        
        // update view
        this.itemView.addItemToInventory(item, x, y);

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
    public Item getItem(EquipmentSlot slot) {
        return this.itemManager.getItem(slot);
    }


    /**
     * Gets the Item from the Inventory corresponding to the column and row numbers
     * @param column
     * @param row
     * @return
     */
    public Item getItem(int column, int row) throws Exception {
        // InventorySlot[][] slots = this.itemManager.getInventoryGrid();
        return this.itemManager.getItem(column, row);
    }

    public Item getItemById(String id) {
        return this.itemManager.getItemById(id);
    }

    public Inventory getInventory() {
        return this.itemManager.getInventory();
    }

    public InventorySlot[][] getInventoryGrid() {
        return this.itemManager.getInventoryGrid();
    }

     public Map<Item, Point> getInventoryItems() {
        return this.itemManager.getInventoryItems();
    }


    public boolean canEquipItem(EquipmentSlot slot, Item item) {
        return this.itemManager.canEquipItem(slot, item);
    }


    /**
     * return all Items, including both equipped Items and inventory Items
     * @return
     */
    public List<Item> getAllItems() {
        return this.itemManager.getAllItems();
    }

    /**
     * Generates and returns a new item
     * @return
     * @throws Exception
     */
    public Item generateNewItem() throws Exception {
        
        // Item newItem = this.game.getItemGenerator().createNewItem();
        //this.itemManager.setGeneratedItem(newItem);
        // return newItem;
        Item newItem = this.itemService.generateItem();
        
        // backend call 
        this.itemManager.setGeneratedItem(newItem);
        
        // view call
        // this.itemView.addItemToSlot(EquipmentSlot.NEW_ITEM_SLOT, newItem);

        return newItem;
        
    }

}

    

