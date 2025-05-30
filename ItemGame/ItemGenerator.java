import java.util.Random;

public class ItemGenerator {

    public static final int MAX_STATS = 5;
    public static final int MIN_STATS = 2;

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

        // an item can have 2-5 stats
        // TODO: these should be saved somewhere as constants
        int numberOfStats = random.nextInt(MIN_STATS, MAX_STATS);
        int itemNumber = random.nextInt(ItemConstants.ITEM_TYPES.length); // Returns a random int between 0 (inclusive) and 100 (exclusive)

        System.out.println("Generating New: " + ItemConstants.ITEM_TYPES[itemNumber]);
        System.out.println("with " + numberOfStats + " stats");
        
        newItem.setItemType(ItemConstants.ITEM_TYPES[itemNumber]);


        // create a new UNIQUE stat
        int i = 0;
        while (i < numberOfStats) {
            int statType = random.nextInt(ItemConstants.ITEM_STAT_TYPE.length);
            System.out.println("Adding stat " + ItemConstants.ITEM_STAT_TYPE[statType]);

            int statValue = random.nextInt(5, 20);
            System.out.println("with value: " + statValue);

            boolean uniqueStatType = true;
            for (int j = 0; j < newItem.getStatList().size(); j++ ){
                if (newItem.getStatList().get(j).type == ItemConstants.ITEM_STAT_TYPE[statType]) {
                    uniqueStatType = false;
                    System.out.println("Trying to add " + ItemConstants.ITEM_STAT_TYPE[statType] + 
                    " but already have " + newItem.getStatList().get(j).type );
                }
            }

            if (uniqueStatType == true) {
                Stat stat = new Stat(ItemConstants.ITEM_STAT_TYPE[statType], statValue);
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
