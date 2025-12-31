package com.craig.itemgame.DTOs;

import java.util.List;

public class ItemDTO {

    private String id;
    private String name;
    private String type; // TODO: update to use enum
    private List<StatDTO> stats;

    public ItemDTO() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
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

    public String getType() {
        return this.type;
    }

    public List<StatDTO> getStats() {
        return this.stats;
    }

}
