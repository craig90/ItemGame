public class ItemStat extends Stat {

    // Item Stat Types
    public static final String HEALTH_STAT_NAME = "Health";
    public static final String DAMAGE_STAT_NAME = "Damage";
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

    public ItemStat() {

    }

    /**
     * returns true if sent a valid stat type, otherwise false
     * @param statType
     * @return
     */
    public static boolean isLegit(String statType) {

         // make sure its a legit stat type
        for (int i = 0; i < ITEM_STAT_TYPE.length; i++) {
            if (statType.equals(ITEM_STAT_TYPE[i])) {
                return true;
            }
        }
        return false;
    }


}
