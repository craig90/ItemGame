import java.util.ArrayList;

public class Item {

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

}
