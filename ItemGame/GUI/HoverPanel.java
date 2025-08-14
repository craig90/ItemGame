import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.Point;

public class HoverPanel extends JPanel {

    //Character character;
    ItemController itemController;

    public HoverPanel(ItemController itemController) {

        this.itemController = itemController;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(0,0,0,180));
        EmptyBorder paddingBorder = new EmptyBorder(3, 3, 3, 3);
        this.setBorder(paddingBorder);
        this.setVisible(true);

    }


    // public void setSpareItemPanel(ItemLayeredPane spareItemPane) {
    //     this.spareItemPane = spareItemPane;
    // }


    /**
     * updates the hover panel by populating it with the item name and stats and setting it's position to
     * the item that is currently being hovered over
     * @param hoverdLabel
     */
    public void updateHoverWindow(JLabel hoverdLabel) {

        // System.out.println("Updating hover window");

        removeAllLabels();

        String slotType = hoverdLabel.getName();
        
        // System.out.println("Parent: " + hoverdLabel.getParent().getName());
        // System.out.println("Bounds: " + hoverdLabel.getBounds());
        // System.out.println("Location: " + hoverdLabel.getLocation());

        // System.out.println("Slot type: " + slotType);

        Item item = null;
        
        // if it's on the Equipped Item Panel
        if (hoverdLabel.getParent().getName() == Gui.ITEM_PANEL_NAME) {
            EquipmentSlot slot;
            
            try {
                slot = EquipmentSlot.valueOf(slotType);
            } catch (EnumConstantNotPresentException e) {
                System.out.println("Not a proper slot to hover over - String doesn't convert back to enum member");
                return;
            }
            
            // System.out.println("Slot: " + slot.toString());

            item = this.itemController.getItem(slot);

            if (item == null) {
                return;
            }

            generateEquippedItemHoverPanel(item);
        } 
        
        // if it's an inventory item
        else if (hoverdLabel.getParent().getName() == Gui.INVENTORY_PANEL_NAME) {

            int x = hoverdLabel.getX();
            int y = hoverdLabel.getY();

            try {
                item = this.itemController.getItem(x/Inventory.GRID_SIZE, y/Inventory.GRID_SIZE);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return; // I think returning is appropriate here - just don't display hover panel if error getting item
            }

            if (item == null) {
                return;
            }

            generateUnequippedItemHoverPanel(item);

        }

        else {
            return; // this should never happen, but if it does, just don't draw the hover panel
        }
        // if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
        //     item = this.itemManager.getGeneratedItem();
        // } else if (ItemManager.isInventory(slot)) {
        //     try {
        //         item = this.itemManager.getInventoryItem(slot);
        //     } catch (Exception e) {
        //         System.out.println(e.getMessage());
        //     }
        // } else {
        //     item = this.itemManager.getUniqueItem(slot);
        // }

        // shouldn't happen but just incase the item is null we just won't bother generating the panel to avoid crashing
        
        // TODO: this will be put back in
        // generate hover panel depending on the type - new vs equipped
        // if (slot == EquipmentSlot.NEW_ITEM_SLOT || ItemManager.isInventorySlot(slot) == true) {
        //     generateUnequippedItemHoverPanel(item);
        // } else {
            
        // }

        this.setSize(this.getPreferredSize());

        calculateLocation(hoverdLabel);
        
    }

    /** 
     * calculates and sets the location of the hover panel
     */
    public void calculateLocation(JLabel hoverdLabel) {

        // Get the absolute position of the label
        Point locationOnScreen = SwingUtilities.convertPoint(hoverdLabel, 0, 0, null);
        
        // System.out.println("Label width: " + hoverdLabel.getWidth());
        // System.out.println("Hover window width: " + this.getWidth());
        // System.out.println("Label's parent x pos: " + hoverdLabel.getParent().getX());

        // offset the hover panel a bit depending on it's size relative to the panel it is drawing over top of
        int xOffset = (this.getWidth() - hoverdLabel.getWidth()) / 2;

        if (hoverdLabel.getParent().getName().equals(Gui.ITEM_PANEL_NAME)) {
            this.setLocation(hoverdLabel.getParent().getX() - xOffset, locationOnScreen.y);
        } 
        
        else if (hoverdLabel.getParent().getName().equals(Gui.INVENTORY_PANEL_NAME)) {
            this.setLocation(hoverdLabel.getX()- xOffset, locationOnScreen.y);
        }
        
        else {
            return; // this shouldn't happen but if it does.. just return without updating location
        }

    }


    private void addName(Item item) {

        JLabel itemName = new JLabel(item.getItemName());
        itemName.setForeground(Color.WHITE);
        this.add(itemName);

    }

    /**
     * Generates the hover panel for an equipped item since the logic is different than that of a new item
     * @param item
     */
    private void generateEquippedItemHoverPanel(Item item) {

        addName(item);

        for (int i = 0; i < item.statListSize(); i++) {
            JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
            label.setForeground(Color.WHITE);
            this.add(label);
        }
    
    }

    private void generateRingHoverPanel(Item item) {

        addName(item);

        System.out.println("Item type should be ring: " + item.getItemType());

        for (int i = 0; i < item.statListSize(); i++) {
            JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
            label.setForeground(Color.WHITE);
            this.add(label);
        }

    }
    
        
    /**
     * Generates the hover panel for a new item as the logic is different than for an existing item
     * @param item
     */
    private void generateUnequippedItemHoverPanel(Item item) {
        
        addName(item);

        Item equippedItem;
        
        // logic for rings will be different because there possibly two equipped
        if (item.getItemType() == ItemType.RING) {

            // if we have two equipped rings we are just going to skip the comparason because I don't know which to comapare to
            if (this.itemController.getItem(EquipmentSlot.RIGHT_RING) != null 
                && this.itemController.getItem(EquipmentSlot.LEFT_RING) != null) {
                generateRingHoverPanel(item);
                return;
            } else { // if there is one ring equipped or zero rings show all the stats as bonus
                equippedItem = null;
            }

        } else { // if it's not a ring, grab the item the corresponds to the correct equipemnt slot
            equippedItem = this.itemController.getItem(item.getItemType().getEquipmentSlot());
        }

        ArrayList<StatDifference> statDifferences = Item.itemComparison(equippedItem, item);

        for (StatDifference statDiff : statDifferences)  {
            
            if (statDiff.isOldStatOnly() == false) {

                JPanel statPanel = new JPanel();
                statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.LINE_AXIS));
                statPanel.setBackground(new Color(0,0,0,180));

                JLabel statLabel = new JLabel(statDiff.getStat().getStatType() + " " +
                statDiff.getStat().getValue());
                JLabel diffLabel;
                
                if (statDiff.isBetter() == true) {
                    diffLabel = new JLabel(" (+" + statDiff.getDifference() + ")");
                    diffLabel.setForeground(Color.GREEN);
                } else {
                    diffLabel = new JLabel(" (" + statDiff.getDifference() + ")");
                    diffLabel.setForeground(Color.RED);
                }
                
                statLabel.setForeground(Color.WHITE);

                statPanel.add(statLabel);
                statPanel.add(diffLabel);

                statPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                this.add(statPanel);

                // JLabel statLabel = new JLabel(statDiff.getStat().getStatType() + " " + statDiff.getStat().getValue());
                // if (statDiff.isBetter() == true) {
                //     statLabel.setForeground(Color.GREEN);
                // } else {
                //     statLabel.setForeground(Color.RED);
                // }

                // this.add(statLabel);
            }
            
            // stat only exists on the old item
            else {
                
                JPanel statPanel = new JPanel();
                statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.LINE_AXIS));
                statPanel.setBackground(new Color(0,0,0,180));

                JLabel statLabel = new JLabel(statDiff.getStat().getStatType());
                JLabel diffLabel = new JLabel(" (-" + statDiff.getStat().getValue() + ")");
                statLabel.setForeground(Color.RED);
                diffLabel.setForeground(Color.RED);
                statPanel.add(statLabel);
                statPanel.add(diffLabel);

                statPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                this.add(statPanel);

                // JLabel statLabel = new JLabel(statDiff.getStat().getStatType()
                // + " (-" + statDiff.getStat().getValue() + ")");
                // statLabel.setForeground(Color.RED);
                // this.add(statLabel);

            }
        }
    }

    /**
     * clears the panel of all of it's components
     */
    private void removeAllLabels() {
        Component[] components = this.getComponents();
        for (Component component : components) {
            // if (component instanceof JLabel) {
                this.remove(component);
            // }
        }
        this.setSize(this.getPreferredSize());
        this.revalidate();
        this.repaint();
    }

    /**
     * Makes the hover panel visible
     */
    public void showHoverPanel() {
        this.setVisible(true);
        this.revalidate(); // Revalidate the layout to ensure it's displayed correctly
        this.repaint();
    }

    /**
     * Hides the hover panel
     */
    public void hideHoverPanel() {
        this.setVisible(false);
        this.revalidate();
        this.repaint();
    }

}
