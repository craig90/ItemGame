package com.craig.itemgame.DTOs;

public class CharacterSummaryDTO {
    
    private String id;
    private String name;
    private double level;

    public CharacterSummaryDTO() { }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getLevel() {
        return this.level;
    }

}
