package com.craig.itemgame.model.Items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.craig.itemgame.model.FileKeywords;
import com.craig.itemgame.model.ItemStat;
import com.craig.itemgame.model.ItemType;
import com.craig.itemgame.model.StatDifference;
import com.craig.itemgame.ui.ImageResizer;
import com.craig.itemgame.ui.ItemLayeredPane;

public abstract class Item {

    // TODO: move these to Item constants file
    protected static String IMAGE_DIR = FileKeywords.WORKING_DIR + "src/main/resources/images/";
    protected static String WEAPON_FILE = "weapon.png";
    protected static String HELM_FILE = "helm.png";
    protected static String SHIELD_FILE = "shield.png";
    protected static String CHEST_FILE = "chest.png";
    protected static String AMULET_FILE = "amulet.png";
    protected static String RING_FILE = "ring.png";
    protected static String GLOVES_FILE = "gloves.png";
    protected static String BELT_FILE = "belt.png";
    protected static String PANTS_FILE = "pants.png";
    protected static String BOOTS_FILE = "boots.png";

    public static final int MAX_STATS = 5; // will generate MAX_STATS - 1 due to how random() method works
    public static final int MIN_STATS = 2;    

    private static final int MIN_ITEM_NAME_LENGTH = 4;

    // these need to be in an array for RNG purposes
    public static final String[] ITEM_ADJECTIVES = {"Green", "Yellow", "Red", "Purple", "Orange", "Blue", "Gold", "Silver", 
    "Bronze", "Cool"};

    protected JLabel itemImageLabel;
    protected ImageIcon itemImageIcon;

    private String itemName;
    private ArrayList<ItemStat> statList;

    protected ItemType itemType;

    private String id;

    /**
     * Constructor for a new Item, will create an Item ID
     * @param itemName
     * @param itemType
     */
    public Item(String itemName, ItemType itemType) {
        this.itemName = itemName;
        this.statList = new ArrayList<ItemStat>();
        this.itemType = itemType;
        this.id = ItemIdGenerator.generateUniqueID();
        this.setImageLabel();
    }

    public abstract int getWidthInGridUnits();

    public abstract int getHeightInGridUnits();

    public ItemType getItemType() {
        return this.itemType;
    }

    public String getID() {
        return this.id;
    }

    public abstract String getImageFile();

    public String getItemName() {
        return this.itemName;
    }

    private void setImageIcon() {
        try {
            this.itemImageIcon = ImageResizer.resizeImageIcon(new ImageIcon(getImageFile()),
                ItemLayeredPane.getItemWidth(this.getItemType()), ItemLayeredPane.getItemHeight(this.getItemType()));
        } catch (Exception e) {
            System.out.println("Error resizing image");
            System.out.println(e.getMessage());
            System.exit(1); // fairly fatal flaw if the image cannot be correctly resized - just exit
        }
    }

    public void setImageLabel() {
        setImageIcon();
        this.itemImageLabel = new JLabel(this.itemImageIcon);
        // System.out.println("Setting icon for item type " + this.itemName);
    }

    public JLabel getItemImageLabel() {
        return this.itemImageLabel;
    }

    /**
     * verifies that the item type is one of the designated item types
     * @param itemType
     * @return
     */
    public boolean isLegitItemType(ItemType itemType) {
        
        ItemType[] itemTypes = ItemType.values();

        // make sure its a legit item type
        for (int i = 0; i < itemTypes.length; i++) {
            if (this.getItemType() == itemTypes[i]) {
                return true;
            }
        }
        return false;
    }


    /**
     * Sets the item name and does a quick check that's it's an appropriate length - could add more verification
     * if we want to make sure it's multiple words
     * 
     * @param itemName
     * @return
     */
    public boolean setItemName(String itemName) {
        
        if (itemName.length() < MIN_ITEM_NAME_LENGTH) {
            return false;
        }
        
        this.itemName = itemName;
        return true;
    }

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
        System.out.println(this.itemName);
        
