package com.craig.itemgame.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.craig.itemgame.controller.GameController;

import com.craig.itemgame.model.CharacterStatNames;

public final class CharacterScreen extends JPanel implements CharacterView {

    public static final String NAME_STRING = "Name: ";
    public static final String LEVEL_STRING = "Level: ";
    public static final String EXPERIENCE_STRING = "Experience: ";
    public static final String DAMAGE_STRING = "Damage: ";
    public static final String HEALTH_STRING = "Health: ";
    public static final String DEFENCE_STRING = "Defence: ";
    public static final String FIRE_RESISTANCE_STRING = "Fire Resistance: ";
    public static final String COLD_RESISTANCE_STRING = "Cold Resistance: ";
    public static final String LIGHTNING_RESISTANCE_STRING = "Lightning Resistance: ";
    public static final String POISON_RESISTANCE_STRING = "Poison Resistance: ";
    public static final String MAGIC_REISTANCE_STRING = "Magic Resistance: ";

    JLabel name;
    JLabel level;
    JLabel experience;
    JLabel damage;
    JLabel health;
    JLabel defence;
    JLabel fireResistance;
    JLabel coldResistance;
    JLabel lightningResistance;
    JLabel poisonResistance;
    JLabel magicResistance;

    JSeparator separator;

    // JButton showItemScreenButton;
    private JButton returnToHomeScreenButton;
    private JButton saveChangesButton;

    private Gui gui;

    private GameController gameController;
    
    private ItemPanel itemPanel;
    private JPanel statsPanel;

    private boolean pendingRepaint = false;

    public CharacterScreen(Gui gui, GameController gameController) {

        this.gui = gui;
        this.gameController = gameController;
        
        this.itemPanel = new ItemPanel(this.gameController.getCharacterController());
        
        this.statsPanel = new JPanel();
        this.statsPanel.setLayout(new BoxLayout(this.statsPanel, BoxLayout.Y_AXIS));

        // character screen
        this.name = new JLabel(NAME_STRING);
        this.level = new JLabel(LEVEL_STRING);
        this.experience = new JLabel(EXPERIENCE_STRING);
        this.damage = new JLabel(DAMAGE_STRING);
        this.health = new JLabel(HEALTH_STRING);
        this.defence = new JLabel(DEFENCE_STRING);
        this.fireResistance = new JLabel(FIRE_RESISTANCE_STRING);
        this.coldResistance = new JLabel(COLD_RESISTANCE_STRING);
        this.lightningResistance = new JLabel(LIGHTNING_RESISTANCE_STRING);
        this.poisonResistance = new JLabel(POISON_RESISTANCE_STRING);
        this.magicResistance = new JLabel(MAGIC_REISTANCE_STRING);

        // this.showItemScreenButton = new JButton("Show Items");
        this.saveChangesButton = new JButton("Save Changes");
        this.returnToHomeScreenButton = new JButton("Return to Home Screen");

        this.statsPanel.add(name);
        this.statsPanel.add(level);
        this.statsPanel.add(experience);
        this.statsPanel.add(damage);
        this.statsPanel.add(health);
        this.statsPanel.add(defence);
        this.statsPanel.add(fireResistance);
        this.statsPanel.add(coldResistance);
        this.statsPanel.add(lightningResistance);
        this.statsPanel.add(poisonResistance);
        this.statsPanel.add(magicResistance);
        
        this.separator = new JSeparator(SwingConstants.HORIZONTAL);
        this.separator.setMaximumSize(new Dimension(this.statsPanel.getMaximumSize().width, 10));
        this.separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.statsPanel.add(this.separator);
        
        this.statsPanel.add(saveChangesButton);
        this.statsPanel.add(returnToHomeScreenButton);

        this.fireResistance.setForeground(Color.RED);
        this.coldResistance.setForeground(Color.BLUE);
        this.lightningResistance.setForeground(Color.YELLOW);
        this.poisonResistance.setForeground(Color.GREEN);
        this.magicResistance.setForeground(Color.MAGENTA);

        this.separator.setMaximumSize(new Dimension(this.statsPanel.getMaximumSize().width, 10));
        this.separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        this.statsPanel.setBorder(lineBorder);

        this.statsPanel.setBackground(new Color(169, 169, 169));

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.statsPanel);
        this.add(this.itemPanel);


        this.saveChangesButton.addActionListener(e -> {
            this.gameController.saveCharacter();
        });


        this.returnToHomeScreenButton.addActionListener(e -> {
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);
        });


    }

    public void loadCharacter() {

        this.itemPanel.loadItems();
        // update();

    }


    public ItemPanel getItemPanel() {
        return this.itemPanel;
    }

    /**
     * Updates the character name... should really only be called on load/reload
     */
    @Override
    public void updateCharacterName(String name) {
        this.name.setText(NAME_STRING + name);

        this.statsPanel.revalidate();
        this.statsPanel.repaint();
    }

    /**
     * Updates the specified Character Stat to the specified value
     */
    @Override
    public void updateCharacterStat(CharacterStatNames stat, double value) {

        switch (stat) {
            case CharacterStatNames.LEVEL:
                this.level.setText(LEVEL_STRING + value);
                break;
            case CharacterStatNames.EXPERIENCE:
                this.experience.setText(EXPERIENCE_STRING + value);
                break;
            case CharacterStatNames.DAMAGE:
                this.damage.setText(DAMAGE_STRING + value);
                break;
            case CharacterStatNames.DEFENCE:
                this.defence.setText(DEFENCE_STRING + value);
                break;
            case CharacterStatNames.HEALTH:
                this.health.setText(HEALTH_STRING + value);
                break;
            case CharacterStatNames.FIRE_RESISTANCE:
                this.fireResistance.setText(FIRE_RESISTANCE_STRING + value);
                break;
            case CharacterStatNames.POISON_RESISTANCE:
                this.poisonResistance.setText(POISON_RESISTANCE_STRING + value);
                break;
            case CharacterStatNames.LIGHTNING_RESISTANCE:
                this.lightningResistance.setText(LIGHTNING_RESISTANCE_STRING + value);
                break;
            case CharacterStatNames.COLD_RESISTANCE:
                this.coldResistance.setText(COLD_RESISTANCE_STRING + value);
                break;
            case CharacterStatNames.MAGIC_RESISTANCE:
                this.magicResistance.setText(MAGIC_REISTANCE_STRING + value);
                break;
        }

    }

    /**
     * used to stop multiple repaints called in succession
     * the pending repaint methods are not really necessary for a project of this size.. could potentially
     * push them into a class in the future but probably not worth the effort
     */
    public void pendingRepaint() {

        if (!this.pendingRepaint) {
            this.pendingRepaint = true;
            SwingUtilities.invokeLater(() -> {
                this.statsPanel.revalidate();
                this.statsPanel.repaint();
                this.pendingRepaint = false;
            });
        }

    }


}