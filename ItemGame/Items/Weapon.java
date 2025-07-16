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

}
