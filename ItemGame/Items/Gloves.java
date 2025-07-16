public class Gloves extends Item {

    public Gloves(String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.GLOVES;
    }

    public String getImageFile() {
        return IMAGE_DIR + GLOVES_FILE;
    }

}
