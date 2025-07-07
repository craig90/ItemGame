import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLayeredPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class ItemWindow extends JFrame {

    final static int ITEM_WINDOW_WIDTH = 700;
    final static int ITEM_WINDOW_HEIGHT = 700;

    JLayeredPane layeredPane;

    JPanel itemPanel;
    // JPanel hoverPanel;
    HoverPanel hoverPanel;

    JLabel helm, weapon, amulet, ring, ringTwo, belt, pants, gloves, shieldLabel, chest, boots;

    ItemLayeredPane helmPanel, weaponPanel, amuletPanel, shieldPanel, ringOnePanel, ringTwoPanel;
    ItemLayeredPane beltPanel, pantsPanel, glovesPanel, chestPanel, bootsPanel;

    ImageIcon helmIcon, shieldIcon;

    JButton getNewItemButton;
    ItemLayeredPane spareItemPanel;

    Character character;
    ItemGenerator itemGenerator;

    public ItemWindow(Character character) {

        this.character = character;
        this.itemGenerator = new ItemGenerator();

        this.setTitle("Items");
        this.setSize(ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.layeredPane = new JLayeredPane();

        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new GridBagLayout());

        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        
        
        // TODO: wondering if the get method and how the character stores items should be rethought a bit
        // helm
        this.helmPanel = new ItemLayeredPane(Item.HELM, character.getItem(Item.HELM));
        c.gridx = 2;
        c.gridy = 0;
        this.itemPanel.add(this.helmPanel, c);

        // weapon
        this.weaponPanel = new ItemLayeredPane(Item.WEAPON, this.character.getItem(Item.WEAPON));
        c.gridx = 0;
        c.gridy = 1;
        this.itemPanel.add(this.weaponPanel, c);

        // amulet
        this.amuletPanel = new ItemLayeredPane(Item.AMULET, this.character.getItem(Item.AMULET));
        c.gridx = 3;
        c.gridy = 0;
        this.itemPanel.add(this.amuletPanel, c);
        
        // ring
        this.ringOnePanel = new ItemLayeredPane(Item.RING, this.character.getItem(Item.RING));
        c.gridx = 1;
        c.gridy = 2;
        this.itemPanel.add(this.ringOnePanel, c);
        
        //TODO: how do we implement this? - It's currently just a placeholder
        this.ringTwoPanel = new ItemLayeredPane(Item.RING, this.character.getItem(Item.RING));
        c.gridx = 3;
        c.gridy = 2;
        this.itemPanel.add(this.ringTwoPanel, c);

        // chest panel
        this.chestPanel = new ItemLayeredPane(Item.CHEST, this.character.getItem(Item.CHEST));
        c.gridx = 2;
        c.gridy = 1;
        this.itemPanel.add(this.chestPanel, c);

        // gloves
        this.glovesPanel = new ItemLayeredPane(Item.GLOVES, this.character.getItem(Item.GLOVES));
        c.gridx = 0;
        c.gridy = 2;
        this.itemPanel.add(this.glovesPanel, c);

        // shield
        this.shieldPanel = new ItemLayeredPane(Item.SHIELD, this.character.getItem(Item.SHIELD));
        c.gridx = 4;
        c.gridy = 1;
        this.itemPanel.add(this.shieldPanel, c);

        // belt
        this.beltPanel = new ItemLayeredPane(Item.BELT, this.character.getItem(Item.BELT));
        c.gridx = 2;
        c.gridy = 2;
        this.itemPanel.add(this.beltPanel, c);

        // pants
        this.pantsPanel = new ItemLayeredPane(Item.PANTS, this.character.getItem(Item.PANTS));
        c.gridx = 2;
        c.gridy = 3;
        this.itemPanel.add(this.pantsPanel, c);

        // boots
        this.bootsPanel = new ItemLayeredPane(Item.BOOTS, this.character.getItem(Item.BOOTS));
        c.gridx = 4;
        c.gridy = 2;
        this.itemPanel.add(this.bootsPanel, c);

        // generate new item button
        this.getNewItemButton = new JButton("Get New Item");
        c.gridx = 5;
        c.gridy = 2;
        this.itemPanel.add(this.getNewItemButton, c);

        // holder for extra item
        spareItemPanel = new ItemLayeredPane(ItemLayeredPane.NEW_ITEM_SLOT, null);
        c.gridx = 5;
        c.gridy = 1;
        this.itemPanel.add(spareItemPanel, c);

        generateItemIcons();
        setItemsInPanes();

        this.itemPanel.setBounds(0, 0, ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.add(this.layeredPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        this.setLocationRelativeTo(this);

        this.hoverPanel = new HoverPanel(this.character, this.spareItemPanel);

        this.layeredPane.add(this.itemPanel, 1);
        this.layeredPane.add(this.hoverPanel, 0);

        this.setVisible(true);

        MyMouseListener mouseListener = new MyMouseListener(this);

        // add mouse listeners to the current item image icons
        for (int i = 0; i < this.character.getItemListSize(); i++) {
            this.character.getItem(i).getItemImageLabel().addMouseListener(mouseListener);
        }

        this.getNewItemButton.addActionListener(e -> {
            Item newItem = this.itemGenerator.createNewItem();
            newItem.setImageLabel(); // TODO: this should be called in a better place
            newItem.getItemImageLabel().addMouseListener(mouseListener);
            this.spareItemPanel.setItem(newItem);
            this.spareItemPanel.updateItem();
            
        });

    }


    /**
     * TODO: I'm not sure if this should be done here, but this will generate the Image Icons for currently 
     * equippied items
     */
    private void generateItemIcons() {

        for (int i = 0; i < this.character.getItemListSize(); i++) {
            Item item = this.character.getItem(i);
            item.setImageLabel();
        }

    }

    /**
     * sets whichever items the character has in their respective item panes
     * TODO: add rings and blings
     * 
     */
    private void setItemsInPanes() {

        if (this.character.getItem(Item.HELM) != null) {
            this.helmPanel.setItem(this.character.getItem(Item.HELM));
            this.helmPanel.updateItem();
        }

        if (this.character.getItem(Item.WEAPON) != null) {
            this.weaponPanel.setItem(this.character.getItem(Item.WEAPON));
            this.weaponPanel.updateItem();
        }

        if (this.character.getItem(Item.AMULET) != null) {
            this.amuletPanel.setItem(this.character.getItem(Item.AMULET));
            this.amuletPanel.updateItem();
        }
        
        if (this.character.getItem(Item.CHEST) != null) {
            this.chestPanel.setItem(this.character.getItem(Item.CHEST));
            this.chestPanel.updateItem();
        }

        if (this.character.getItem(Item.SHIELD) != null) {
            this.shieldPanel.setItem(this.character.getItem(Item.SHIELD));
            this.shieldPanel.updateItem();
        }
        
        if (this.character.getItem(Item.BOOTS) != null) {
            this.bootsPanel.setItem(this.character.getItem(Item.BOOTS));
            this.bootsPanel.updateItem();
        }

        if (this.character.getItem(Item.BELT) != null) {
            this.beltPanel.setItem(this.character.getItem(Item.BELT));
            this.beltPanel.updateItem();
        }

        if (this.character.getItem(Item.GLOVES) != null) {
            this.glovesPanel.setItem(this.character.getItem(Item.GLOVES));
            this.glovesPanel.updateItem();
        }

        if (this.character.getItem(Item.PANTS) != null) {
            this.pantsPanel.setItem(this.character.getItem(Item.PANTS));
            this.pantsPanel.updateItem();
        }
    }

}

