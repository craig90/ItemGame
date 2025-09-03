package com.craig.itemgame.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.craig.itemgame.controller.CharacterController;
import com.craig.itemgame.model.EquipmentSlot;
import com.craig.itemgame.model.Inventory;
import com.craig.itemgame.model.ItemType;
import com.craig.itemgame.model.StatDifference;
import com.craig.itemgame.model.Items.Item;

public class HoverPanel extends JPanel {

    // the approximate number of pixels from the top of an image label to where Swing reports it's Y location
    // used for calculating where to draw the hover window when the item is at the bottom of the screen
    private static final int BASE_Y_OFFSET = 32;
    //Character character;
    CharacterController characterController;

    public HoverPanel(CharacterController characterController) {

        this.characterController = characterController;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setOpaque(false);
        EmptyBorder paddingBorder = new EmptyBorder(3, 3, 3, 3);
        this.setBorder(paddingBorder);
        this.setVisible(true);

    }

    /**
     * Have to write the paintComponent method because it won't set the transparency correct otherwise
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 180)); // semi-transparent black
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
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

            item = this.characterController.getItem(slot);

            if (item == null) {
                return;
            }

            if (slot == EquipmentSlot.NEW_ITEM_SLOT || slot == EquipmentSlot.DELETE_ITEM_SLOT) {
                generateUnequippedItemHoverPanel(item);
            } else {
                generateEquippedItemHoverPanel(item);
            }
        
        } 
        
        // if it's an inventory item
        else if (hoverdLabel.getParent().getName() == Gui.INVENTORY_PANEL_NAME) {

            int x = hoverdLabel.getX();
            int y = hoverdLabel.getY();

            try {
                item = this.characterController.getItem(x/InventoryPanel.GRID_SIZE, y/InventoryPanel.GRID_SIZE);
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
            if (hoverdLabel.getName().equals(EquipmentSlot.NEW_ITEM_SLOT.toString()) ||
                hoverdLabel.getName().equals(EquipmentSlot.DELETE_ITEM_SLOT.toString())) {
                // since it's to the right of the inventory, grab the inventory size and then how much the layered panel
                // is to the right of the grid
                int x = ((InventoryPanel.GRID_SIZE*Inventory.INVENTORY_COLUMNS)+hoverdLabel.getParent().getX())-xOffset;
                this.setLocation(x, locationOnScreen.y);
            } else {
                this.setLocation(hoverdLabel.getParent().getX() - xOffset, locationOnScreen.y);
            }
        } 
        
        else if (hoverdLabel.getParent().getName().equals(Gui.INVENTORY_PANEL_NAME)) {
            
            // this is to prevent an Item in column 1 or 2 have a hovered panel that is not fully visible on the left
            // if the xOffset is > than the X location it will draw it partially out of bounds and therefore won't 
            // be visible
            if (xOffset > hoverdLabel.getX()) {
                xOffset = hoverdLabel.getX();
            }

            // the lowest Y position the hover panel will occupy
            int yOffset = 0;
            int finalY = hoverdLabel.getY() + this.getSize().height + BASE_Y_OFFSET;

            // check if the finalY is lower than the parent, if so we need to offset the hover window so the bottom isn't
            // cut off
            if (finalY > hoverdLabel.getParent().getSize().height) {
                yOffset = finalY - hoverdLabel.getParent().getSize().height;
            }

            this.setLocation(hoverdLabel.getX()- xOffset, locationOnScreen.y - yOffset);
            
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
            if (this.characterController.getItem(EquipmentSlot.RIGHT_RING) != null 
                && this.characterController.getItem(EquipmentSlot.LEFT_RING) != null) {
                generateRingHoverPanel(item);
                return;
            } else { // if there is one ring equipped or zero rings show all the stats as bonus
                equippedItem = null;
            }

        } else { // if it's not a ring, grab the item the corresponds to the correct equipemnt slot
            equippedItem = this.characterController.getItem(item.getItemType().getEquipmentSlot());
        }

        ArrayList<StatDifference> statDifferences = Item.itemComparison(equippedItem, item);

        for (StatDifference statDiff : statDifferences)  {
        
            if (statDiff.isOldStatOnly() == false) {

                JPanel statPanel = new JPanel();
                statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.LINE_AXIS));
                
                // this panel has to be set to be fully transparent 
                statPanel.setBackground(new Color(0,0,0,0));

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
