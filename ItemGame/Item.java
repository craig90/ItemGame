import java.util.ArrayList;

public class Item {

    public static final String[] ITEM_TYPES = {"Amulet", "Chest", "Ring", "Gloves", "Weapon", "Shield", "Belt", "Pants", "Boots"};
    public static final String[] ITEM_STAT_TYPE = {"Defense", "Damage", "Health", "Poison Resistance", "Lightning Resistance", "Fire Resistance", "Magic Resistance", "Cold Resistance"};

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

    }

}
