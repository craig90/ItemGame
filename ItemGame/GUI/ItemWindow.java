import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.Color;
import javax.swing.JLayeredPane;

import java.util.ArrayList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class ItemWindow extends JFrame {

    JLayeredPane layeredPane;

    JPanel itemPanel;
    JPanel hoverPanel;

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
        this.setSize(700, 700);

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
        spareItemPanel = new ItemLayeredPane("New Item", null);
        c.gridx = 5;
        c.gridy = 1;
        this.itemPanel.add(spareItemPanel, c);

        generateItemIcons();
        setItemsInPanes();

        this.itemPanel.setBounds(0, 0, 700, 700);

        this.add(this.layeredPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        this.setLocationRelativeTo(this);

        this.hoverPanel = new JPanel();
        this.hoverPanel.setLayout(new BoxLayout(this.hoverPanel, BoxLayout.PAGE_AXIS));
        // this.hoverPanel.setBounds(0, 0, 200,200);
        // this.hoverPanel.setBackground(Color.WHITE);
        this.hoverPanel.setBackground(new Color(0,0,0,180));
        this.hoverPanel.setVisible(false);

        this.layeredPane.add(this.itemPanel, 1);
        this.layeredPane.add(this.hoverPanel, 0);

        this.setVisible(true);

        MyMouseListener mouseListener = new MyMouseListener(this, this.hoverPanel);
        this.helmPanel.itemLabel.addMouseListener(mouseListener);
        this.weaponPanel.itemLabel.addMouseListener(mouseListener);
        this.amuletPanel.itemLabel.addMouseListener(mouseListener);
        this.ringOnePanel.itemLabel.addMouseListener(mouseListener);
        this.ringTwoPanel.itemLabel.addMouseListener(mouseListener);
        this.beltPanel.itemLabel.addMouseListener(mouseListener);
        this.pantsPanel.itemLabel.addMouseListener(mouseListener);
        this.glovesPanel.itemLabel.addMouseListener(mouseListener);
        this.shieldPanel.itemLabel.addMouseListener(mouseListener);
        this.chestPanel.itemLabel.addMouseListener(mouseListener);
        this.bootsPanel.itemLabel.addMouseListener(mouseListener);
        this.spareItemPanel.itemLabel.addMouseListener(mouseListener);

        this.getNewItemButton.addActionListener(e -> {
            Item newItem = this.itemGenerator.createNewItem();
            newItem.setImageLabel(); // TODO: this should be called in a better place
            this.spareItemPanel.setItem(newItem);
            this.spareItemPanel.updateItem();
        });

    }

    public void updateHoverWindow(JLabel hoverdLabel) {

        removeAllLabels();

        String itemType = hoverdLabel.getText();

        // TODO: i really think I need an Item Manager Class
        // TODO: save New Item constant somewhere
        Item item;
        if (itemType.equals("New Item")) {
            item = this.spareItemPanel.getItem();
        } else {
            item = this.character.getItem(itemType);
        }

        if (item == null){
            return;
        }

        JLabel itemName = new JLabel(item.getItemName());
        itemName.setForeground(Color.WHITE);
        this.hoverPanel.add(itemName);

        if (itemType.equals("New Item")) {
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

                    this.hoverPanel.add(statLabel);
                    this.hoverPanel.add(diffLabel);
                }
                
                // stat only exists on the old item
                else {
                    JLabel statLabel = new JLabel(statDiff.getStat().getStatType());
                    JLabel diffLabel = new JLabel("(-" + statDiff.getStat().getValue() + ")");
                    statLabel.setForeground(Color.RED);
                    diffLabel.setForeground(Color.RED);
                    this.hoverPanel.add(statLabel);
                    this.hoverPanel.add(diffLabel);
                }

            }

        } 
        
        else {
            for (int i = 0; i < item.statListSize(); i++) {
                JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
                label.setForeground(Color.WHITE);
                this.hoverPanel.add(label);
            }
        }
        

        // Get the absolute position of the label
        Point locationOnScreen = SwingUtilities.convertPoint(hoverdLabel, 0, 0, this);
        
        this.hoverPanel.setBounds(locationOnScreen.x, locationOnScreen.y, 200, 200);
        this.hoverPanel.setSize(this.hoverPanel.getPreferredSize());

    }

    /**
     * Removes all the components from the hoverPanel
     */
    private void removeAllLabels() {
        Component[] components = this.hoverPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                this.hoverPanel.remove(component);
            }
        }
        this.hoverPanel.setSize(this.hoverPanel.getPreferredSize());
        this.hoverPanel.revalidate();
        this.hoverPanel.repaint();
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

