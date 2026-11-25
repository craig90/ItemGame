package com.craig.itemgame.model;

public class InventorySlot {

    private int x;
    private int y;
    private String itemId;
    private boolean isOccupied;

    /**
     * X and Y should only be set once when the Inventory Slot is initialized as far as I know
     * Therefore they will not have setter methods
     * @param x
     * @param y
     */
    public InventorySlot(int y, int x) {
        this.x = x; // column
        this.y = y; // row
        this.itemId = null;
        this.isOccupied = false;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean isOcuppied () {
        return this.isOccupied;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItem(String itemId) {
        this.itemId = itemId;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
