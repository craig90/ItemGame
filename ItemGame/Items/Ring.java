public class Ring extends Item {

    RingSlot slot;

    public Ring(String itemName) {
        super(itemName);
    }

    public void setRingSlot(RingSlot slot) {
        this.slot = slot;
    }

    public RingSlot getRingSlot() {
        return this.slot;
    }

    public String getImageFile() {
        return IMAGE_DIR + RING_FILE;
    }

    public ItemType getItemType() {
        return ItemType.RING;
    }
}
