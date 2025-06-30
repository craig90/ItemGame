import javax.swing.ImageIcon;
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

    public static final int SPARE_WIDTH = 125, SPARE_HEIGHT = 175;

    JLabel itemLabel;
    JLabel itemIconLabel;
    ImageIcon itemImageIcon;

    Item item;
    String itemType;

    // about how this will work
    public ItemLayeredPane(String itemType, Item item) {

        this.itemType = itemType;
        this.item = item;

        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(getItemWidth(), getItemHeight()));
        this.setOpaque(true);
        
        this.itemLabel = new JLabel(this.itemType);
        this.itemLabel.setBounds(0, 0, getItemWidth(), getItemHeight());
        this.itemLabel.setHorizontalAlignment(JLabel.CENTER);
        this.itemLabel.setVerticalAlignment(JLabel.TOP);
        this.add(this.itemLabel, 1);
        
        if (this.item != null) {
            this.itemImageIcon = ImageResizer.resizeImageIcon(new ImageIcon(this.item.getImageFile()), getItemWidth(), getItemHeight());
            this.itemIconLabel = new JLabel(this.itemImageIcon);
            this.itemIconLabel.setBounds(0, 0, getItemWidth(), getItemHeight());
            this.add(this.itemIconLabel, 0);
        }

    }

    public void updateItem() {
        if (this.item != null) {
            this.itemImageIcon = ImageResizer.resizeImageIcon(new ImageIcon(this.item.getImageFile()), getItemWidth(), getItemHeight());
            this.itemIconLabel = new JLabel(this.itemImageIcon);
            this.itemIconLabel.setBounds(0, 0, getItemWidth(), getItemHeight());
            this.add(this.itemIconLabel, 0);
        }

        this.revalidate();
        this.repaint();
    }


    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * return the height in pixels of the layered pain depending on the item type
     * @return
     */
    private int getItemHeight() {

        if (this.itemType == Item.WEAPON) {
            return WEAPON_HEIGHT;
        } else if (this.itemType == Item.HELM) {
            return HELM_HEIGHT;
        } else if (this.itemType == Item.CHEST) {
            return CHEST_HEIGHT;
        } else if (this.itemType == Item.SHIELD) {
            return SHIELD_HEIGHT;
        } else if (this.itemType == Item.AMULET) {
            return AMULET_HEIGHT;
        } else if (this.itemType == Item.RING) {
            return RING_HEIGHT;
        } else if (this.itemType == Item.GLOVES) {
            return GLOVES_HEIGHT;
        } else if (this.itemType == Item.BELT) {
            return BELT_HEIGHT;
        } else if (this.itemType == Item.PANTS) {
            return PANTS_HEIGHT;
        } else if (this.itemType == Item.BOOTS ) {
            return BOOTS_HEIGHT;
        } else if (this.itemType == "New Item") {
            return SPARE_HEIGHT;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

    /**
     * returns the width of the layered pain in pixels cooresponding to the item type
     * @return
     */
    private int getItemWidth() {

        if (this.itemType == Item.WEAPON) {
            return WEAPON_WIDTH;
        } else if (this.itemType == Item.HELM) {
            return HELM_WIDTH;
        } else if (this.itemType == Item.CHEST) {
            return CHEST_WIDTH;
        } else if (this.itemType == Item.SHIELD) {
            return SHIELD_WIDTH;
        } else if (this.itemType == Item.AMULET) {
            return AMULET_WIDTH;
        } else if (this.itemType == Item.RING) {
            return RING_WIDTH;
        } else if (this.itemType == Item.GLOVES) {
            return GLOVES_WIDTH;
        } else if (this.itemType == Item.BELT) {
            return BELT_WIDTH;
        } else if (this.itemType == Item.PANTS) {
            return PANTS_WIDTH;
        } else if (this.itemType == Item.BOOTS ) {
            return BOOTS_WIDTH;
        } else if (this.itemType == "New Item") { // TODO: define this somehere
            return SPARE_WIDTH;
        } else {
            return 0;  // TODO: this shouln't be reached - throw exception?
        }

    }

}
