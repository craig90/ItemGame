package com.craig.itemgame.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.CharacterSummaryDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.model.Game;
import com.craig.itemgame.model.Items.Item;
import com.craig.itemgame.model.Character;
import com.craig.itemgame.model.CharacterFile;
import com.craig.itemgame.model.EquipmentSlot;

public class GameService {

    private Game game;
    private CharacterMapper characterMapper;

    public GameService() {
        this.game = new Game();
        this.characterMapper = new CharacterMapper();
    }

    public Game getGame() {
        return this.game;
    }

    public CharacterDTO getCharacter(String id) throws CharacterNotFoundException {

        try {
            Character character = this.game.getCharacter(id);
            return this.characterMapper.mapCharacterToDTO(character);
        } catch (Exception e) {
            throw new CharacterNotFoundException("Character not found in game: " + id);
        }

    }

    public List<CharacterSummaryDTO> getCharacters() {
    
        List<CharacterFile> characterFiles = this.game.getCharacterFiles();
        return this.characterMapper.mapCharactersToDTO(characterFiles);
    
    }

    public ItemDTO generateItem() {

        Item generatedItem = this.game.generateItem();
        return this.characterMapper.mapItemToDTO(generatedItem);
        
    }

    public CharacterDTO deleteItem(String characterId, String itemId) {

        try {
            Character character = this.game.getCharacter(characterId);
            character.getItemManager().removeDeleteItem();
            return this.characterMapper.mapCharacterToDTO(character);
        } catch (Exception e) {
            throw new CharacterNotFoundException("Character not found in game: " + characterId);
        }

    }

    public CharacterDTO equipItem(String characterId, String itemId, String slotType) {

        EquipmentSlot targetSlot;

        // TODO: it's not ideal to have to change the formatting like this
        String targetSlotString = slotType.replace("-", "_").toUpperCase();
        targetSlot = EquipmentSlot.valueOf(targetSlotString);
 
        try {
            Character character = this.game.getCharacter(characterId);
            Item item = character.getItemManager().getItemById(itemId);
            EquipmentSlot sourceSlot = character.getItemManager().findEquipmentSlot(itemId);

            if (sourceSlot != null) {
                character.getItemManager().removeItem(sourceSlot);
                character.getItemManager().equipItem(targetSlot, item);
            } else if (character.getItemManager().getInventory().isInInventory(itemId) == true) {
                character.getItemManager().getInventory().removeItem(item);
                character.getItemManager().equipItem(targetSlot, item);
            } else {
                throw new CharacterNotFoundException("Item not found and therefore can't be moved: " + characterId);    
            }

            return this.characterMapper.mapCharacterToDTO(character);
        
        } catch (Exception e) {
            throw new CharacterNotFoundException("Could not move item for character with: " + characterId);
        }

        

    }

}
