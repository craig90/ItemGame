package com.craig.itemgame.DTOs;

public class StatDTO {

    private String type;
    private double value;

    public StatDTO(String type, double value) {
        this.type = type;
        this.value = value;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public double getValue() {
        return this.value;
    }

}
