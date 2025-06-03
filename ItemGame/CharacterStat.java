public class CharacterStat extends Stat {

    Character character;

    public static final double HEALTH_LEVEL_MODIFIER = 2.0;
    public static final double RESISTANCE_LEVEL_MODIFIER = 0.5;
    public static final double DEFENCE_LEVEL_MODIFIER = 3.0;
    public static final double DAMAGE_LEVEL_MODIFIER = 1.0;

    private double baseValue = 0.0;
    private double itemValue = 0.0;

    public CharacterStat() {

    }

    public CharacterStat(Character character, String type, double value) {
        this.character = character;
        this.type = type;
        this.value = value;
    }

    /**
     * set stats first depending on level
     */
    private void updateBaseValue() {

        if (this.type == StatNames.HEALTH) {
            this.baseValue = character.getLevel() * HEALTH_LEVEL_MODIFIER;
        } else if (this.type == StatNames.DEFENSE) {
            this.baseValue = character.getLevel() * DEFENCE_LEVEL_MODIFIER;
        } else if (this.type == StatNames.DAMAGE) {
            this.baseValue = character.getLevel() * DAMAGE_LEVEL_MODIFIER;
        } else if (this.type == StatNames.COLD_RESISTANCE || this.type == StatNames.FIRE_RESISTANCE || 
            this.type == StatNames.LIGHTNING_RESISTANCE || this.type == StatNames.MAGIC_RESISTANCE || 
            this.type == StatNames.POISON_RESISTANCE) {
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
        for (int i = 0; i < this.character.getItemList().size(); i++) {
            
            // go through the list of stats
            for (int j = 0; j < this.character.getItemList().get(i).getStatList().size(); j++) {
                
                // see if its the same type
                if (this.type.equals(this.character.getItemList().get(i).getStatList().get(j).getStatType())) {
                    this.itemValue += this.character.getItemList().get(i).getStatList().get(j).getValue();
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
