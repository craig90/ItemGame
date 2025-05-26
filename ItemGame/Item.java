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

    public ArrayList<Stat> getStatList() {
        return this.statList;
    }

}
