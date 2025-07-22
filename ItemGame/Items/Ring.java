public class Ring extends Item {

    EquipmentSlot slot;

    public Ring(String itemName) {
        super(itemName);
    }

    public void setRingSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

    public EquipmentSlot getRingSlot() {
        return this.slot;
    }

    public String getImageFile() {
        return IMAGE_DIR + RING_FILE;
    }

    public ItemType getItemType() {
        return ItemType.RING;
    }
}
