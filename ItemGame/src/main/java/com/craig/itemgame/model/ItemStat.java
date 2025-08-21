package com.craig.itemgame.model;

import java.util.Map;

public class ItemStat extends Stat {

    private static Map<String, Integer> statPriority = Map.of(
        "Damage", 1,
        "Health", 2,
        "Defence", 3,
        "All Resistance", 4,
        "Fire Resistance", 5,
        "Poison Resistance", 6,
        "Lightning Resistance", 7,
        "Cold Resistance", 8,
        "Magic Resistance", 9
    );

    // Item Stat Types
    public static final String DAMAGE_STAT_NAME = "Damage";
    public static final String HEALTH_STAT_NAME = "Health";
    public static final String DEFENCE_STAT_NAME = "Defence";
    public static final String ALL_RESISTANCE_STAT_NAME = "All Resistance";
    public static final String FIRE_RESISTANCE_STAT_NAME = "Fire Resistance";
    public static final String POISON_RESISTANCE_STAT_NAME = "Poison Resistance";
    public static final String LIGHTNING_RESISTANCE_STAT_NAME = "Lightning Resistance";
    public static final String COLD_RESISTANCE_STAT_NAME = "Cold Resistance";
    public static final String MAGIC_RESISTANCE_STAT_NAME = "Magic Resistance";

    // these need to be in an array for RNG purposes
    public static final String[] ITEM_STAT_TYPE = {DAMAGE_STAT_NAME, HEALTH_STAT_NAME, DEFENCE_STAT_NAME, 
        ALL_RESISTANCE_STAT_NAME, FIRE_RESISTANCE_STAT_NAME, POISON_RESISTANCE_STAT_NAME, 
        LIGHTNING_RESISTANCE_STAT_NAME, COLD_RESISTANCE_STAT_NAME, MAGIC_RESISTANCE_STAT_NAME};


    public static final int RESISTANCE_MIN_VALUE = 5;
    public static final int RESISTANCE_MAX_VALUE = 20;

    public static final int ALL_RESISTANCE_MIN_VALUE = 5;
    public static final int ALL_RESISTANCE_MAX_VALUE = 10;
    
    public static final int HEALTH_MIN_VALUE = 20;
    public static final int HEALTH_MAX_VALUE = 60;

    public static final int DEFENCE_MIN_VALUE = 25;
    public static final int DEFENCE_MAX_VALUE = 100;

    public static final int CHEST_DEFENCE_MIN_VALUE = 100;
    public static final int CHEST_DEFENCE_MAX_VALUE = 500;

    public static final int SHIELD_DEFENCE_MIN_VALUE = 100;
    public static final int SHIELD_DEFENCE_MAX_VALUE = 600;

    public static final int DAMAGE_MIN_VALUE = 25;
    public static final int DAMAGE_MAX_VALUE = 100;

    public static final int WEAPON_DAMAGE_MIN_VALUE = 100;
    public static final int WEAPON_DAMGE_MAX_VALUE = 500;


    public ItemStat(String type, double value) {
        this.type = type;
        this.value = value;
    }


    /**
     * returns true if sent a valid stat type, otherwise false
     * @param statType
     * @return
     */
    public static boolean isLegitStat(String statType) {

         // make sure its a legit stat type
        for (int i = 0; i < ITEM_STAT_TYPE.length; i++) {
            if (statType.equals(ITEM_STAT_TYPE[i])) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, Integer> getStatPriority() {
        return statPriority;
    }

}

