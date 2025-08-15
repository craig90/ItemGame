import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class InventoryPanel extends JPanel {

    public static final int GRID_SIZE = 50;

    private final ItemController itemController;

    private final int cellSize;
    private final int numRows;
    private final int numCols;

    public InventoryPanel(ItemController itemController, int cellSize, int numRows, int numCols) {
        this.itemController = itemController;
        this.cellSize = cellSize;
        this.numRows = numRows;
        this.numCols = numCols;
        setPreferredSize(new Dimension(numCols * cellSize + 1, numRows * cellSize + 1)); // +1 for the last line
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

        item.itemImageLabel.setBounds(x*cellSize, y*cellSize, 
                item.getWidthInGridUnits()*GRID_SIZE, item.getHeightInGridUnits()*GRID_SIZE);
        this.add(item.itemImageLabel);

    }

    public void removeItem(Item item) {
        this.remove(item.itemImageLabel);
    }

    public Item getItem(int x, int y) {
        return this.itemController.getItem(x,y);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // System.out.println("In paint component");
        InventorySlot[][] inventorySlots = this.itemController.getInventoryGrid();

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

        //             if (itemController.getInventory().isItemOrigin(inventorySlots[i][j].getItem(), j, i) ) {

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
