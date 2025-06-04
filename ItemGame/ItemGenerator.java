import java.util.Random;
import java.util.Comparator;

/**
 * The StatComparator Class sorts the stats by their type
 * The order of the types correspons to their order on the ITEM_STAT_TYPE array
 */
class StatComparator implements Comparator<Stat> {
    @Override
    public int compare(Stat statA, Stat statB) {
        
        // return - integer if b comes first // return 0 if even // return + integer if a comes first
        int aIndex = -1;
        int bIndex = -1;

        for (int i = 0; i < Item.ITEM_STAT_TYPE.length; i++) {
            
            if (statA.getStatType().equals(Item.ITEM_STAT_TYPE[i])) {
                aIndex = i;
            }

            if (statB.getStatType().equals(Item.ITEM_STAT_TYPE[i])) {
                bIndex = i; 
            }

        }

        return aIndex - bIndex;
    }
}

public class ItemGenerator {

    public static final int MAX_STATS = 5; // will generate MAX_STATS - 1 due to how random() method works
    public static final int MIN_STATS = 2;
    
    // TODO: I don't think that these should be define here - Probably should be defined in subclass ItemStat
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


    public ItemGenerator () {

    }

    /**
     * TODO: potentially should be in stat class? 
     * @param newItem
     * @return
     */
    private boolean statIsUnique(Item item, int statType) {

        for (int i = 0; i < item.getStatList().size(); i++ ){
            if (item.getStatList().get(i).type == Item.ITEM_STAT_TYPE[statType]) {
                // System.out.println("Trying to add " + ItemConstants.ITEM_STAT_TYPE[statType] + 
                // " but already have " + newItem.getStatList().get(j).type );
                return false;
            }
        }
        return true;

    }



    // TODO: we could add a lot more logic over different stat types
    // Do certain items have a smaller subset of stat types - it's possible that each item type has seperate logic
    // for stat creation
    public Item createNewItem() {
    // public void createNewItem() {

        // TODO; clean up the constructor for new Item
        Item newItem = new Item("", "");
        Random random = new Random();

        // decide how many stats the item will have
        int numberOfStats = random.nextInt(MIN_STATS, MAX_STATS);
        
        // decide the item type
        int itemNumber = random.nextInt(Item.ITEM_TYPES.length);

        // System.out.println("Generating New: " + ItecmConstants.ITEM_TYPES[itemNumber]);
        // System.out.println("with " + numberOfStats + " stats");
        
        newItem.setItemType(Item.ITEM_TYPES[itemNumber]);

        // choose the item Name from list of adjectives
        int itemNameAdjective = random.nextInt(Item.ITEM_ADJECTIVES.length);
        newItem.setItemName(Item.ITEM_ADJECTIVES[itemNameAdjective] + " " + Item.ITEM_TYPES[itemNumber]);

        // TODO: define item type names somewhere
        // Weapon is a special case where Damage has to be the main stat
        if (newItem.getItemType() == "Weapon") {
            System.out.println("Weapon has to have damage");
            int statValue = random.nextInt(WEAPON_DAMAGE_MIN_VALUE, WEAPON_DAMGE_MAX_VALUE);
            Stat stat = new Stat(Item.DAMAGE_STAT_NAME, statValue);
            newItem.addStat(stat);
        }
        
        // these are special cases where the first stat must be defense
        else if (newItem.getItemType() == "Chest") {
            System.out.println("Chest has to have defence");
            int statValue = random.nextInt(CHEST_DEFENCE_MIN_VALUE, CHEST_DEFENCE_MAX_VALUE);
            Stat stat = new Stat(Item.DEFENCE_STAT_NAME, statValue);
            newItem.addStat(stat);
        }

        else if (newItem.getItemType() == "Shield") {
            System.out.println("Shield has to have defence");
            int statValue = random.nextInt(SHIELD_DEFENCE_MIN_VALUE, SHIELD_DEFENCE_MAX_VALUE);
            Stat stat = new Stat(Item.DEFENCE_STAT_NAME, statValue);
            newItem.addStat(stat);
        } 

        // create a new UNIQUE stat - this loop will create new stats and also make sure we're not adding the same stat more than once
        while (newItem.getStatList().size() < numberOfStats) {
            
            int statValue = -1;
            int statType = random.nextInt(Item.ITEM_STAT_TYPE.length);
            
            // stat ranges are going to vary a bit depending on the type
            if (Item.ITEM_STAT_TYPE[statType].equals(Item.HEALTH_STAT_NAME)) {
                statValue = random.nextInt(HEALTH_MIN_VALUE, HEALTH_MAX_VALUE);
            } else if (Item.ITEM_STAT_TYPE[statType].equals(Item.DEFENCE_STAT_NAME)) {
                statValue = random.nextInt(DEFENCE_MIN_VALUE, DEFENCE_MAX_VALUE);
            } else if (Item.ITEM_STAT_TYPE[statType].equals(Item.DAMAGE_STAT_NAME)) {
                statValue = random.nextInt(DAMAGE_MIN_VALUE, DAMAGE_MAX_VALUE);    
            } else if (Item.ITEM_STAT_TYPE[statType].equals(Item.ALL_RESISTANCE_STAT_NAME)) {
                statValue = random.nextInt(ALL_RESISTANCE_MIN_VALUE, ALL_RESISTANCE_MAX_VALUE);    
            } else {
                statValue = random.nextInt(RESISTANCE_MIN_VALUE, RESISTANCE_MAX_VALUE);
            }

            // System.out.println("Adding stat " + ItemConstants.ITEM_STAT_TYPE[statType]);s
            // System.out.println("with value: " + statValue);

            // make sure the stat is unique
            if (statIsUnique(newItem, statType) == true) {
                Stat stat = new Stat(Item.ITEM_STAT_TYPE[statType], statValue);
                newItem.addStat(stat);
            }
        
        }

        // sort the stats on the new item - makes it easier to view and compare
        newItem.getStatList().sort(new StatComparator());
        return newItem;

    }

    /**
     * this main method is purely for resting the ItemGenerator
     * @param args
     */
    public static void main(String args[]){

        ItemGenerator itemGenerator = new ItemGenerator();
        Item item = itemGenerator.createNewItem();
        item.displayItem();

    }    

}
