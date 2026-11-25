package com.craig.itemgame.model;

public class CharacterHeader {

    private String characterName;
    private CharacterStat level;
    private String id;

    public String getCharacterName() {
        return this.characterName;
    }

    public CharacterStat getLevel() {
        return this.level;
    }

    public String getId() {
        return this.id;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setLevel(CharacterStat level) {
        this.level = level;
    }

    public void setId(String id) {
        this.id = id;
    }

}

