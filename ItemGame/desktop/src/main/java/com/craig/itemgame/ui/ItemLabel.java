package com.craig.itemgame.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.craig.itemgame.model.Items.Item;

public class ItemLabel extends JLabel {
    
    private static final String IMAGE_DIR = "desktop/src/main/resources/images/";
    private static final String IMAGE_EXTENSION = ".png";

    // private String itemId;

    public ItemLabel(Item item) {
        super();
        setImageIcon(item);
    }

    private void setImageIcon(Item item) {
        // this.itemId = item.getID();

        if (item.getID() == null) {
            setIcon(null);
            System.out.println("Error, item has no item id");
            System.exit(1); // exit if there is an issue with the id.  The game can't run if this happens
        }

        try {
            ImageIcon imageIcon = ImageResizer.resizeImageIcon(new ImageIcon(IMAGE_DIR + item.getImageKey() + IMAGE_EXTENSION),
            ItemLayeredPane.getItemWidth(item.getItemType()), 
            ItemLayeredPane.getItemHeight(item.getItemType()));
            System.out.println(IMAGE_DIR + item.getImageKey() + IMAGE_EXTENSION);
            setIcon(imageIcon);
            System.out.println("Successfully created image");
        } catch (Exception e) {
            System.out.println("Error resizing image");
            System.out.println(e.getMessage());
            System.exit(1); // fairly fatal flaw if the image cannot be correctly resized - just exit
        }

    }


}
