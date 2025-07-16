public class Pants extends Item {

    public Pants (String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.PANTS;
    }

    public String getImageFile() {
        return IMAGE_DIR + PANTS_FILE;
    }

}
