package com.craig.itemgame.game;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.model.Game;
import com.craig.itemgame.model.Character;

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

}
