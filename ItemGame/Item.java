import java.util.ArrayList;
import java.util.Comparator;

public class Item {

    private static String IMAGE_DIR = "ItemGame/Images/";
    private static String WEAPON_FILE = "weapon.png";
    private static String HELM_FILE = "helm.png";
    private static String SHIELD_FILE = "shield.png";
    private static String CHEST_FILE = "chest.png";
    private static String AMULET_FILE = "amulet.png";
    private static String RING_FILE = "helm.png";
    private static String GLOVES_FILE = "gloves.png";
    private static String BELT_FILE = "belt.png";
    private static String PANTS_FILE = "pants.png";
    private static String BOOTS_FILE = "boots.png";

    public static final int MAX_STATS = 5; // will generate MAX_STATS - 1 due to how random() method works
    public static final int MIN_STATS = 2;    

    // all the different Item Types
    public static final String WEAPON = "Weapon";
    public static final String HELM = "Helm";
    public static final String CHEST = "Chest";
    public static final String SHIELD = "Shield";
    public static final String AMULET = "Amulet";
    public static final String RING = "Ring";
    public static final String GLOVES = "Gloves";
    public static final String BELT = "Belt";
    public static final String PANTS = "Pants";
    public static final String BOOTS = "Boots";

    // these needs to be placed in an array for RNG purposes
    public static final String[] ITEM_TYPES = {WEAPON, HELM, CHEST, SHIELD, AMULET, RING, GLOVES, BELT, PANTS, BOOTS};
   
    // these need to be in an array for RNG purposes
    public static final String[] ITEM_ADJECTIVES = {"Green", "Yellow", "Red", "Purple", "Orange", "Blue", "Gold", "Silver", 
    "Bronze", "Cool"};


    private String itemType;
    private String itemName;
    private ArrayList<ItemStat> statList;

    public Item(String itemType, String itemName) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.statList = new ArrayList<ItemStat>();
    }

    public String getItemType() {
        return this.itemType;
    }

    public String getItemName() {
        return this.itemName;
    }

    // TODO: this should not be changed more than once... could add code to prevent that
    public boolean setItemType(String itemType) {
        
        if (isLegitItemType(itemType) == true) {
            this.itemType = itemType;
            return true;
        }
        
        System.out.println("Invalid item type " + itemType);
        return false;

    }


    /**
     * Returns the File Image for the cooresonpding item
     * @return
     */
    public String getImageFile() {
        
        if (this.itemType.equals(WEAPON)) {
            return IMAGE_DIR + WEAPON_FILE;
        } else if (this.itemType.equals(HELM)) {
            return IMAGE_DIR + HELM_FILE;
        } else if (this.itemType.equals(SHIELD)) {
            return IMAGE_DIR + SHIELD_FILE;
        } else if (this.itemType.equals(CHEST)) {
            return IMAGE_DIR + CHEST_FILE;
        } else if (this.itemType.equals(AMULET)) {
            return IMAGE_DIR + AMULET_FILE;
        } else if (this.itemType.equals(RING)) {
            return IMAGE_DIR + RING_FILE;
        } else if (this.itemType.equals(GLOVES)) {
            return IMAGE_DIR + GLOVES_FILE;
        } else if (this.itemType.equals(BELT)) {
            return IMAGE_DIR + BELT_FILE;
        } else if (this.itemType.equals(PANTS)) {
            return IMAGE_DIR + PANTS_FILE;
        } else if (this.itemType.equals(BOOTS)) {
            return IMAGE_DIR + BOOTS_FILE;
        }
        return "";
    }

    /**
     * verifies that the item type is one of the designated item types
     * @param itemType
     * @return
     */
    public static boolean isLegitItemType(String itemType) {
        
        // make sure its a legit item type
        for (int i = 0; i < ITEM_TYPES.length; i++) {
            if (itemType.equals(ITEM_TYPES[i])) {
                return true;
            }
        }
        return false;
    }


    /**
     * TODO: add name verification logic
     * @param itemName
     * @return
     */
    public boolean setItemName(String itemName) {
        this.itemName = itemName;
        return true;
    }

    /*public ArrayList<Stat> getStatList() {
        return this.statList;
    }*/

    /**
     * returns the number of stats the Item has
     * @return
     */
    public int statListSize() {
        return this.statList.size();
    }

    /**
     * will add a an stat to the Item's statlist only if it is unique and there are not too many stats already
     * @param stat
     * @return
     */
    public boolean addStat(ItemStat stat) {
        
        // needs uniquness check
        if (this.statIsUnique(stat.getStatType()) == false) {
            return false;
        }

        if (this.statList.size() >= Item.MAX_STATS) {
            return false;
        }
        this.statList.add(stat);
        return true;
    
    }

    public ItemStat getStat(int index) {
        return this.statList.get(index);
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
        boolean[] foundOldStat = new boolean[this.statList.size()];
        boolean[] foundNewStat = new boolean[newItem.statList.size()];
    
        System.out.println("Stat Differences: ");
        
        for (int i = 0; i < this.statList.size(); i++) {
            ItemStat itemStat = this.statList.get(i);
            
            for (int j = 0; j < newItem.statListSize(); j++) {
                ItemStat newStat = newItem.getStat(j);
                
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
        for (int i = 0; i < this.statList.size(); i++) {
            if (foundOldStat[i] == false) {
                System.out.println(this.statList.get(i).type + " -" + this.statList.get(i).value);
            }
        }

        // these are stats that only exist on the new item
        for (int i = 0; i < newItem.statListSize(); i++) {
            if (foundNewStat[i] == false) {
                System.out.println(newItem.getStat(i).type + " +" + newItem.getStat(i).value);
            }
        }

    }

    /** sorts the stat list based on the order we defined in comparator */
    public void sortStats() {
        this.statList.sort(new StatComparator());
    }


    /**
     * 
     * @param newItem
     * @return
     */
    public boolean statIsUnique(String statType) {

        for (int i = 0; i < this.statList.size(); i++ ) {
            
            if (this.statList.get(i).type.equals(statType)) {
                // System.out.println("Trying to add " + ItemConstants.ITEM_STAT_TYPE[statType] + 
                // " but already have " + newItem.getStatList().get(j).type );
                return false;
            }
        }

        return true;

    }

    /**
     * returns true if the item has an appropriate name, type, and correct sized statlist
     * @return
     */
    public boolean isLegit() {

        if (this.itemName.equals("")) {
            System.out.println("Error: Item Name is not set or is empty");
            return false;
        } else if (isLegitItemType(this.itemType) == false) {
            return false;
        } else if (this.statList.size() < Item.MIN_STATS) {
            System.out.println("Error: Item doesn't have enough stats!");
            return false;
        } else if (this.statList.size() > Item.MAX_STATS) {
            System.out.println("Error: Item has too many stats!");
            return false;
        } else {
            return true;       
        }

    }

}



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

        for (int i = 0; i < ItemStat.ITEM_STAT_TYPE.length; i++) {
            
            if (statA.getStatType().equals(ItemStat.ITEM_STAT_TYPE[i])) {
                aIndex = i;
            }

            if (statB.getStatType().equals(ItemStat.ITEM_STAT_TYPE[i])) {
                bIndex = i; 
            }

        }

        return aIndex - bIndex;
    }

}