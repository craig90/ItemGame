package com.craig.itemgame.model;

public enum CharacterStatNames {
    HEALTH("Health"),
    LEVEL("Level"),
    EXPERIENCE("Experience"),
    DAMAGE("Damage"),
    DEFENCE("Defence"),
    FIRE_RESISTANCE("Fire Resistance"),
    POISON_RESISTANCE("Poison Resistance"),
    LIGHTNING_RESISTANCE("Lightning Resistance"),
    COLD_RESISTANCE("Cold Resistance"),
    MAGIC_RESISTANCE("Magic Resistance");

    private final String type;

    private CharacterStatNames(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }


}
