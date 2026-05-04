package com.craig.itemgame.DTOs;

import java.util.List;

import com.craig.itemgame.model.ItemType;

public class ItemDTO {

    private String id;
    private String name;
    private ItemType type;
    private List<StatDTO> stats;

    public ItemDTO() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setStats(List<StatDTO> stats) {
        this.stats = stats;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ItemType getType() {
        return this.type;
    }

    public List<StatDTO> getStats() {
        return this.stats;
    }

}
