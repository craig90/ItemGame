package com.craig.itemgame.model;

public class CharacterHeader {

    private String characterName;
    private CharacterStat level;

    public String getCharacterName() {
        return this.characterName;
    }

    public CharacterStat getLevel() {
        return this.level;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setLevel(CharacterStat level) {
        this.level = level;
    }

}

