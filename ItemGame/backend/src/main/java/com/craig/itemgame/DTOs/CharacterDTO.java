package com.craig.itemgame.DTOs;

import java.util.Map;

public class CharacterDTO {
    
    private String id;
    private String name;
    private double level;
    private double experience;
    private Map<String, ItemDTO> items;  // TODO: change to slot type enum as key

    public CharacterDTO() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public void setItems(Map<String, ItemDTO> items) {
        this.items = items;
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

    public double getExperience() {
        return this.experience;
    }

    public Map<String, ItemDTO> getItems() {
        return this.items;
    }

}
