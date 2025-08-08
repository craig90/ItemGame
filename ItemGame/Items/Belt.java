public class Belt extends Item {

    public Belt (String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.BELT;
    }

    public String getImageFile() {
        return IMAGE_DIR + BELT_FILE;
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.BELT_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.BELT_GRID_UNIT_HEIGHT;
    }
}
