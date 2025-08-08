import java.util.Random;

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

    /**
     * Chest is a special case where it has to have defense
     */
    public void generateItem(Random random) {
        
        // System.out.println("Chest has to have defence");
        int statValue = random.nextInt(ItemStat.CHEST_DEFENCE_MIN_VALUE, ItemStat.CHEST_DEFENCE_MAX_VALUE);
        ItemStat stat = new ItemStat(ItemStat.DEFENCE_STAT_NAME, statValue);
        this.addStat(stat);
        
        super.generateItem(random);
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.CHEST_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.CHEST_GRID_UNIT_HEIGHT;
    }

}
