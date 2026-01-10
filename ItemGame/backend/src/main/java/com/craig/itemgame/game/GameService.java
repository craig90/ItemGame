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

    public CharacterDTO getCharacter(String id) {
        Character character = this.game.getCharacter(id);

        // turn Character into DTO
        CharacterDTO characterDTO = this.characterMapper.mapCharacterToDTO(character);

        // return
        return characterDTO;
    }

}
