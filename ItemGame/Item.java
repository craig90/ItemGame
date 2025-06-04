import java.util.ArrayList;

public class Item {

    public static final String[] ITEM_TYPES = {"Amulet", "Chest", "Ring", "Gloves", "Weapon", "Shield", "Belt", "Pants", "Boots"};
   
   // TODO: this needs to be cleaned up because they're being defined in more than one place
    public static final String[] ITEM_STAT_TYPE = {"Damage", "Defence", "Health", "Poison Resistance", "Lightning Resistance", "Fire Resistance", "Cold Resistance", "Magic Resistance"};

    public static final String[] ITEM_ADJECTIVES = {"Green", "Yellow", "Red", "Purple", "Orange", "Blue", "Gold", "Silver", 
    "Bronze", "Cool"};


    private String itemType;
    private String itemName;
    private ArrayList<Stat> statList;

    public Item(String itemType, String itemName) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.statList = new ArrayList<Stat>();
    }

    public String getItemType() {
        return this.itemType;
    }

    public String getItemName() {
        return this.itemName;
    }

    // TODO: this should not be changed more than once... could add code to prevent that
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ArrayList<Stat> getStatList() {
        return this.statList;
    }

    // TODO: add logic for number of stats an item can hold once I figure out what the limit should be
    public void addStat(Stat stat) {
        this.statList.add(stat);
    }

    /**
     * Display the item, including item type, name, and all of its stats
     */
    public void displayItem() {
        System.out.println(this.itemType + " " + this.itemName);
        
        for (int i = 0; i < this.statList.size(); i++) {
            System.out.println(this.statList.get(i).type + ": " + this.statList.get(i).value);
        }
        System.out.println("");

    }

    /**
     * compares a new item to this item and prints any differences
     * @param newItem
     */
    public void itemComparison(Item newItem) {

        // do a quick error check to see if these items are of the same type
        if (this.itemType.equals(newItem.getItemType()) == false) {
            System.out.println("These items are not of the same type... comparison is pointless");
            return;
        }

        // this is a little complicated, we're going to have to run through all the stats from each item
        // and compare and see what will increase and what will decrease
        // show the two items for testing purposes
        this.displayItem();
        newItem.displayItem();

        // automatically initialized false
        // these help track stats that are unique to each item
        boolean[] foundOldStat = new boolean[this.getStatList().size()];
        boolean[] foundNewStat = new boolean[newItem.getStatList().size()];
    
        System.out.println("Stat Differences: ");
        
        for (int i = 0; i < this.getStatList().size(); i++) {
            Stat itemStat = this.getStatList().get(i);
            
            for (int j = 0; j < newItem.getStatList().size(); j++) {
                Stat newStat = newItem.getStatList().get(j);
                
                if (itemStat.type.equals(newStat.type)) {
                    foundOldStat[i] = true;
                    foundNewStat[j] = true;
                    // System.out.println("It has the same stat");
                    double difference = newStat.value - itemStat.value;
                    System.out.println(newStat.type + " " + difference);
                }

            }

        }

        // the stats that only exist on the old item
        for (int i = 0; i < this.getStatList().size(); i++) {
            if (foundOldStat[i] == false) {
                System.out.println(this.getStatList().get(i).type + " -" + this.getStatList().get(i).value);
            }
        }

        // these are stats that only exist on the new item
        for (int i = 0; i < newItem.getStatList().size(); i++) {
            if (foundNewStat[i] == false) {
                System.out.println(newItem.getStatList().get(i).type + " +" + newItem.getStatList().get(i).value);
            }
        }

    }


}
