import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.Color;
import java.awt.Dimension;

public class ItemLayeredPane extends JLayeredPane {

    public static final int WEAPON_WIDTH = 125, HELM_WIDTH = 125, CHEST_WIDTH = 125, SHIELD_WIDTH = 125;
    public static final int AMULET_WIDTH = 55, RING_WIDTH = 55, GLOVES_WIDTH = 125, BELT_WIDTH = 125;
    public static final int PANTS_WIDTH = 125, BOOTS_WIDTH = 125;

    public static final int WEAPON_HEIGHT = 175, HELM_HEIGHT = 125, CHEST_HEIGHT = 175, SHIELD_HEIGHT = 175;
    public static final int AMULET_HEIGHT = 55, RING_HEIGHT = 55, GLOVES_HEIGHT = 125, BELT_HEIGHT = 55;
    public static final int PANTS_HEIGHT = 175, BOOTS_HEIGHT = 125;

    public static final int NEW_SLOT_WIDTH = 125, NEW_SLOT_HEIGHT = 175;

    public static final String NEW_ITEM_SLOT = "New Item";

    JLabel itemLabel;
    JLabel itemIconLabel;
    // ImageIcon itemImageIcon;

    Item item;
    String itemSlotType;

    // about how this will work
    public ItemLayeredPane(String itemSlotType, Item item) {

        this.itemSlotType = itemSlotType;
        this.item = item;

        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(getItemWidth(itemSlotType), getItemHeight(itemSlotType)));
        this.setOpaque(true);
        
        this.itemLabel = new JLabel(this.itemSlotType);
        this.itemLabel.setBounds(0, 0, getItemWidth(itemSlotType), getItemHeight(itemSlotType));
        this.itemLabel.setHorizontalAlignment(JLabel.CENTER);
        this.itemLabel.setVerticalAlignment(JLabel.TOP);
        this.add(this.itemLabel, 1);
        
    }

    public void addItemToSlot(Item item) {

        this.item = item;
        
        if (this.itemIconLabel != null) {
            this.remove(this.itemIconLabel);
        }

        if (this.item != null) {
            System.out.println("Adding item " + this.item.getItemName());
            this.itemIconLabel = this.item.getItemImageLabel();
            this.itemIconLabel.setName(this.itemSlotType);
            this.itemIconLabel.setVerticalAlignment(JLabel.CENTER);
            this.itemIconLabel.setHorizontalAlignment(JLabel.CENTER);
            this.itemIconLabel.setBounds(0, 0, getItemWidth(this.itemSlotType), getItemHeight(this.itemSlotType));
            this.add(this.itemIconLabel, 0);
            System.out.println("Panel " + this.itemSlotType);
        }

        this.revalidate();
        this.repaint();

    }

    public void removeItemFromSlot() {
        
        // remove the item from the slot
        this.remove(this.itemIconLabel);
        this.revalidate();
        this.repaint();

        System.out.println("Removing item" + this.itemIconLabel.getName());
        // set things to null
        this.itemIconLabel = null;
        this.item = null;
    }

    /**
     * if there is no item in the slot return true
     * @return
     */
    public boolean isEmpty() {
        if (this.item == null) {
            return true;
        }
        return false;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * return the height in pixels of the layered pain depending on the item type
     * @return
     */
    public static int getItemHeight(String itemType) {

        if (itemType.equals(Item.WEAPON)) {
            return WEAPON_HEIGHT;
        } else if (itemType.equals(Item.HELM)) {
            return HELM_HEIGHT;
        } else if (itemType.equals(Item.CHEST)) {
            return CHEST_HEIGHT;
        } else if (itemType.equals(Item.SHIELD)) {
            return SHIELD_HEIGHT;
        } else if (itemType.equals(Item.AMULET)) {
            return AMULET_HEIGHT;
        } else if (itemType.equals(Item.RING)) {
            return RING_HEIGHT;
        } else if (itemType.equals(Item.GLOVES)) {
            return GLOVES_HEIGHT;
        } else if (itemType.equals(Item.BELT)) {
            return BELT_HEIGHT;
        } else if (itemType.equals(Item.PANTS)) {
            return PANTS_HEIGHT;
        } else if (itemType.equals(Item.BOOTS)) {
            return BOOTS_HEIGHT;
        } else if (itemType.equals(NEW_ITEM_SLOT)) {
            return NEW_SLOT_HEIGHT;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

    /**
     * returns the width of the layered pain in pixels cooresponding to the item type
     * @return
     */
    public static int getItemWidth(String itemType) {

        if (itemType.equals(Item.WEAPON)) {
            return WEAPON_WIDTH;
        } else if (itemType.equals(Item.HELM)) {
            return HELM_WIDTH;
        } else if (itemType.equals(Item.CHEST)) {
            return CHEST_WIDTH;
        } else if (itemType.equals(Item.SHIELD)) {
            return SHIELD_WIDTH;
        } else if (itemType.equals(Item.AMULET)) {
            return AMULET_WIDTH;
        } else if (itemType.equals(Item.RING)) {
            return RING_WIDTH;
        } else if (itemType.equals(Item.GLOVES)) {
            return GLOVES_WIDTH;
        } else if (itemType.equals(Item.BELT)) {
            return BELT_WIDTH;
        } else if (itemType.equals(Item.PANTS)) {
            return PANTS_WIDTH;
        } else if (itemType.equals(Item.BOOTS)) {
            return BOOTS_WIDTH;
        } else if (itemType.equals(NEW_ITEM_SLOT)) {
            return NEW_SLOT_WIDTH;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

}
