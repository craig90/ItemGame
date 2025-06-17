public class CharacterStat extends Stat {

    Character character;

    public static final double HEALTH_LEVEL_MODIFIER = 2.0;
    public static final double RESISTANCE_LEVEL_MODIFIER = 0.5;
    public static final double DEFENCE_LEVEL_MODIFIER = 3.0;
    public static final double DAMAGE_LEVEL_MODIFIER = 1.0;

    private double baseValue = 0.0;
    private double itemValue = 0.0;
    private static final double INIT_VALUE = 0.0;

    public CharacterStat() { } // needed for potential subclasses

    public CharacterStat(Character character, String type) {
        this.character = character;
        this.type = type;
        this.value = INIT_VALUE;
    }

    /**
     * set stats first depending on level
     */
    private void updateBaseValue() {

        if (this.type == CharacterStatNames.HEALTH) {
            this.baseValue = character.getLevel() * HEALTH_LEVEL_MODIFIER;
        } else if (this.type == CharacterStatNames.DEFENCE) {
            this.baseValue = character.getLevel() * DEFENCE_LEVEL_MODIFIER;
        } else if (this.type == CharacterStatNames.DAMAGE) {
            this.baseValue = character.getLevel() * DAMAGE_LEVEL_MODIFIER;
        } else if (this.type == CharacterStatNames.COLD_RESISTANCE || this.type == CharacterStatNames.FIRE_RESISTANCE || 
            this.type == CharacterStatNames.LIGHTNING_RESISTANCE || this.type == CharacterStatNames.MAGIC_RESISTANCE || 
            this.type == CharacterStatNames.POISON_RESISTANCE) {
            this.baseValue = character.getLevel() * RESISTANCE_LEVEL_MODIFIER; 
        }
    
    }

    /**
     * updates the itemValue stat taking into account any items with this stat type
     */
    private void updateValueWithItems() {
        
        // reset itemValue to 0
        this.itemValue = 0.0;

        // go through the list of items
        for (int i = 0; i < this.character.getItemListSize(); i++) {
            
            // go through the list of stats
            for (int j = 0; j < this.character.getItem(i).statListSize(); j++) {
                
                // see if its the same type
                if (this.type.equals(this.character.getItem(i).getStat(j).getStatType())) {
                    this.itemValue += this.character.getItem(i).getStat(j).getValue();
                }

                // special case for All Resistance as it will increase each resitance type
                if (this.character.getItem(i).getStat(j).getStatType().equals(ItemStat.ALL_RESISTANCE_STAT_NAME) &&
                    (this.type.equals(ItemStat.FIRE_RESISTANCE_STAT_NAME) || this.type.equals(ItemStat.COLD_RESISTANCE_STAT_NAME)
                    || this.type.equals(ItemStat.POISON_RESISTANCE_STAT_NAME) || this.type.equals(ItemStat.MAGIC_RESISTANCE_STAT_NAME)
                    || this.type.equals(ItemStat.LIGHTNING_RESISTANCE_STAT_NAME))) {

                    this.itemValue += this.character.getItem(i).getStat(j).getValue();   
                    
                }

            }

        }
    }

    /*
     * Update the value of the character stat
     * 
     * This will take into acoount the character's level and any gear they are wearing
     */
    public void updateValue() {

        this.updateBaseValue();
        this.updateValueWithItems();

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
