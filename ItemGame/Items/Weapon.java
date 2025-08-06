import java.util.Random;

public class Weapon extends Item {

    public Weapon(String itemName) {
        super(itemName);
    }

    public ItemType getItemType() {
        return ItemType.WEAPON;
    }

    public String getImageFile() {
        return IMAGE_DIR + WEAPON_FILE;
    }

    /**
     * Weapon is a special case where it must have a damage stat
     * @param random
     */
    public void generateItem(Random random) {
        
        // System.out.println("Weapon has to have damage");
        int statValue = random.nextInt(ItemStat.WEAPON_DAMAGE_MIN_VALUE, ItemStat.WEAPON_DAMGE_MAX_VALUE);
        ItemStat stat = new ItemStat(ItemStat.DAMAGE_STAT_NAME, statValue);
        this.addStat(stat);
        
        super.generateItem(random);
    }


}
