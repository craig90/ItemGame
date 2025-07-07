import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.util.ArrayList;

import java.awt.Component;
import java.awt.Point;

public class HoverPanel extends JPanel {

    // TODO: this class really shouldn't have access to the entire character, once agian going back to item manager
    // class or sending in an item list
    Character character;
    // this should not be sent in either -- need item manager TODO
    ItemLayeredPane spareItemPane;

    public HoverPanel(Character character, ItemLayeredPane spareItemPane) {

        this.character = character;
        this.spareItemPane = spareItemPane;
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(new Color(0,0,0,180));
        this.setVisible(true);

    }

    /**
     * updates the hover panel by populating it with the item name and stats and setting it's position to
     * the item that is currently being hovered over
     * @param hoverdLabel
     */
    public void updateHoverWindow(JLabel hoverdLabel) {

        removeAllLabels();

        String panelType = hoverdLabel.getName();
        
        Item item;
        if (panelType.equals(ItemLayeredPane.NEW_ITEM_SLOT)) {
            item = this.spareItemPane.getItem();
        } else {
            item = this.character.getItem(panelType);
        }

        // shouldn't happen but just incase the item is null we just won't bother generating the panel to avoid crashing
        if (item == null) {
            return;
        }

        JLabel itemName = new JLabel(item.getItemName());
        itemName.setForeground(Color.WHITE);
        this.add(itemName);

        // generate hover panel depending on the type - new vs equipped
        if (panelType.equals(ItemLayeredPane.NEW_ITEM_SLOT)) {
            generateNewItemHoverPanel(item);
        } else {
            generateEquippedItemHoverPanel(item);
        }
        

        // Get the absolute position of the label
        Point locationOnScreen = SwingUtilities.convertPoint(hoverdLabel, 0, 0, null);
        this.setBounds(locationOnScreen.x, locationOnScreen.y, 200, 200);
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
    
    /**
     * Generates the hover panel for a new item as the logic is different than for an existing item
     * @param item
     */
    private void generateNewItemHoverPanel(Item item) {

        Item equippedItem = this.character.getItem(item.getItemType());
        ArrayList<StatDifference> statDifferences = Item.itemComparison(equippedItem, item);

        for (int i = 0; i < statDifferences.size(); i ++) {
            
            StatDifference statDiff = statDifferences.get(i);

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


}
