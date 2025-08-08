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

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.GLOVES_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.GLOVES_GRID_UNIT_HEIGHT;
    }

}
