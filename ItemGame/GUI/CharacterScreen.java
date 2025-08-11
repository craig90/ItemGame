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
import javax.swing.border.Border;

public class CharacterScreen extends JPanel {

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
    JButton returnToHomeScreenButton;
    JButton saveChangesButton;

    Character character;
    Gui gui;
    CharacterSaver characterSaver;

    ItemPanel itemPanel;

    JPanel statsPanel;

    public CharacterScreen(Gui gui) {

        this.gui = gui;
        this.character = this.gui.getGame().getCharacter();
        this.itemPanel = new ItemPanel(this, this.gui.getGame().getItemController());
        this.characterSaver = this.gui.getGame().getCharacterSaver();

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

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        this.statsPanel.setBorder(lineBorder);

        this.statsPanel.setBackground(new Color(169, 169, 169));

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.statsPanel);
        this.add(this.itemPanel);


        this.saveChangesButton.addActionListener(e -> {
            this.characterSaver.saveCharacter(this.character);
        });


        this.returnToHomeScreenButton.addActionListener(e -> {
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);
        });


    }

    public void loadCharacter() {

        // make sure we have the latest character
        this.character = this.gui.getGame().getCharacter();
        
        if (this.character == null) {
            System.out.println("Error - we have no loaded character");
            return;
        }
        
        // this only needs to be done once
        this.gui.getGame().getItemController().setItemPanel(this.itemPanel);

        // TODO: this maybe should be a bit cleaner
        // update the items for the new character
        this.itemPanel.setItemManager(this.character.getItemManager());
        this.gui.getGame().getItemController().setItemManager(this.character.getItemManager());
        
        // TODO: this should be moved into item panel
        this.itemPanel.clearItemPanes(); // if we're reloading we have to clear what's there already
        this.itemPanel.setItemsInPanes();
        this.itemPanel.setSourceTransferHandlers();
        this.itemPanel.setupHoverPanel();

        update();

    }


    /**
     * updates all the character stats in the gui
     */
    public void update() {

        // update all the stats on the character stats
        this.character.updateStats();
        this.name.setText(NAME_STRING + this.character.getName());
        this.level.setText(LEVEL_STRING + this.character.getLevel());
        this.experience.setText(EXPERIENCE_STRING + this.character.getExperience());
        this.damage.setText(DAMAGE_STRING + this.character.getDamage());
        this.health.setText(HEALTH_STRING + this.character.getHealth());
        this.defence.setText(DEFENCE_STRING + this.character.getDefence());
        this.fireResistance.setText(FIRE_RESISTANCE_STRING + this.character.getFireResistance());
        this.coldResistance.setText(COLD_RESISTANCE_STRING + this.character.getColdResistance());
        this.lightningResistance.setText(LIGHTNING_RESISTANCE_STRING + this.character.getLightningResistance());
        this.poisonResistance.setText(POISON_RESISTANCE_STRING + this.character.getPoisonResistance());
        this.magicResistance.setText(MAGIC_REISTANCE_STRING + this.character.getMagicResistance());

        this.fireResistance.setForeground(Color.RED);
        this.coldResistance.setForeground(Color.BLUE);
        this.lightningResistance.setForeground(Color.YELLOW);
        this.poisonResistance.setForeground(Color.GREEN);
        this.magicResistance.setForeground(Color.MAGENTA);


        this.separator.setMaximumSize(new Dimension(this.statsPanel.getMaximumSize().width, 10));
        this.separator.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.statsPanel.revalidate();
        this.statsPanel.repaint();
    }

    // /**
    //  * Used to update the Character Screen's character since the character can change
    //  * @param character
    //  */
    // public void setCharacter(Character character) {
    //     this.character = character;
    // }

}