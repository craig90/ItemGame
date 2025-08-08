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

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.HELM_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.HELM_GRID_UNIT_HEIGHT;
    }

}
