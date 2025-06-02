import java.util.Random;

public class ItemGenerator {

    public static final int MAX_STATS = 5;
    public static final int MIN_STATS = 2;
    
    // TODO: I don't think that these should be define here - Probably should be defined in subclass ItemStat
    public static final int STAT_MIN_VALUE = 5;
    public static final int STAT_MAX_VALUE = 20;

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

        // create a new UNIQUE stat - this loop will create new stats and also make sure we're not adding the same stat more than once
        int i = 0;
        while (i < numberOfStats) {
            
            boolean uniqueStatType = true;
            int statType = random.nextInt(Item.ITEM_STAT_TYPE.length);
            // System.out.println("Adding stat " + ItemConstants.ITEM_STAT_TYPE[statType]);
            int statValue = random.nextInt(STAT_MIN_VALUE, STAT_MAX_VALUE);
            // System.out.println("with value: " + statValue);

            // make sure the stat is unique
            for (int j = 0; j < newItem.getStatList().size(); j++ ){
                if (newItem.getStatList().get(j).type == Item.ITEM_STAT_TYPE[statType]) {
                    uniqueStatType = false;
                    // System.out.println("Trying to add " + ItemConstants.ITEM_STAT_TYPE[statType] + 
                    // " but already have " + newItem.getStatList().get(j).type );
                }
            }

            if (uniqueStatType == true) {
                Stat stat = new Stat(Item.ITEM_STAT_TYPE[statType], statValue);
                newItem.addStat(stat);
                i++;
            }
        
        }

        return newItem;

    }


    public static void main(String args[]){

        ItemGenerator itemGenerator = new ItemGenerator();
        itemGenerator.createNewItem();

    }    

}
