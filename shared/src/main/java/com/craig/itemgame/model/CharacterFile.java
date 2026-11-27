package com.craig.itemgame.model;

public class CharacterFile {

    private String characterName;
    private String fileName;
    private String id;
    private double level;

    public CharacterFile() {

    }

    public CharacterFile(String fileName, String characterName, double level, String id) {
        this.fileName = fileName;
        this.characterName = characterName;
        this.level = level;
        this.id = id;
    }

    public boolean setCharacterName(String characterName) {
        
        if (characterName.length() < Character.MIN_NAME_LENGTH) { // TODO: duplicate logic
            return false;
        }
        this.characterName = characterName;
        return true;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public double getLevel() {
        return this.level;
    }

    public String getId() {
        return this.id;
    }
}
