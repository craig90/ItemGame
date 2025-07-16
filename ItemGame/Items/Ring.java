public class Ring extends Item {

    RingSlot slot;

    public Ring(String itemName) {
        super(itemName);
    }

    // TODO: come back to this
    /*public void setRingSlot(ItemManager.RingSlot slot) {
        this.slot = slot;
    }

    public ItemManager.RingSlot getRingSlot() {
        return this.slot;
    }*/

    public String getImageFile() {
        return IMAGE_DIR + RING_FILE;
    }

    public ItemType getItemType() {
        return ItemType.RING;
    }
}
