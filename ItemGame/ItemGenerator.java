import java.util.Random;


public class ItemGenerator {

    public ItemGenerator () {

    }

    // TODO: we could add a lot more logic over different stat types
    // Do certain items have a smaller subset of stat types - it's possible that each item type has seperate logic
    // for stat creation
    public Item createNewItem() {
    // public void createNewItem() {

        // TODO; clean up the constructor for new Item
        Item newItem = new Item("", "");
        Random random = new Random();

        // decide how many stats the item will have +1 since upper end of range is exclusive
        int numberOfStats = random.nextInt(Item.MIN_STATS, Item.MAX_STATS+1);
        
        // decide the item type
        int itemNumber = random.nextInt(Item.ITEM_TYPES.length);

        // System.out.println("Generating New: " + ItecmConstants.ITEM_TYPES[itemNumber]);
        // System.out.println("with " + numberOfStats + " stats");
        
        newItem.setItemType(Item.ITEM_TYPES[itemNumber]);

        // choose the item Name from list of adjectives
        int itemNameAdjective = random.nextInt(Item.ITEM_ADJECTIVES.length);
        newItem.setItemName(Item.ITEM_ADJECTIVES[itemNameAdjective] + " " + Item.ITEM_TYPES[itemNumber]);

        // Weapon is a special case where Damage has to be the main stat
        if (newItem.getItemType() == Item.WEAPON) {
            System.out.println("Weapon has to have damage");
            int statValue = random.nextInt(ItemStat.WEAPON_DAMAGE_MIN_VALUE, ItemStat.WEAPON_DAMGE_MAX_VALUE);
            ItemStat stat = new ItemStat(ItemStat.DAMAGE_STAT_NAME, statValue);
            newItem.addStat(stat);
        }
        
        // these are special cases where the first stat must be defense
        else if (newItem.getItemType() == Item.CHEST) {
            System.out.println("Chest has to have defence");
            int statValue = random.nextInt(ItemStat.CHEST_DEFENCE_MIN_VALUE, ItemStat.CHEST_DEFENCE_MAX_VALUE);
            ItemStat stat = new ItemStat(ItemStat.DEFENCE_STAT_NAME, statValue);
            newItem.addStat(stat);
        }

        else if (newItem.getItemType() == Item.SHIELD) {
            System.out.println("Shield has to have defence");
            int statValue = random.nextInt(ItemStat.SHIELD_DEFENCE_MIN_VALUE, ItemStat.SHIELD_DEFENCE_MAX_VALUE);
            ItemStat stat = new ItemStat(ItemStat.DEFENCE_STAT_NAME, statValue);
            newItem.addStat(stat);
        } 

        // create a new UNIQUE stat - this loop will create new stats and also make sure we're not adding the same stat more than once
        while (newItem.statListSize() < numberOfStats) {
            
            int statValue = -1;
            int statType = random.nextInt(ItemStat.ITEM_STAT_TYPE.length);
            
            // stat ranges are going to vary a bit depending on the type
            if (ItemStat.ITEM_STAT_TYPE[statType].equals(ItemStat.HEALTH_STAT_NAME)) {
                statValue = random.nextInt(ItemStat.HEALTH_MIN_VALUE, ItemStat.HEALTH_MAX_VALUE);
            } else if (ItemStat.ITEM_STAT_TYPE[statType].equals(ItemStat.DEFENCE_STAT_NAME)) {
                statValue = random.nextInt(ItemStat.DEFENCE_MIN_VALUE, ItemStat.DEFENCE_MAX_VALUE);
            } else if (ItemStat.ITEM_STAT_TYPE[statType].equals(ItemStat.DAMAGE_STAT_NAME)) {
                statValue = random.nextInt(ItemStat.DAMAGE_MIN_VALUE, ItemStat.DAMAGE_MAX_VALUE);    
            } else if (ItemStat.ITEM_STAT_TYPE[statType].equals(ItemStat.ALL_RESISTANCE_STAT_NAME)) {
                statValue = random.nextInt(ItemStat.ALL_RESISTANCE_MIN_VALUE, ItemStat.ALL_RESISTANCE_MAX_VALUE);    
            } else {
                statValue = random.nextInt(ItemStat.RESISTANCE_MIN_VALUE, ItemStat.RESISTANCE_MAX_VALUE);
            }

            // System.out.println("Adding stat " + ItemConstants.ITEM_STAT_TYPE[statType]);s
            // System.out.println("with value: " + statValue);

            // make sure the stat is unique
            if (newItem.statIsUnique(ItemStat.ITEM_STAT_TYPE[statType]) == true) {
                ItemStat stat = new ItemStat(ItemStat.ITEM_STAT_TYPE[statType], statValue);
                newItem.addStat(stat);
            }
        
        }

        // sort the stats on the new item - makes it easier to view and compare
        newItem.sortStats();
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
