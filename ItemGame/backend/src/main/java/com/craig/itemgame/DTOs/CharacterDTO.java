package com.craig.itemgame.DTOs;

import java.util.Map;

public class CharacterDTO {
    
    private String id;
    private String name;
    private double level;
    private double experience;
    private double health;
    private double damage;
    private double defence;
    private double fireResistance;
    private double lightningResistance;
    private double coldResistance;
    private double poisonResistance;
    private double magicResistance;

    // TODO: saving as String.. at least for now because the front-end wants strings
    private Map<String, ItemDTO> items;

    public CharacterDTO() { }

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

    public void setHealth(double health) {
        this.health = health;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setFireResistence(double fireResistance) {
        this.fireResistance = fireResistance;
    }

    public void setLightningResistance(double lightningResistance) {
        this.lightningResistance = lightningResistance;
    }

    public void setColdResistance(double coldResistance) {
        this.coldResistance = coldResistance;
    }

    public void setMagicResistance(double magicResistance) {
        this.magicResistance = magicResistance;
    }

    public void setPoisonResistance(double poisonResistance) {
        this.poisonResistance = poisonResistance;
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

    public double getHealth() {
        return this.health;
    }

    public double getDamage() {
        return this.damage;
    }

    public double getDefence() {
        return this.defence;
    }

    public double getFireResistance() {
        return this.fireResistance;
    }

    public double getLightningResistance() {
        return this.lightningResistance;
    }

    public double getPoisonResistance() {
        return this.poisonResistance;
    }

    public double getColdResistance() {
        return this.coldResistance;
    }

    public double getMagicResistance() {
        return this.magicResistance;
    }

    public Map<String, ItemDTO> getItems() {
        return this.items;
    }

}
