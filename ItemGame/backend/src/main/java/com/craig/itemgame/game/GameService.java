package com.craig.itemgame.game;

import java.util.List;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.CharacterSummaryDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.exceptions.InvalidItemMoveException;
import com.craig.itemgame.exceptions.ItemNotFoundException;
import com.craig.itemgame.exceptions.CharacterNotFoundException;
import com.craig.itemgame.model.Game;
import com.craig.itemgame.model.InventorySlot;
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
            throw new CharacterNotFoundException(id);
        }

    }

    public List<CharacterSummaryDTO> getCharacters() {
    
        List<CharacterFile> characterFiles = this.game.getCharacterFiles();
        return this.characterMapper.mapCharactersToDTO(characterFiles);
    
    }

    // TODO: this should probably be tied to the character somehow
    public ItemDTO generateItem() {

        Item generatedItem = this.game.generateItem();
        return this.characterMapper.mapItemToDTO(generatedItem);
        
    }

    // TODO: If the back-end has no "delete item", it will still report success
    public CharacterDTO deleteItem(String characterId, String itemId) {

        try {
            Character character = this.game.getCharacter(characterId);
            character.getItemManager().removeDeleteItem();
            return this.characterMapper.mapCharacterToDTO(character);
        } catch (Exception e) {
            throw new CharacterNotFoundException(characterId);
        }

    }

    public CharacterDTO equipItem(String characterId, String itemId, String slotType) {

        Character character;
        Item item;
        EquipmentSlot sourceSlot, targetSlot;

        try {
            character = this.game.getCharacter(characterId);
        } catch (Exception e) {
            throw new CharacterNotFoundException("Could not find character with id: " + characterId);
        }

        item = character.getItemManager().getItemById(itemId);
        
        if (item == null) {
            throw new ItemNotFoundException(character.getName(), itemId);
        }

        // TODO: it's not ideal to have to change the formatting like this
        targetSlot = EquipmentSlot.valueOf(slotType.replace("-", "_").toUpperCase());
        sourceSlot = character.getItemManager().findEquipmentSlot(itemId);

        if (character.getItemManager().canEquipItem(targetSlot, item) == false) {
            throw new InvalidItemMoveException("Cannot move " + item.getItemName() + " to " + targetSlot.getDisplayName());
        }

        if (sourceSlot != null) {
            character.getItemManager().removeItem(sourceSlot);
            character.getItemManager().equipItem(targetSlot, item);
        } else if (character.getItemManager().getInventory().isInInventory(itemId) == true) {
            character.getItemManager().getInventory().removeItem(item);
            character.getItemManager().equipItem(targetSlot, item);
        // no source slot and it's not in the inventory so the item can't be found
        } else {
            throw new InvalidItemMoveException("Item " + item.getItemName() + " not found and therefore can't be moved");    
        }

        character.updateStats();

        return this.characterMapper.mapCharacterToDTO(character);
        
        

    }

    public CharacterDTO moveItem(String characterId, String itemId, int x, int y) {

        Character character;
        Item item;
        EquipmentSlot sourceSlot;
        InventorySlot destInvSlot;

        try {
            character = this.game.getCharacter(characterId);
        } catch (Exception e) {
            throw new CharacterNotFoundException(characterId);
        }
        
        item = character.getItemManager().getItemById(itemId);
        
        if (item == null) {
            throw new ItemNotFoundException(character.getName(), itemId);
        }
        
        sourceSlot = character.getItemManager().findEquipmentSlot(itemId);
        destInvSlot = character.getItemManager().getGridSlot(x, y);

        if (destInvSlot == null) {
            throw new InvalidItemMoveException("Can't find destination inventory slot (x,y): (" + x + "," + y +")");
        }

        if (sourceSlot != null) {
            character.getItemManager().removeItem(sourceSlot);
            character.getItemManager().getInventory().placeItem(item, destInvSlot);
        } else if (character.getItemManager().getInventory().isInInventory(itemId) == true) {
            character.getItemManager().getInventory().removeItem(item);
            character.getItemManager().getInventory().placeItem(item, destInvSlot);
        // no source slot and it's not in the inventory so the item can't be found
        } else {
            throw new InvalidItemMoveException("Item not found and therefore can't be moved: " + characterId);    
        }

        character.updateStats();

        return this.characterMapper.mapCharacterToDTO(character);

    }

}
