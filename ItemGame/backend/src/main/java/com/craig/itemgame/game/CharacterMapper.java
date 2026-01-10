package com.craig.itemgame.game;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DummyDataSets.DummyCharacter;

import com.craig.itemgame.model.Character;

public class CharacterMapper {

    private final DummyCharacter dummy;

    public CharacterMapper() {
        dummy = new DummyCharacter();
    }

    /**
     * 
     * @param character
     * @return
     */
    public CharacterDTO mapCharacterToDTO(Character character) {
        return dummy.generate();  // TODO: this is just temporary until the converter is written
    }

}
