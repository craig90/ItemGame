package com.craig.itemgame.model;

public class CharacterFile {

    private String characterName;
    private String fileName;
    private double level;

    public CharacterFile() {

    }

    public CharacterFile(String fileName, String characterName, double level) {
        this.fileName = fileName;
        this.characterName = characterName;
        this.level = level;
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

    public String getCharacterName() {
        return this.characterName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public double getLevel() {
        return this.level;
    }
}
