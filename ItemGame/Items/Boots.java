public class Boots extends Item {

    public Boots (String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.BOOTS;
    }

    public String getImageFile() {
        return Item.IMAGE_DIR + Item.BOOTS_FILE;
    }

}
