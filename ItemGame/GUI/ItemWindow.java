import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Point;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;

import java.awt.Color;
import javax.swing.JLayeredPane;


public class ItemWindow extends JFrame {

    JLayeredPane layeredPane;

    JPanel itemPanel;
    JPanel hoverPanel;

    JLabel helm, weapon, amulet, ring, ringTwo, belt, pants, gloves, shieldLabel, chest, boots;

    JLayeredPane helmPanel, shieldPanel;
    
    JPanel weaponPanel, amuletPanel, ringPanel, ringTwoPanel, beltPanel, pantsPanel, glovesPanel;
    JPanel chestPanel, bootsPanel;

    ImageIcon helmIcon, shieldIcon;

    Character character;

    public ItemWindow(Character character) {

        this.character = character;

        this.setTitle("Items");
        this.setSize(700, 700);

        this.layeredPane = new JLayeredPane();

        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new GridBagLayout());

        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        
        // TODO: try catch

        this.helmIcon = ImageResizer.resizeImageIcon(new ImageIcon(this.character.getItem(Item.HELM).getImageFile()), 125, 125);
        this.shieldIcon = ImageResizer.resizeImageIcon(new ImageIcon(this.character.getItem(Item.SHIELD).getImageFile()), 125, 175);

        // helm creation
        this.helmPanel = new JLayeredPane();
        this.helmPanel.setBackground(Color.GRAY);
        this.helmPanel.setPreferredSize(new Dimension(125, 125));
        this.helmPanel.setOpaque(true);
        this.helm = new JLabel("Helm");
        JLabel helmIconLabel = new JLabel(this.helmIcon);
        this.helm.setBounds(0,0,125,125);
        this.helmPanel.add(this.helm, 1);
        helmIconLabel.setBounds(0,0,125,125);
        this.helmPanel.add(helmIconLabel, 0);
        
        // constraints for helm panel alignment
        c.gridx = 2;
        c.gridy = 0;
        this.itemPanel.add(this.helmPanel, c);

        this.weaponPanel = new JPanel();
        this.weaponPanel.setBackground(Color.GRAY);
        this.weaponPanel.setPreferredSize(new Dimension(125, 175));
        this.weapon = new JLabel("Weapon");
        c.gridx = 0;
        c.gridy = 1;
        this.weaponPanel.add(this.weapon);
        this.itemPanel.add(this.weaponPanel, c);


        this.amuletPanel = new JPanel();
        this.amuletPanel.setBackground(Color.GRAY);
        this.amuletPanel.setPreferredSize(new Dimension(55, 55));
        this.amulet = new JLabel("Amulet");
        c.gridx = 3;
        c.gridy = 0;
        this.amuletPanel.add(this.amulet);
        this.itemPanel.add(this.amuletPanel, c);
        
        this.ringPanel = new JPanel();
        this.ringPanel.setBackground(Color.GRAY);
        this.ringPanel.setPreferredSize(new Dimension(55, 55));
        this.ring = new JLabel("Ring1");
        c.gridx = 1;
        c.gridy = 2;
        this.ringPanel.add(this.ring);
        this.itemPanel.add(ringPanel, c);
        
        //TODO: how do we implement this? - It's currently just a placeholder
        this.ringTwoPanel = new JPanel();
        this.ringTwoPanel.setBackground(Color.GRAY);
        this.ringTwoPanel.setPreferredSize(new Dimension(55, 55));
        this.ringTwo = new JLabel("Ring2");
        c.gridx = 3;
        c.gridy = 2;
        this.ringTwoPanel.add(this.ringTwo);
        this.itemPanel.add(this.ringTwoPanel, c);

        this.chestPanel = new JPanel();
        this.chestPanel.setBackground(Color.GRAY);
        this.chestPanel.setPreferredSize(new Dimension(125, 175));
        this.chest = new JLabel("Chest");
        c.gridx = 2;
        c.gridy = 1;
        this.chestPanel.add(this.chest);
        this.itemPanel.add(this.chestPanel, c);

        this.glovesPanel = new JPanel();
        this.glovesPanel.setBackground(Color.GRAY);
        this.glovesPanel.setPreferredSize(new Dimension(125, 125));
        this.gloves = new JLabel("Gloves");
        c.gridx = 0;
        c.gridy = 2;
        this.glovesPanel.add(this.gloves);
        this.itemPanel.add(this.glovesPanel, c);

        // create shield
        this.shieldPanel = new JLayeredPane();
        this.shieldPanel.setBackground(Color.GRAY);
        this.shieldPanel.setPreferredSize(new Dimension(125, 175));
        this.shieldPanel.setOpaque(true);
        this.shieldLabel = new JLabel("Shield");
        JLabel shieldIconLabel = new JLabel(this.shieldIcon);
        c.gridx = 4;
        c.gridy = 1;
        this.shieldLabel.setBounds(0,0,125,175);
        this.shieldPanel.add(this.shieldLabel, 1);
        shieldIconLabel.setBounds(0,0,125, 175);
        this.shieldPanel.add(shieldIconLabel, 0);
        this.itemPanel.add(this.shieldPanel, c);

        // create belt
        this.beltPanel = new JPanel();
        this.beltPanel.setBackground(Color.GRAY);
        this.beltPanel.setPreferredSize(new Dimension(125, 55));
        this.belt = new JLabel("Belt");
        c.gridx = 2;
        c.gridy = 2;
        this.beltPanel.add(this.belt);
        this.itemPanel.add(this.beltPanel, c);

        this.pantsPanel = new JPanel();
        this.pantsPanel.setBackground(Color.GRAY);
        this.pantsPanel.setPreferredSize(new Dimension(125, 175));
        this.pants = new JLabel("Pants");
        c.gridx = 2;
        c.gridy = 3;
        this.pantsPanel.add(this.pants);
        this.itemPanel.add(this.pantsPanel, c);

        this.bootsPanel = new JPanel();
        this.bootsPanel.setBackground(Color.GRAY);
        this.bootsPanel.setPreferredSize(new Dimension(125, 125));
        this.boots = new JLabel("Boots");
        c.gridx = 4;
        c.gridy = 2;
        this.bootsPanel.add(this.boots);
        this.itemPanel.add(this.bootsPanel, c);

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
        this.helm.addMouseListener(mouseListener);
        this.weapon.addMouseListener(mouseListener);
        this.amulet.addMouseListener(mouseListener);
        this.ring.addMouseListener(mouseListener);
        this.belt.addMouseListener(mouseListener);
        this.pants.addMouseListener(mouseListener);
        this.gloves.addMouseListener(mouseListener);
        this.shieldLabel.addMouseListener(mouseListener);
        this.chest.addMouseListener(mouseListener);
        this.boots.addMouseListener(mouseListener);

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
