package com.craig.itemgame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.CharacterSummaryDTO;
import com.craig.itemgame.DTOs.InventoryItemDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.DTOs.StatDTO;
import com.craig.itemgame.model.Character;
import com.craig.itemgame.model.CharacterFile;
import com.craig.itemgame.model.EquipmentSlot;
import com.craig.itemgame.model.ItemManager;
import com.craig.itemgame.model.Items.Item;

import java.awt.Point;

public class CharacterMapper {

    // private final DummyCharacter dummy;

    public CharacterMapper() {
        // dummy = new DummyCharacter();
    }

    /**
     * 
     * @param character
     * @return
     */
    public CharacterDTO mapCharacterToDTO(Character character) {

        CharacterDTO characterDTO = new CharacterDTO();
        Map<String, String> equippedItems = this.mapEquippedItems(character.getItemManager());
        Map<String, ItemDTO> itemsById = this.mapItemsByID(character.getItemManager());
        List<InventoryItemDTO> inventoryItemDTOs = this.mapInventoryItems(character.getItemManager());

        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setLevel(character.getLevel());
        characterDTO.setExperience(character.getExperience());
        characterDTO.setHealth(character.getHealth());
        characterDTO.setDamage(character.getDamage());
        characterDTO.setDefence(character.getDefence());
        characterDTO.setColdResistance(character.getColdResistance());
        characterDTO.setFireResistence(character.getFireResistance());
        characterDTO.setLightningResistance(character.getLightningResistance());
        characterDTO.setPoisonResistance(character.getPoisonResistance());
        characterDTO.setMagicResistance(character.getMagicResistance());

        characterDTO.setEquippedItems(equippedItems);
        characterDTO.setItemsById(itemsById);
        characterDTO.setInventoryItems(inventoryItemDTOs);

        // System.out.println(characterDTO.toString());

        return characterDTO;

    }

    public ItemDTO mapItemToDTO(Item item) {

        ItemDTO itemDTO = new ItemDTO();
        List<StatDTO> statList = new ArrayList<>();
            
        itemDTO.setId(item.getID());
        itemDTO.setName(item.getItemName());
        itemDTO.setType(item.getItemType());
        statList = mapStatsToDTO(item);

        itemDTO.setStats(statList);

        return itemDTO;

    }

    private Map<String, ItemDTO> mapItemsByID(ItemManager itemManager) {

        Map<String, ItemDTO> itemMap = new HashMap<>();
        List<Item> items = itemManager.getAllItems();

        for (Item item: items) {
            
            ItemDTO itemDTO = mapItemToDTO(item);
            // ItemDTO itemDTO = new ItemDTO();
            // List<StatDTO> statList = new ArrayList<>();
            
            // itemDTO.setId(item.getID());
            // itemDTO.setName(item.getItemName());
            // itemDTO.setType(item.getItemType());
            // statList = mapStatsToDTO(item);

            // itemDTO.setStats(statList);

            itemMap.put(item.getID(), itemDTO);

        }

        return itemMap;

    }

    private List<InventoryItemDTO> mapInventoryItems(ItemManager itemManager) {

        List<InventoryItemDTO> inventoryItemDTOs = new ArrayList<>();

        for (Map.Entry<String,Point> entry: itemManager.getInventory().getInventoryItems().entrySet()) {

            InventoryItemDTO inventoryItemDTO = new InventoryItemDTO();
            inventoryItemDTO.setId(entry.getKey());

            // TODO: these should be stored as whole numbers so the cast should be safe
            // but may want to double check
            inventoryItemDTO.setX((int)entry.getValue().getX());
            inventoryItemDTO.setY((int)entry.getValue().getY());

            inventoryItemDTO.setHeight(itemManager.getItemById(entry.getKey()).getHeightInGridUnits());
            inventoryItemDTO.setWidth(itemManager.getItemById(entry.getKey()).getWidthInGridUnits());

            inventoryItemDTOs.add(inventoryItemDTO);

        }

        return inventoryItemDTOs;

    }



    private Map<String, String> mapEquippedItems(ItemManager itemManager) {

        Map<String, String> equippedItemMap = new HashMap<>();
        
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            
            Item item = itemManager.getItem(slot);
        
            if (item != null) {

                // ItemDTO itemDTO = new ItemDTO();
                // List<StatDTO> statList = new ArrayList<>();

                // itemDTO.setId(item.getID());
                // itemDTO.setName(item.getItemName());
                // itemDTO.setType(item.getItemType());
                
                // statList = mapStatsToDTO(item);
                // itemDTO.setStats(statList);
                
                // some of these have different names in the front-end compared to the back-end
                if (slot.equals(EquipmentSlot.RIGHT_RING)) {
                    equippedItemMap.put("ring-right", item.getID());
                } else if (slot.equals(EquipmentSlot.LEFT_RING)) {
                    equippedItemMap.put("ring-left", item.getID());
                } else if (slot.equals(EquipmentSlot.DELETE_ITEM_SLOT)) {
                    equippedItemMap.put("delete-item", item.getID());
                } else if (slot.equals(EquipmentSlot.NEW_ITEM_SLOT)) {
                    equippedItemMap.put("new-item", item.getID());
                } else {
                    equippedItemMap.put(slot.toString().toLowerCase(), item.getID());
                }
                
                // System.out.println(slot.toString().toLowerCase());
            
            }

            // System.out.println("Adding " + slot.getDisplayName());

        }

        return equippedItemMap;

    }

    private List<StatDTO> mapStatsToDTO(Item item) {

        List<StatDTO> statList = new ArrayList<>();

        for (int i = 0; i < item.statListSize(); i++) {

            StatDTO statDTO = new StatDTO(item.getStat(i).getStatType(), item.getStat(i).getValue());
            statList.add(statDTO);
        }
        
        return statList;

    }

    public List<CharacterSummaryDTO> mapCharactersToDTO(List<CharacterFile> characterFiles) {

        List<CharacterSummaryDTO> characterSummaries = new ArrayList<>();

        for (CharacterFile file : characterFiles) {
            CharacterSummaryDTO charSummary = new CharacterSummaryDTO();
            charSummary.setId(file.getId());
            charSummary.setLevel(file.getLevel());
            charSummary.setName(file.getCharacterName());
            characterSummaries.add(charSummary);
        }

        return characterSummaries;

    }

}
