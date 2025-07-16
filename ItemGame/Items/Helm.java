public class Helm extends Item {

    public Helm(String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.HELM;
    }

    public String getImageFile() {
        return IMAGE_DIR + HELM_FILE;
    }

}
