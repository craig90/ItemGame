import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Point;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.Color;
import javax.swing.JLayeredPane;


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


        this.itemPanel.setBounds(0, 0, 700, 700);

        this.add(this.layeredPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        this.setLocationRelativeTo(this);

        this.hoverPanel = new JPanel();
        this.hoverPanel.setLayout(new BoxLayout(this.hoverPanel, BoxLayout.PAGE_AXIS));
        // this.hoverPanel.setBounds(0, 0, 200,200);
        // this.hoverPanel.setBackground(Color.WHITE);
        this.hoverPanel.setBackground(new Color(0,0,0,150));
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
        // this.spareItemPanel.itemLabel.addMouseListener(mouseListener);

        this.getNewItemButton.addActionListener(e -> {
            Item newItem = this.itemGenerator.createNewItem();
            this.spareItemPanel.setItem(newItem);
            this.spareItemPanel.updateItem();
        });

        // this.itemPanel.setSize(this.itemPanel.getPreferredSize());
    }

    public void updateHoverWindow(JLabel hoverdLabel) {

        removeAllLabels();

        String itemType = hoverdLabel.getText();

        Item item;
        if ((item = this.character.getItem(itemType)) == null) {
            return;
        }

        JLabel itemName = new JLabel(item.getItemName());
        itemName.setForeground(Color.WHITE);
        this.hoverPanel.add(itemName);

        for (int i = 0; i < item.statListSize(); i++) {
            JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
            label.setForeground(Color.WHITE);
            this.hoverPanel.add(label);
        }

        // Get the absolute position of the label
        Point locationOnScreen = SwingUtilities.convertPoint(hoverdLabel, 0, 0, this);
        
        this.hoverPanel.setBounds(locationOnScreen.x, locationOnScreen.y, 200, 200);
        this.hoverPanel.setSize(this.hoverPanel.getPreferredSize());

        // System.out.println("Button's absolute X position: " + locationOnScreen.x);
        // System.out.println("Button's absolute Y position: " + locationOnScreen.y);

    }

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

}