        for (int i = 0; i < this.statList.size(); i++) {
            System.out.println(this.statList.get(i).getStatType() + ": " + this.statList.get(i).getValue());
        }
        System.out.println("");

    }


    /**
     * 
     * @param statDifferences
     * @param newItem
     * @return
     */
    public static void itemComparisonNewItemOnly(ArrayList<StatDifference> statDifferences, Item newItem) {

        // cycle through all the stats - there is no old item so every stat is an upgrade
        for (ItemStat newStat : newItem.statList) {
            // ItemStat newStat = newItem.getStat(i);
            
            double difference = newStat.getValue();
            // System.out.println(newStat.type + " " + difference);
            StatDifference statDifference = new StatDifference(newStat);
        
            statDifference.setIsBetterStat(true);
            statDifference.setDifference(difference);
            statDifferences.add(statDifference);
        }

    }

    /**
     * 
     * @param statDifferences
     * @param oldItem
     */
    public static void itemComparisonOldItemOnly(ArrayList<StatDifference> statDifferences, Item oldItem) {

        for (ItemStat oldStat : oldItem.statList) {
            //ItemStat oldStat = oldItem.getStat(i);
            
            double difference = oldStat.getValue();
            // System.out.println(oldStat.type + " " + difference);
            StatDifference statDifference = new StatDifference(oldStat);
        
            statDifference.setIsBetterStat(false);
            statDifference.setDifference(difference);
            statDifferences.add(statDifference);
        }

    }


    /**
     * 
     * @param newItem
     * @param foundNewStat
     * @param statDifferences
     */
    public static void itemComparisonAddNewStats(Item newItem, boolean[] foundNewStat, ArrayList<StatDifference> statDifferences) {

        // these are stats that only exist on the new item
        for (int i = 0; i < newItem.statListSize(); i++) {
            if (foundNewStat[i] == false) {
                // System.out.println(newItem.getStat(i).type + " +" + newItem.getStat(i).value);
                StatDifference statDifference = new StatDifference(newItem.getStat(i));
                statDifference.setIsBetterStat(true);
                statDifference.setDifference(newItem.getStat(i).getValue());
                statDifferences.add(statDifference);
            }
        }

    }

    /**
     * 
     * @param oldItem
     * @param foundOldStat
     * @param statDifferences
     */
    public static void itemComparisonAddOldStats(Item oldItem, boolean[] foundOldStat, ArrayList<StatDifference> statDifferences) {

        // the stats that only exist on the old item
        for (int i = 0; i < oldItem.statList.size(); i++) {
            if (foundOldStat[i] == false) {
                // System.out.println(oldItem.statList.get(i).type + " -" + oldItem.statList.get(i).value);
                StatDifference statDifference = new StatDifference(oldItem.statList.get(i));
                statDifference.setIsBetterStat(false);
                statDifference.setIsOldStatOnly(true);
                statDifference.setDifference(oldItem.statList.get(i).getValue());
                statDifferences.add(statDifference);
            }
        }
        
    }

    /**
     * compares a new item to this item and prints any differences
     * @param newItem
     */
    public static ArrayList<StatDifference> itemComparison(Item oldItem, Item newItem) {

        ArrayList<StatDifference> statDifferences = new ArrayList<StatDifference>();

        if (oldItem == null && newItem == null) {
            System.out.println("No items to compare");
            return null;
        }

        // case where oldItem is null - just fill the array showing how all the stats on the new item are better
        if (oldItem == null) {
            itemComparisonNewItemOnly(statDifferences, newItem);
            return statDifferences;
        }

        // this should really never happen - but if it does fill the array showing that all the stats on the old item are better
        if (newItem == null) {
            itemComparisonOldItemOnly(statDifferences, oldItem);
            return statDifferences;
        }

        // do a quick error check to see if these items are of the same type
        if (oldItem.getClass() != newItem.getClass()) {
            System.out.println("These items are not of the same type... comparison is pointless");
            return null; // TODO: throw error?
        }
        
        // show the two items for testing purposes
        // oldItem.displayItem();
        // newItem.displayItem();

        // this is a little complicated, we're going to have to run through all the stats from each item
        // and compare and see what will increase and what will decrease
        // automatically initialized false
        // these help track stats that are unique to each item
        boolean[] foundOldStat = new boolean[oldItem.statList.size()];
        boolean[] foundNewStat = new boolean[newItem.statList.size()];
    
        // System.out.println("Stat Differences: ");
        
        for (int i = 0; i < oldItem.statListSize(); i++) {
            ItemStat itemStat = oldItem.statList.get(i);
            
            for (int j = 0; j < newItem.statListSize(); j++) {
                ItemStat newStat = newItem.getStat(j);
                
                if (itemStat.getStatType().equals(newStat.getStatType())) {
                    foundOldStat[i] = true;
                    foundNewStat[j] = true;
                    // System.out.println("It has the same stat");
                    double difference = newStat.getValue() - itemStat.getValue();
                    // System.out.println(newStat.type + " " + difference);
                    StatDifference statDifference = new StatDifference(newStat);
                
                    if (newStat.getValue() >= itemStat.getValue()) {
                        statDifference.setIsBetterStat(true);
                    } else {
                        statDifference.setIsBetterStat(false);
                    }
                    statDifference.setDifference(difference);
                    statDifferences.add(statDifference);

                }

            }

        }

        // add stats only on new item and only on old item
        itemComparisonAddNewStats(newItem, foundNewStat, statDifferences);
        itemComparisonAddOldStats(oldItem, foundOldStat, statDifferences);
        
        return statDifferences;

    }


    /** sorts the stat list based on the order we defined in comparator */
    public void sortStats() {
        
        ArrayList<ItemStat> sortedStats = statList.stream()
            .sorted(Comparator.comparingInt(stat -> ItemStat.getStatPriority().getOrDefault(stat.getStatType(), Integer.MAX_VALUE)))
            .collect(Collectors.toCollection(ArrayList::new));

        this.statList = sortedStats;

        // displayItem();
    }


    /**
     * 
     * @param newItem
     * @return
     */
    public boolean statIsUnique(String statType) {

        for (int i = 0; i < this.statList.size(); i++ ) {
            
            if (this.statList.get(i).getStatType().equals(statType)) {
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
        } else if (isLegitItemType(this.getItemType()) == false) {
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

    public void generateItem(Random random) {

        // decide how many stats the item will have +1 since upper end of range is exclusive
        int numberOfStats = random.nextInt(Item.MIN_STATS, Item.MAX_STATS+1);

        // create a new UNIQUE stat - this loop will create new stats and also make sure we're not adding the same stat more than once
        while (this.statListSize() < numberOfStats) {
            
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
            if (this.statIsUnique(ItemStat.ITEM_STAT_TYPE[statType]) == true) {
                ItemStat stat = new ItemStat(ItemStat.ITEM_STAT_TYPE[statType], statValue);
                this.addStat(stat);
            }
        
        }

        // sort the stats on the new item - makes it easier to view and compare
        this.sortStats();

    }


}



/**
 * The StatComparator Class sorts the stats by their type
 * The order of the types correspons to their order on the ITEM_STAT_TYPE array
 */
// class StatComparator implements Comparator<Stat> {
//     @Override
//     public int compare(Stat statA, Stat statB) {
        
//         // return - integer if b comes first // return 0 if even // return + integer if a comes first
//         int aIndex = -1;
//         int bIndex = -1;

//         for (int i = 0; i < ItemStat.ITEM_STAT_TYPE.length; i++) {
            
//             if (statA.getStatType().equals(ItemStat.ITEM_STAT_TYPE[i])) {
//                 aIndex = i;
//             }

//             if (statB.getStatType().equals(ItemStat.ITEM_STAT_TYPE[i])) {
//                 bIndex = i; 
//             }

//         }

//         return aIndex - bIndex;
//     }

// }