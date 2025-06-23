import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JLayeredPane;


public class ItemWindow extends JFrame {

    JLayeredPane layeredPane;

    JPanel itemPanel;
    JPanel hoverPanel;

    JLabel hoverLabel;

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

        this.helm = new JLabel("Helm place holder");
        this.weapon = new JLabel("Weapon place holder");
        this.amulet = new JLabel("Amulet place holder");
        this.ring = new JLabel("Ring place holder");
        this.chest = new JLabel("Chest place holder");
        this.gloves = new JLabel("Gloves place holder");
        this.shield = new JLabel("Shield place holder");
        this.belt = new JLabel("Belt place holder");
        this.pants = new JLabel("Pants place holder");
        this.boots = new JLabel("Boots place holder");

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
        this.hoverLabel = new JLabel();

        this.hoverPanel.setBounds(0, 0, 200,200);
        // this.hoverPanel.setBackground(Color.BLUE);
        this.hoverPanel.add(this.hoverLabel);
        this.hoverPanel.setVisible(false);

        this.layeredPane.add(this.itemPanel, 1);
        this.layeredPane.add(this.hoverPanel, 0);

        this.setVisible(true);


        this.helm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updateHoverWindow();
                hoverPanel.setVisible(true);
                hoverPanel.revalidate(); // Revalidate the layout to ensure it's displayed correctly
                hoverPanel.repaint();
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverPanel.setVisible(false);
                hoverPanel.revalidate();
                hoverPanel.repaint();
            }
        });


    }

    public void updateHoverWindow() {

        Item item;
        if ((item = this.character.getItem("Helm")) == null) {
            return;
        }

        this.hoverLabel.setText(item.getItemName());

        for (int i = 0; i < item.statListSize(); i++) {
            JLabel label = new JLabel(item.getStat(i).getStatType() + " " + item.getStat(i).getValue());
            this.hoverPanel.add(label);
        }

    }

}
