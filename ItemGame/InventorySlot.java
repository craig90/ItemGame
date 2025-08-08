public class InventorySlot {

    private int x;
    private int y;
    private Item item;
    private boolean isOccupied;

    /**
     * X and Y should only be set once when the Inventory Slot is initialized as far as I know
     * Therefore they will not have setter methods
     * @param x
     * @param y
     */
    public InventorySlot(int x, int y) {
        this.x = x;
        this.y = y;
        this.item = null;
        this.isOccupied = false;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean isOcuppied () {
        return this.isOccupied;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
