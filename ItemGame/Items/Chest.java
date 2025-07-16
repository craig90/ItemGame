public class Chest extends Item {

    public Chest(String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.CHEST;
    }

    public String getImageFile() {
        return IMAGE_DIR + CHEST_FILE;
    }

}
