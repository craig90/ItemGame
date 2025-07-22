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

    // JButton showItemScreenButton;
    JButton returnToHomeScreenButton;
    JButton saveChangesButton;

    Character character;
    Gui gui;
    CharacterSaver characterSaver;

    ItemWindow itemPanel;

    JPanel statsPanel;

    public CharacterScreen(Gui gui) {

        this.gui = gui;
        this.character = this.gui.getGame().getCharacter();
        this.itemPanel = new ItemWindow(this);
        this.characterSaver = this.gui.getGame().getCharacterSaver();

        this.statsPanel = new JPanel();
        this.statsPanel.setLayout(new BoxLayout(this.statsPanel, BoxLayout.Y_AXIS));

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

        // this.showItemScreenButton = new JButton("Show Items");
        this.saveChangesButton = new JButton("Save Changes");
        this.returnToHomeScreenButton = new JButton("Return to Home Screen");

        this.statsPanel.add(characterName);
        this.statsPanel.add(characterLevel);
        this.statsPanel.add(characterExperience);
        this.statsPanel.add(characterDamage);
        this.statsPanel.add(characterHealth);
        this.statsPanel.add(characterDefence);
        this.statsPanel.add(characterFireResistance);
        this.statsPanel.add(characterColdResistance);
        this.statsPanel.add(characterLightningResistance);
        this.statsPanel.add(characterPoisonResistance);
        this.statsPanel.add(characterMagicResistance);

        // this.add(showItemScreenButton);
        this.statsPanel.add(saveChangesButton);
        this.statsPanel.add(returnToHomeScreenButton);


        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.statsPanel);
        this.add(this.itemPanel);

        System.out.println(this.itemPanel.isVisible());
        System.out.println(this.itemPanel.getSize());
        // this.showItemScreenButton.addActionListener(e -> {
            
        //     // make sure we are only opening one window at a time
        //     if (this.itemWindow == null) {
        //         this.itemWindow = new ItemWindow(this, this.character.getItemManager());
        //     } else {
        //         // this.itemWindow.toFront();
        //         this.itemWindow.setVisible(true);
        //     }
       
        // });

        this.saveChangesButton.addActionListener(e -> {
            this.characterSaver.saveCharacter(this.character);
        });


        this.returnToHomeScreenButton.addActionListener(e -> {
            
            // dispose then set to null since a disposed frame can be redrawn
            // if (this.itemWindow != null) {
            //     this.itemWindow.dispose();
            //     this.itemWindow = null;
            // }

            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);

        });


    }

    public void setCharacter() {

        // make sure we have the latest character
        this.character = this.gui.getGame().getCharacter();
        
        if (this.character == null) {
            System.out.println("Error - we have no loaded character");
            return;
        }

        // TODO: this maybe should be a bit cleaner
        // update the items for the new character
        this.itemPanel.setItemManager(this.character.getItemManager());
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

    // /**
    //  * Used to update the Character Screen's character since the character can change
    //  * @param character
    //  */
    // public void setCharacter(Character character) {
    //     this.character = character;
    // }

}