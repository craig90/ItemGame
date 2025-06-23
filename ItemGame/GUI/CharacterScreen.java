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
    JButton getNewItemButton;

    // TODO: this may have to be rethought
    Character character;
    Gui gui;

    public CharacterScreen(Character character, Gui gui) {

        this.character = character;
        this.gui = gui;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // TODO: need to think about when we're going to run updates of stats
        // character screen
        this.characterName = new JLabel("Name: " + this.character.getName());
        this.characterLevel = new JLabel("Level: " + this.character.getLevel());
        this.characterExperience = new JLabel("Experience: " + this.character.getExperience());
        this.characterDamage = new JLabel("Damage: " + this.character.getDamage());
        this.characterHealth = new JLabel("Health: " + this.character.getHealth());
        this.characterDefence = new JLabel("Defence: " + this.character.getDefence());
        this.characterFireResistance = new JLabel("Fire Resistance: " + this.character.getFireResistance());
        this.characterColdResistance = new JLabel("Cold Resistance: " + this.character.getColdResistance());
        this.characterLightningResistance = new JLabel("Lightning Resistance: " + this.character.getLightningResistance());
        this.characterPoisonResistance = new JLabel("Poison Resistance: " + this.character.getPoisonResistance());
        this.characterMagicResistance = new JLabel("Magic Resistance: " + this.character.getMagicResistance());

        this.showItemScreenButton = new JButton("Show Items");
        this.getNewItemButton = new JButton("Get New Item");
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
        this.add(getNewItemButton);
        this.add(returnToHomeScreenButton);

        // TODO: hook this button up
        this.showItemScreenButton.addActionListener(e -> {
            ItemWindow itemWindow = new ItemWindow(this.character);
        });

        this.returnToHomeScreenButton.addActionListener(e -> {
            this.gui.cardLayout.show(this.gui.mainPanel, "Intro Screen");
        });


    }

    // TODO: shouldn't be writing all this shit out twice

    public void update() {

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


}