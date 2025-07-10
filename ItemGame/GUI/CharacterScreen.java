import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterScreen extends JPanel {

    JLabel characterName;
    JLabel characterLevel;
    JLabel characterExperience;
    JLabel characterDamage;
    JLabel characterHealth;
    JLabel characterDefence;
    JLabel characterFireResistance;
    JLabel characterColdResistance;
    JLabel characterLightningResistance;
    JLabel characterPoisonResistance;
    JLabel characterMagicResistance;

    JButton showItemScreenButton;
    JButton returnToHomeScreenButton;
    JButton saveChangesButton;

    Character character;
    Gui gui;

    ItemWindow itemWindow;

    public CharacterScreen(Character character, Gui gui) {

        this.character = character;
        this.gui = gui;
        this.itemWindow = null;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // TODO: use constants for labels
        // character screen
        this.characterName = new JLabel("Name: ");
        this.characterLevel = new JLabel("Level: ");
        this.characterExperience = new JLabel("Experience: ");
        this.characterDamage = new JLabel("Damage: ");
        this.characterHealth = new JLabel("Health: ");
        this.characterDefence = new JLabel("Defence: ");
        this.characterFireResistance = new JLabel("Fire Resistance: ");
        this.characterColdResistance = new JLabel("Cold Resistance: ");
        this.characterLightningResistance = new JLabel("Lightning Resistance: ");
        this.characterPoisonResistance = new JLabel("Poison Resistance: ");
        this.characterMagicResistance = new JLabel("Magic Resistance: ");

        this.showItemScreenButton = new JButton("Show Items");
        this.saveChangesButton = new JButton("Save Changes");
        this.returnToHomeScreenButton = new JButton("Return to Home Screen");

        this.add(characterName);
        this.add(characterLevel);
        this.add(characterExperience);
        this.add(characterDamage);
        this.add(characterHealth);
        this.add(characterDefence);
        this.add(characterFireResistance);
        this.add(characterColdResistance);
        this.add(characterLightningResistance);
        this.add(characterPoisonResistance);
        this.add(characterMagicResistance);

        this.add(showItemScreenButton);
        this.add(saveChangesButton);
        this.add(returnToHomeScreenButton);

        this.showItemScreenButton.addActionListener(e -> {
            if (this.itemWindow == null) {
                this.itemWindow = new ItemWindow(this, this.character);
            } else {
                this.itemWindow.toFront();
                this.itemWindow.setVisible(true);
            }
        });

        this.returnToHomeScreenButton.addActionListener(e -> {
            
            if (this.itemWindow != null) {
                this.itemWindow.dispose();
                this.itemWindow = null; // dispose then set to null since a disposed frame can be redrawn
            }

            this.gui.cardLayout.show(this.gui.mainPanel, "Intro Screen");

        });


    }

    /**
     * updates all the character stats in the gui
     */
    public void update() {

        if (this.character == null) {
            System.out.println("Error - we have no loaded character");
            return;
        }

        this.character.updateStats();
        this.characterName.setText("Name: " + this.character.getName());
        this.characterLevel.setText("Level: " + this.character.getLevel());
        this.characterExperience.setText("Experience: " + this.character.getExperience());
        this.characterDamage.setText("Damage: " + this.character.getDamage());
        this.characterHealth.setText("Health: " + this.character.getHealth());
        this.characterDefence.setText("Defence: " + this.character.getDefence());
        this.characterFireResistance.setText("Fire Resistance: " + this.character.getFireResistance());
        this.characterColdResistance.setText("Cold Resistance: " + this.character.getColdResistance());
        this.characterLightningResistance.setText("Lightning Resistance: " + this.character.getLightningResistance());
        this.characterPoisonResistance.setText("Poison Resistance: " + this.character.getPoisonResistance());
        this.characterMagicResistance.setText("Magic Resistance: " + this.character.getMagicResistance());

    }

    /**
     * Used to update the Character Screen's character since the character can change
     * @param character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

}