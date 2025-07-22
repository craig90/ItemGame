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

    JLabel slotLabel;
    JLabel itemIconLabel;
    // ImageIcon itemImageIcon;

    Item item;
    EquipmentSlot equipmentSlot;

    // about how this will work
    public ItemLayeredPane(EquipmentSlot equipmentSlot) {

        this.equipmentSlot = equipmentSlot;

        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(getItemSlotWidth(this.equipmentSlot), getItemSlotHeight(this.equipmentSlot)));
        this.setOpaque(true);
        
        this.slotLabel = new JLabel(this.equipmentSlot.toString());
        this.slotLabel.setBounds(0, 0, getItemSlotWidth(this.equipmentSlot), getItemSlotHeight(this.equipmentSlot));
        this.slotLabel.setHorizontalAlignment(JLabel.CENTER);
        this.slotLabel.setVerticalAlignment(JLabel.TOP);
        this.add(this.slotLabel, 1);
        
    }

    public void addItemToSlot(Item item) {

        this.item = item;
        
        if (this.itemIconLabel != null) {
            this.remove(this.itemIconLabel);
        }

        if (this.item != null) {
            // System.out.println("Adding item " + this.item.getItemName());
            this.itemIconLabel = this.item.getItemImageLabel();
            this.itemIconLabel.setName(this.equipmentSlot.toString());
            this.itemIconLabel.setBounds(0, 0, getItemWidth(this.item.getItemType()), getItemHeight(this.item.getItemType()));
            this.itemIconLabel.setVerticalAlignment(JLabel.CENTER);
            this.itemIconLabel.setHorizontalAlignment(JLabel.CENTER);
            this.add(this.itemIconLabel, 0);
            // System.out.println("Panel " + this.equipmentSlot);
        }

        this.revalidate();
        this.repaint();

    }

    public void removeItemFromSlot() {
        
        // remove the item from the slot
        this.remove(this.itemIconLabel);
        this.revalidate();
        this.repaint();

        System.out.println("Removing item " + this.itemIconLabel.getName() + " from " + this.slotLabel.getText() + " slot");
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
    public static int getItemSlotHeight(EquipmentSlot equipmentSlot) {

        if (equipmentSlot == EquipmentSlot.WEAPON) {
            return WEAPON_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.HELM) {
            return HELM_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.CHEST) {
            return CHEST_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.SHIELD) {
            return SHIELD_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.AMULET) {
            return AMULET_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.GLOVES) {
            return GLOVES_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.BELT) {
            return BELT_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.PANTS) {
            return PANTS_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.BOOTS) {
            return BOOTS_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.RIGHT_RING) {
            return RING_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.LEFT_RING) {
            return RING_HEIGHT;
        } else if (equipmentSlot == EquipmentSlot.NEW_ITEM_SLOT) {
            return NEW_SLOT_HEIGHT;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

    /**
     * returns the width of the layered pain in pixels cooresponding to the item type
     * @return
     */
    public static int getItemSlotWidth(EquipmentSlot equipmentSlot) {

        if (equipmentSlot == EquipmentSlot.LEFT_RING) {
            return RING_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.RIGHT_RING) {
            return RING_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.WEAPON) {
            return WEAPON_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.HELM) {
            return HELM_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.CHEST) {
            return CHEST_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.SHIELD) {
            return SHIELD_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.AMULET) {
            return AMULET_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.GLOVES) {
            return GLOVES_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.BELT) {
            return BELT_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.PANTS) {
            return PANTS_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.BOOTS) {
            return BOOTS_WIDTH;
        } else if (equipmentSlot == EquipmentSlot.NEW_ITEM_SLOT) {
            return NEW_SLOT_WIDTH;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

    /**
     * return the height in pixels of the image depending on the item type
     * @return
     */
    public static int getItemHeight(ItemType itemType) {

        if (itemType == ItemType.WEAPON) {
            return WEAPON_HEIGHT;
        } else if (itemType == ItemType.HELM) {
            return HELM_HEIGHT;
        } else if (itemType == ItemType.CHEST) {
            return CHEST_HEIGHT;
        } else if (itemType == ItemType.SHIELD) {
            return SHIELD_HEIGHT;
        } else if (itemType == ItemType.AMULET) {
            return AMULET_HEIGHT;
        } else if (itemType == ItemType.GLOVES) {
            return GLOVES_HEIGHT;
        } else if (itemType == ItemType.BELT) {
            return BELT_HEIGHT;
        } else if (itemType == ItemType.PANTS) {
            return PANTS_HEIGHT;
        } else if (itemType == ItemType.BOOTS) {
            return BOOTS_HEIGHT;
        } else if (itemType == ItemType.RING) {
            return RING_HEIGHT;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

    /**
     * returns the width of the layered pain in pixels cooresponding to the item type
     * @return
     */
    public static int getItemWidth(ItemType itemType) {

        if (itemType == ItemType.RING) {
            return RING_WIDTH;
        } else if (itemType == ItemType.WEAPON) {
            return WEAPON_WIDTH;
        } else if (itemType == ItemType.HELM) {
            return HELM_WIDTH;
        } else if (itemType == ItemType.CHEST) {
            return CHEST_WIDTH;
        } else if (itemType == ItemType.SHIELD) {
            return SHIELD_WIDTH;
        } else if (itemType == ItemType.AMULET) {
            return AMULET_WIDTH;
        } else if (itemType == ItemType.GLOVES) {
            return GLOVES_WIDTH;
        } else if (itemType == ItemType.BELT) {
            return BELT_WIDTH;
        } else if (itemType == ItemType.PANTS) {
            return PANTS_WIDTH;
        } else if (itemType == ItemType.BOOTS) {
            return BOOTS_WIDTH;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }
    }

}
