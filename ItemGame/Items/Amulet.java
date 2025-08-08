public class Amulet extends Item {

    public Amulet (String itemName) {
        super(itemName);
    }

    public String getImageFile() {
        return IMAGE_DIR + AMULET_FILE;
    }

    public ItemType getItemType() {
        return ItemType.AMULET;
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.AMULET_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.AMULET_GRID_UNIT_HEIGHT;
    }

}
