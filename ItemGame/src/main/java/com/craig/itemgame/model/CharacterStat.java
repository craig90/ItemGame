package com.craig.itemgame.model;

import com.craig.itemgame.model.Items.Item;

public class CharacterStat extends Stat {

    public static final double HEALTH_LEVEL_MODIFIER = 2.0;
    public static final double RESISTANCE_LEVEL_MODIFIER = 0.5;
    public static final double DEFENCE_LEVEL_MODIFIER = 3.0;
    public static final double DAMAGE_LEVEL_MODIFIER = 1.0;

    private double baseValue = 0.0;
    private double itemValue = 0.0;
    private static final double INIT_VALUE = 0.0;

    public CharacterStat() { } // needed for potential subclasses

    public CharacterStat(CharacterStatNames statName) {
        this.type = statName.getType();
        this.value = INIT_VALUE;
    }

    /**
     * set stats first depending on level
     */
    private void updateBaseValue(double level) {

        if (this.type.equals(CharacterStatNames.HEALTH.getType())) {
            this.baseValue = level * HEALTH_LEVEL_MODIFIER;
        } else if (this.type.equals(CharacterStatNames.DEFENCE.getType())) {
            this.baseValue = level * DEFENCE_LEVEL_MODIFIER;
        } else if (this.type.equals(CharacterStatNames.DAMAGE.getType())) {
            this.baseValue = level * DAMAGE_LEVEL_MODIFIER;
        } else if (this.type.equals(CharacterStatNames.COLD_RESISTANCE.getType()) 
            || this.type.equals(CharacterStatNames.FIRE_RESISTANCE.getType()) 
            || this.type.equals(CharacterStatNames.LIGHTNING_RESISTANCE.getType())
            || this.type.equals(CharacterStatNames.MAGIC_RESISTANCE.getType())
            || this.type.equals(CharacterStatNames.POISON_RESISTANCE.getType())) {
            this.baseValue = level * RESISTANCE_LEVEL_MODIFIER; 
        }
    
    }

    /**
     * updates the itemValue stat taking into account any items with this stat type
     */
    private void updateValueWithItems(ItemManager itemManager) {
        
        // reset itemValue to 0
        this.itemValue = 0.0;

        for (Item item : itemManager.getAllEquippedItems()) {

            // go through the list of stats
            for (int j = 0; j < item.statListSize(); j++) {
                
                // see if its the same type
                if (this.type.equals(item.getStat(j).getStatType())) {
                    this.itemValue += item.getStat(j).getValue();
                }

                // special case for All Resistance as it will increase each resitance type
                if (item.getStat(j).getStatType().equals(ItemStat.ALL_RESISTANCE_STAT_NAME) &&
                    (this.type.equals(ItemStat.FIRE_RESISTANCE_STAT_NAME) || 
                    this.type.equals(ItemStat.COLD_RESISTANCE_STAT_NAME) || 
                    this.type.equals(ItemStat.POISON_RESISTANCE_STAT_NAME) || 
                    this.type.equals(ItemStat.MAGIC_RESISTANCE_STAT_NAME) || 
                    this.type.equals(ItemStat.LIGHTNING_RESISTANCE_STAT_NAME))) {
                        this.itemValue += item.getStat(j).getValue();   
                    
                }

            }
    
        }

    }

    /*
     * Update the value of the character stat
     * 
     * This will take into acoount the character's level and any gear they are wearing
     */
    public void updateValue(ItemManager itemManager, double level) {

        this.updateBaseValue(level);
        this.updateValueWithItems(itemManager);

        this.value = this.baseValue + this.itemValue;

    }

    /**
     * returns the base stat value
     * @return
     */
    public double getBaseValue() {
        return this.baseValue;
    }


}
