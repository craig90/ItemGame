package com.craig.itemgame.model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.craig.itemgame.model.Items.Item;
import com.craig.itemgame.model.Items.Shield;
import com.craig.itemgame.model.Items.Weapon;

public class Inventory {

    public static final int INVENTORY_ROWS = 5;
    public static final int INVENTORY_COLUMNS = 10;

    private int rows;
    private int columns;
    private InventorySlot[][] inventorySlots;

    private Map<String, Point> itemOrigins;

    public Inventory(int columns, int rows) {
        this.inventorySlots = new InventorySlot[rows][columns];
        this.columns = columns;
        this.rows = rows;
        this.itemOrigins = new HashMap<String,Point>();
        initGrid();
    }

    public Map<String, Point> getInventoryItems() {
        return this.itemOrigins;
    }

    public final void initGrid() {

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.inventorySlots[i][j] = new InventorySlot(i, j);
            }
        }

    }

    /**
     * Checks if the needed grid units are unoccupied and that the item will be placed within the grid bounds
     * @param item
     * @param slot
     * @return
     */
    public boolean canPlaceItem(Item item, InventorySlot slot) {

        // System.out.println("\nChecking if we can place the item");

        int finalX = slot.getX() + item.getWidthInGridUnits();
        int finalY = slot.getY() + item.getHeightInGridUnits();

        // System.out.println("Final X " + finalX + " final Y " + finalY);

        // make sure we're not stepping outside of the grid to the righ
        if (finalX > this.columns) {
            // System.out.println("Can't put item outside bounds " + finalX + " is beyond X axis size " + this.columns);
            return false;
        }

        // make sure we're not stepping below the grid
        if (finalY > this.rows) {
            // System.out.println("Can't put item outside bounds " + finalY + " is beyond Y axis size " + this.rows);
            return false;
        }

        // make sure that none of the necessary grid positions are occupied
        for (int i = slot.getY(); i < finalY; i++) {
            for (int j = slot.getX(); j < finalX; j++) {
                
                // System.out.println("I: " + i + " J: " + j + " " + inventorySlots[i][j].isOcuppied());

                if (inventorySlots[i][j].isOcuppied() == true) {

                    if (item.getID().equals(inventorySlots[i][j].getItemId()) == false) {
                        // System.out.println("Collision at " + j + " " + i);
                        return false;
                    } else {
                        // System.out.println("Same item, so collision is okay");
                    }
                }

            }
        }

        return true;

    }

    /**
     * Places an item in all of the slots it must occupy.  Does a quick check to make sure it can be placed first
     * @param item
     * @param slot
     * @return
     */
    public boolean placeItem(Item item, InventorySlot slot) {

        // do a quick check to make sure we can actually place the item rather than assuming the caller has already
        // done the check
        if (canPlaceItem(item, slot) == false) {
            return false;
        }

        int finalX = slot.getX() + item.getWidthInGridUnits();
        int finalY = slot.getY() + item.getHeightInGridUnits();

        // System.out.println("X: " + slot.getX() + " final X: " + finalX);
        // System.out.println("Y: " + slot.getY() + " final Y: " + finalY);

        // System.out.println("Inventory.java -> Placing item");

        for (int i = slot.getY(); i < finalY; i++) {
            for (int j = slot.getX(); j < finalX; j++) {
                // System.out.println("Inventory.java -> Placing item at " + i + " " + j);
                
                this.inventorySlots[i][j].setItem(item.getID());
                this.inventorySlots[i][j].setIsOccupied(true);
            }
        }

        this.itemOrigins.put(item.getID(), new Point(slot.getX(), slot.getY()));

        return true;
    }

    /**
     * Removes the item from the inventory.  This includes removing it from all of the slots it occupies.
     * @param item
     * @param slot
     */
    public void removeItem(Item item) {

        Point point = this.itemOrigins.get(item.getID());

        if (point == null) {
            return;
        }

        int finalX = (int)point.getX() + item.getWidthInGridUnits();
        int finalY = (int)point.getY() + item.getHeightInGridUnits();

        if (finalX > this.columns) {  // TODO: shouldn't happen maybe throw exception
            System.out.println("Error");
            return;
        }

        if (finalY > this.rows) {
            System.out.println("Error");
            return;
        }

        for (int i = (int)point.getY(); i < finalY; i++) {
            for (int j = (int)point.getX(); j < finalX; j++) {
                this.inventorySlots[i][j].setItem(null);
                this.inventorySlots[i][j].setIsOccupied(false);
            }
        }

        this.itemOrigins.remove(item.getID());
    }

    /**
     * this is just a method for testing, won't be used with GUI
     */
    public void printInventory() {

        System.out.println(this.columns);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (inventorySlots[i][j].isOcuppied() == false) {
                    System.out.print("[E]");
                } else {
                    System.out.print("[O]");
                }
            }
            System.out.println("");
        }

    }

    public boolean isItemOrigin(Item item, int x, int y) {

        Point point = this.itemOrigins.get(item.getID());

        // System.out.println("Origin X: " + point.getX() + " Origin Y: " + point.getY());
        // System.out.println("X: " + x + " Y: " + y);


        if ((int)point.getX() == x && (int)point.getY() == y) {
            return true;
        }

        return false;
    }

    public InventorySlot getGridSlot(int x, int y) {
        
        if (x < 0 || x >= INVENTORY_COLUMNS) {
            return null;
        }

        if (y < 0 || y >= INVENTORY_ROWS) {
            return null;
        }
        
        return this.inventorySlots[y][x];
    }

    public InventorySlot[][] getGrid() {
        return this.inventorySlots;
    }


    public static void main(String args[]) {

        Inventory inventory = new Inventory(Inventory.INVENTORY_COLUMNS, Inventory.INVENTORY_ROWS);
        
        Item shield = new Shield("Test Shield");
        Item weapon = new Weapon("Test Weapon");
        
        if (inventory.canPlaceItem(shield, inventory.inventorySlots[2][2])) {
            System.out.println("Can place the shield at [2][2]");
            inventory.placeItem(shield, inventory.inventorySlots[2][2]);
        }

        // should fail
        inventory.placeItem(shield, inventory.inventorySlots[3][3]);

        // should fail
        inventory.placeItem(weapon, inventory.inventorySlots[1][0]);

        inventory.printInventory();

        // inventory.removeItem(shield, new InventorySlot(2, 2));

        inventory.printInventory();

    }

    public boolean isInInventory(String itemId) {

        if (this.itemOrigins.containsKey(itemId)) {
            return true;
        }
        return false;

    }


}
