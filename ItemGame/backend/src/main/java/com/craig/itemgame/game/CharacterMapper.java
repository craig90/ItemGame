package com.craig.itemgame.game;

import com.craig.itemgame.DTOs.CharacterDTO;

import com.craig.itemgame.model.Character;

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

        return characterDTO;

    }

}
