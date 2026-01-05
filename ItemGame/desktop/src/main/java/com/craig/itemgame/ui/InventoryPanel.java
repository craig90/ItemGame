package com.craig.itemgame.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.craig.itemgame.controller.CharacterController;
import com.craig.itemgame.model.InventorySlot;
import com.craig.itemgame.model.Items.Item;


public final class InventoryPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    public static final int GRID_SIZE = 50;

    private final CharacterController characterController;

    private final int cellSize;
    private final int numRows;
    private final int numCols;

    private boolean pendingRepaint = false;

    private Map<String, ItemLabel> itemIdToItemLabel;

    public InventoryPanel(CharacterController characterController,
        Map<String, ItemLabel> itemIdToItemLabel, int cellSize, int numRows, int numCols) {
        this.characterController = characterController;
        this.itemIdToItemLabel = itemIdToItemLabel;
        this.cellSize = cellSize;
        this.numRows = numRows;
        this.numCols = numCols;
        this.setPreferredSize(new Dimension(numCols * cellSize + 1, numRows * cellSize + 1)); // +1 for the last line
        this.setLayout(null);
        this.setName(Gui.INVENTORY_PANEL_NAME);
    }

    /**
     * Adds the specified item at the specified grid location
     * @param item
     * @param x
     * @param y
     */
    public void addItem(Item item, int x, int y) {

        this.itemIdToItemLabel.get(item.getID()).setBounds(x*cellSize, y*cellSize, 
                item.getWidthInGridUnits()*GRID_SIZE, item.getHeightInGridUnits()*GRID_SIZE);
        this.add(this.itemIdToItemLabel.get(item.getID()));

        pendingRepaint();

    }

    /**
     * used to stop multiple repaints called in succession
     */
    public void pendingRepaint() {
        
        if (!this.pendingRepaint) {
            this.pendingRepaint = true;
            SwingUtilities.invokeLater(() -> {
                this.revalidate();
                this.repaint();
                this.pendingRepaint = false;
            });
        }
    }

    public void addItems() {
         Map<Item, Point> inventoryMap = this.characterController.getInventoryItems();

         for (Map.Entry<Item, Point> entry : inventoryMap.entrySet()) {
            addItem(entry.getKey(), entry.getValue().x, entry.getValue().y);
         }

    }

    public void removeItem(Item item) {
        this.remove(itemIdToItemLabel.get(item.getID()));
    }

    public Item getItem(int x, int y) {
        
        Item item = null;
        try {
            item = this.characterController.getItem(x,y);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return item;
    }

    /**
     * clears all Items from the Inventory Grid
     */
    public void removeAllItems() {

        Component[] children = this.getComponents();

        for (Component child : children) {
            if (child instanceof JLabel) {
                remove(child);
            }
        }

        revalidate();
        repaint();

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // System.out.println("In paint component");
        InventorySlot[][] inventorySlots = this.characterController.getInventoryGrid();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {

                if (inventorySlots[i][j].isOcuppied()) {
                    Color bluishGrey = new Color(80, 90, 110);
                    g2d.setColor(bluishGrey);
                    g2d.fillRect(j*GRID_SIZE, i*GRID_SIZE, GRID_SIZE, GRID_SIZE);
                }
            }
        }

        g2d.setColor(Color.BLACK);

        // Draw horizontal lines
        for (int i = 0; i <= numRows; i++) {
            g2d.drawLine(0, i * cellSize, numCols * cellSize, i * cellSize);
        }

        // Draw vertical lines
        for (int i = 0; i <= numCols; i++) {
            g2d.drawLine(i * cellSize, 0, i * cellSize, numRows * cellSize);
        }

        

        //             // System.out.println("GUI -> occupied at " + i + " " + j );

        //             if (characterController.getInventory().isItemOrigin(inventorySlots[i][j].getItem(), j, i) ) {

        //                 System.out.println("Placing item");
        //                 System.out.println("x position: " + j*cellSize);
        //                 System.out.println("Y position: " + i*cellSize);
                        
        //                 JLabel imageLabel = inventorySlots[i][j].getItem().itemImageLabel;
                        
        //                 if (imageLabel.getParent() != null) {
        //                     System.out.println("Parent component: " + imageLabel.getParent());
        //                 } else {
        //                     System.out.println("Parent is null... as it should be");
        //                 }

        //                 imageLabel.setBounds(j*cellSize, i*cellSize, 125, 175);
        //                 this.add(imageLabel);
        //                 System.out.println("Bounds: " + inventorySlots[i][j].getItem().itemImageLabel.getBounds());

        //             }

        //         }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // JFrame frame = new JFrame("Grid Example");
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // InventoryPanel gridPanel = new InventoryPanel(30, 10, 15); // Cell size 30, 10 rows, 15 columns
            // frame.add(gridPanel);

            // frame.pack();
            // frame.setLocationRelativeTo(null); // Center the frame
            // frame.setVisible(true);
        });
    }
}
