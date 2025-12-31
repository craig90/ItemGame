package com.craig.itemgame.DTOs;

import java.util.List;

public class CharacterDTO {
    
    private String id;
    private String name;
    private double level;
    private double experience;
    private List<ItemDTO> items;

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

    public void setItems(List<ItemDTO> items) {
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

    public List<ItemDTO> getItems() {
        return this.items;
    }

}
