public class Shield extends Item {

    public Shield (String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.SHIELD;
    }

    public String getImageFile() {
        return Item.IMAGE_DIR + Item.SHIELD_FILE;
    }

}
