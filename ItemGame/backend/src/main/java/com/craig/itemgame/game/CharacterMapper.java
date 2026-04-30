package com.craig.itemgame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.CharacterSummaryDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.DTOs.StatDTO;
import com.craig.itemgame.model.Character;
import com.craig.itemgame.model.CharacterFile;
import com.craig.itemgame.model.EquipmentSlot;
import com.craig.itemgame.model.Items.Item;

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
        Map<String, ItemDTO> itemMap = this.mapItemsToDTO(character);

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

        characterDTO.setItems(itemMap);

        // System.out.println(characterDTO.toString());

        return characterDTO;

    }

    private Map<String, ItemDTO> mapItemsToDTO(Character character) {

        Map<String, ItemDTO> itemMap = new HashMap<>();
        
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            
            Item item = character.getItemManager().getItem(slot);
        
            if (item != null) {

                ItemDTO itemDTO = new ItemDTO();
                List<StatDTO> statList = new ArrayList<>();

                itemDTO.setId(item.getID());
                itemDTO.setName(item.getItemName());
                itemDTO.setType(item.getItemType().toString());
                
                statList = mapStatsToDTO(item);
                itemDTO.setStats(statList);
                
                // some of these have different names in the front-end compared to the back-end
                if (slot.equals(EquipmentSlot.RIGHT_RING)) {
                    itemMap.put("ring-right", itemDTO);
                } else if (slot.equals(EquipmentSlot.LEFT_RING)) {
                    itemMap.put("ring-left", itemDTO);
                } else if (slot.equals(EquipmentSlot.DELETE_ITEM_SLOT)) {
                    itemMap.put("delete-item", itemDTO);
                } else if (slot.equals(EquipmentSlot.NEW_ITEM_SLOT)) {
                    itemMap.put("new-item", itemDTO);
                } else {
                    itemMap.put(slot.toString().toLowerCase(), itemDTO);
                }
                
                // System.out.println(slot.toString().toLowerCase());
            
            }

            // System.out.println("Adding " + slot.getDisplayName());

        }

        return itemMap;

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
