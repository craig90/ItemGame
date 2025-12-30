package com.craig.itemgame.controller;

import com.craig.itemgame.model.Game;

import java.util.List;

import com.craig.itemgame.model.Character;
import com.craig.itemgame.model.CharacterFile;

public class GameController {

    private Game game;
    private CharacterController characterController;

    public GameController(Game game) {
        this.game = game;
        this.characterController = new CharacterController(game);
    }


    /**
     * Calls backend to create a new character with the supplied name.  Returns false if name is invalid
     * @param characterName
     * @return
     */
    public boolean createCharacter(String characterName) {
     
        Character character = null;

        // backend call
        if ((character = this.game.createCharacter(characterName)) == null) {
            return false;
        }
        
        this.characterController.setCharacter(character);
        
        return true;
        
    }

    /**
     * TODO: this shouldn't be needed once the view is implmented
     * @return
     */
    public CharacterController getCharacterController() {
        return this.characterController;
    }

    public List<CharacterFile> getCharacterFiles() {
        return this.game.getCharacterFiles();
    }


    public void loadCharacterFromFile(CharacterFile characterFile) throws Exception {
        Character character = this.game.loadCharacterFromFile(characterFile);
        this.characterController.setCharacter(character);
    }

    public void saveCharacter() {
        this.game.saveCharacter(); // which will save the current character
    }

    public boolean loadCharacter() {
        return true;
    }


}
