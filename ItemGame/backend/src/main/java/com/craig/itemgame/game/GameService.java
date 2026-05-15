package com.craig.itemgame.game;

import java.util.List;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.CharacterSummaryDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.model.Game;
import com.craig.itemgame.model.Items.Item;
import com.craig.itemgame.model.Character;
import com.craig.itemgame.model.CharacterFile;

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

}
