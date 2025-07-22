import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.Point;

public class HoverPanel extends JPanel {

    //Character character;
    ItemManager itemManager;

    public HoverPanel() {

        // this.character = character;
        // this.itemManager = itemManager;
        // this.spareItemPane = spareItemPane;
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(0,0,0,180));
        this.setVisible(true);

    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
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

        if (this.itemManager == null) {
            return;
        }

        removeAllLabels();

        String panelType = hoverdLabel.getName();
        // System.out.println(panelType);

        Item item;
        EquipmentSlot slot;
        
        try {
            slot = EquipmentSlot.valueOf(panelType);
        } catch (EnumConstantNotPresentException e) {
            System.out.println("Not a proper slot to hover over - String doesn't convert back to enum member");
            return;
        }

        if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
            item = this.itemManager.getGeneratedItem();
        } else if (slot == EquipmentSlot.DELETE_ITEM_SLOT) {
            item = this.itemManager.getRemovedItem();
        } else {
            item = this.itemManager.getUniqueItem(slot);
        }

        // shouldn't happen but just incase the item is null we just won't bother generating the panel to avoid crashing
        if (item == null) {
            return;
        }

        JLabel itemName = new JLabel(item.getItemName());
        itemName.setForeground(Color.WHITE);
        this.add(itemName);

        // generate hover panel depending on the type - new vs equipped
        if (panelType.equals(EquipmentSlot.NEW_ITEM_SLOT.toString())) {
            System.out.println("New Item Hover Panel");
            generateNewItemHoverPanel(item);
        } else {
            generateEquippedItemHoverPanel(item);
        }
        

        // Get the absolute position of the label
        Point locationOnScreen = SwingUtilities.convertPoint(hoverdLabel, 0, 0, null);
        // System.out.println("Absolutie X: " + locationOnScreen.x);
        // System.out.println("Parent X: " + hoverdLabel.getParent().getX());
        this.setBounds(hoverdLabel.getParent().getX(), locationOnScreen.y, 200, 200);

        this.setSize(this.getPreferredSize());

    }

    /**
     * Generates the hover panel for an equipped item since the logic is different than that of a new item
     * @param item
     */
    private void generateEquippedItemHoverPanel(Item item) {
        
        for (int i = 0; i < item.statListSize(); i++) {
            JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
            label.setForeground(Color.WHITE);
            this.add(label);
        }
    
    }

    private void generateRingHoverPanel(Item item) {

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
    private void generateNewItemHoverPanel(Item item) {

        Item equippedItem;
        
        // logic for rings will be different because there possibly two equipped
        if (item.getItemType() == ItemType.RING) {

            // if we have two equipped rings we are just going to skip the comparason because I don't know which to comapare to
            if (this.itemManager.getRightRing() != null && this.itemManager.getLeftRing() != null) {
                generateRingHoverPanel(item);
                return;
            } else { // if there is one ring equipped or zero rings show all the stats as bonus
                equippedItem = null;
            }

        } else { // if it's not a ring, grab the item the corresponds to the correct equipemnt slot
            equippedItem = this.itemManager.getUniqueItem(item.getItemType().getEquipmentSlot());
        }


        ArrayList<StatDifference> statDifferences = Item.itemComparison(equippedItem, item);

        for (StatDifference statDiff : statDifferences)  {
            
            if (statDiff.isOldStatOnly() == false) {

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

                this.add(statLabel);
                this.add(diffLabel);
            }
            
            // stat only exists on the old item
            else {
                JLabel statLabel = new JLabel(statDiff.getStat().getStatType());
                JLabel diffLabel = new JLabel("(-" + statDiff.getStat().getValue() + ")");
                statLabel.setForeground(Color.RED);
                diffLabel.setForeground(Color.RED);
                this.add(statLabel);
                this.add(diffLabel);
            }
        }
    }

    /**
     * clears the panel of all of it's components
     */
    private void removeAllLabels() {
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                this.remove(component);
            }
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
