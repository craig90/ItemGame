import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLayeredPane;


public class ItemWindow extends JFrame {

    JLayeredPane layeredPane;

    JPanel itemPanel;
    JPanel hoverPanel;

    JLabel helm;
    JLabel weapon;
    JLabel amulet;
    JLabel ring;
    // JLabel ringTwo;// TODO
    JLabel belt;
    JLabel pants;
    JLabel gloves;
    JLabel shield;
    JLabel chest;
    JLabel boots;

    Character character;

    public ItemWindow(Character character) {

        this.character = character;

        this.setTitle("Items");
        this.setSize(700, 500);

        this.layeredPane = new JLayeredPane();

        this.itemPanel = new JPanel(new FlowLayout());

        this.helm = new JLabel("Helm");
        this.weapon = new JLabel("Weapon");
        this.amulet = new JLabel("Amulet");
        this.ring = new JLabel("Ring");
        this.chest = new JLabel("Chest");
        this.gloves = new JLabel("Gloves");
        this.shield = new JLabel("Shield");
        this.belt = new JLabel("Belt");
        this.pants = new JLabel("Pants");
        this.boots = new JLabel("Boots");

        this.itemPanel.add(this.helm);
        this.itemPanel.add(this.weapon);
        this.itemPanel.add(this.amulet);
        this.itemPanel.add(this.ring);
        this.itemPanel.add(this.belt);
        this.itemPanel.add(this.pants);
        this.itemPanel.add(this.gloves);
        this.itemPanel.add(this.shield);
        this.itemPanel.add(this.chest);
        this.itemPanel.add(this.boots);

        this.itemPanel.setBounds(0, 0, 700, 500);

        this.add(this.layeredPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        this.setLocationRelativeTo(this);

        this.hoverPanel = new JPanel();
        this.hoverPanel.setLayout(new BoxLayout(this.hoverPanel, BoxLayout.PAGE_AXIS));
        // this.hoverPanel.setBounds(0, 0, 200,200);
        this.hoverPanel.setBackground(Color.WHITE);
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
        this.shield.addMouseListener(mouseListener);
        this.chest.addMouseListener(mouseListener);
        this.boots.addMouseListener(mouseListener);

    }

    public void updateHoverWindow(JLabel hoverdLabel) {

        removeAllLabels();

        String itemType = hoverdLabel.getText();
        // System.out.println(itemType);

        Item item;
        if ((item = this.character.getItem(itemType)) == null) {
            return;
        }

        JLabel itemName = new JLabel(item.getItemName());
        this.hoverPanel.add(itemName);

        for (int i = 0; i < item.statListSize(); i++) {
            JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
            this.hoverPanel.add(label);
        }

        this.hoverPanel.setBounds(hoverdLabel.getX(), hoverdLabel.getY(), 200, 200);
        this.hoverPanel.setSize(this.hoverPanel.getPreferredSize());
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
