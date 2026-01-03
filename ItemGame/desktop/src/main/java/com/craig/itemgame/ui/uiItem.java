package com.craig.itemgame.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.craig.itemgame.model.Items.Item;

public class uiItem {

    private final Item item;
    private final JLabel itemImageLabel;
    private final ImageIcon itemImageIcon;
    
    private static final String IMAGE_DIR = "desktop/src/main/resources/images/";
    private static final String IMAGE_EXTENSION = ".png";

    public uiItem(Item item) {
        this.item = item;
        this.itemImageIcon = createImageIcon();
        this.itemImageLabel = createImageLabel();
    }
    
    private ImageIcon createImageIcon() {
        
        ImageIcon itemImageIcon = null;

        System.out.println(IMAGE_DIR + this.item.getImageKey() + IMAGE_EXTENSION);
        
        try {
            itemImageIcon = ImageResizer.resizeImageIcon(new ImageIcon(IMAGE_DIR + this.item.getImageKey() + IMAGE_EXTENSION),
                ItemLayeredPane.getItemWidth(this.item.getItemType()), ItemLayeredPane.getItemHeight(this.item.getItemType()));
        } catch (Exception e) {
            System.out.println("Error resizing image");
            System.out.println(e.getMessage());
            System.exit(1); // fairly fatal flaw if the image cannot be correctly resized - just exit
        }

        return itemImageIcon;
        
    }

    public JLabel createImageLabel() {
        JLabel itemImageIcon = new JLabel(this.itemImageIcon);
        return itemImageIcon;
    }

    public JLabel getItemImageLabel() {
        return this.itemImageLabel;
    }

    public Item getItem() {
        return this.item;
    }

}
