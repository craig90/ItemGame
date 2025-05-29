public class CharacterStat extends Stat {

    Character character;

    public static final double HEALTH_LEVEL_MODIFIER = 2.0;
    public static final double RESISTANCE_LEVEL_MODIFIER = 0.5;
    public static final double DEFENCE_LEVEL_MODIFIER = 3.0;
    public static final double DAMAGE_LEVEL_MODIFIER = 1.0;


    public CharacterStat() {

    }

    public CharacterStat(Character character, String type, double value) {
        this.character = character;
        this.type = type;
        this.value = value;
    }


    /*
     * Update the value of the character stat
     * 
     * This will take into acoount the character's level and any gear they are wearing
     */
    void updateValue() {

        // set stats first depending on level -- TODO: this can probably be moved to its own method
        if (this.type == StatNames.HEALTH) {
            this.value = character.getLevel() * HEALTH_LEVEL_MODIFIER;
        } else if (this.type == StatNames.DEFENSE) {
            this.value = character.getLevel() * DEFENCE_LEVEL_MODIFIER;
        } else if (this.type == StatNames.DAMAGE) {
            this.value = character.getLevel() * DAMAGE_LEVEL_MODIFIER;
        } else if (this.type == StatNames.COLD_RESISTANCE || this.type == StatNames.FIRE_RESISTANCE || 
            this.type == StatNames.LIGHTNING_RESISTANCE || this.type == StatNames.MAGIC_RESISTANCE || 
            this.type == StatNames.POISON_RESISTANCE) {
            this.value = character.getLevel() * RESISTANCE_LEVEL_MODIFIER; 
        }

        // System.out.println("We have " + this.character.getItemList().size() + " items");
        // go through the list of items
        for (int i = 0; i < this.character.getItemList().size(); i++) {
            
            // System.out.println("Item " + this.character.getItemList().get(i).getItemName() + " has " + 
            // this.character.getItemList().get(i).getStatList().size() + " stats");

            // go through the list of stats
            for (int j = 0; j < this.character.getItemList().get(i).getStatList().size(); j++) {
                
                // see if its the same type
                if (this.type.equals(this.character.getItemList().get(i).getStatList().get(j).getStatType())) {

                    // System.out.println(this.type + " is being updated");
                    this.value += this.character.getItemList().get(i).getStatList().get(j).getValue();
                } 
        
            }
        
        }

    }

}
