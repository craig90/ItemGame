import java.awt.CardLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private Game game;

    public Gui () {

        this.game = new Game();

        // Create the main frame
        this.setTitle("Item Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout);
        

        // home screen
        JPanel introScreen = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to the Item Game");

        JButton createCharButton = new JButton("Create New Character");
        JButton loadCharButton = new JButton("Load Character From File");

        introScreen.add(welcomeLabel);
        introScreen.add(createCharButton);
        introScreen.add(loadCharButton);

        loadCharButton.addActionListener(e -> {
            this.cardLayout.show(this.mainPanel, "Load Character Screen");
        });

        createCharButton.addActionListener(e -> {
            this.cardLayout.show(this.mainPanel, "Create Character Screen");
        });


        // load character screen
        JPanel loadCharacterScreen = new JPanel();
        
        ArrayList<String> fileNames = new ArrayList<String>();
        ArrayList<String> characterNames = new ArrayList<String>();

        try {
            fileNames = FileReader.readFileNames();
            characterNames = this.game.getCharacterNamesFromFile(fileNames);
        } catch (FileNotFoundException e) {
           // TODO: catch here -- say no files found then return to home screen
           // maybe make a new screen to illustrate it
        }

        for (int i = 0; i < characterNames.size(); i++) {
            JLabel label = new JLabel(characterNames.get(i) + " Level: 20"); // TODO: level is hard coded for now
            JButton button = new JButton("Load");
            loadCharacterScreen.add(label);
            loadCharacterScreen.add(button);
            button.addActionListener(e -> {
                
                this.cardLayout.show(this.mainPanel, "Character Screen");
            });
        }

        // JLabel testCharacterLabel = new JLabel("Character 1");
        JButton backToHomeScreenButton = new JButton("Return to Home Screen");
        // loadCharacterScreen.add(testCharacterLabel);
        loadCharacterScreen.add(backToHomeScreenButton);

        backToHomeScreenButton.addActionListener(e -> {
            this.cardLayout.show(this.mainPanel, "Intro Screen");
        });



        // TODO: need to think about when we're going to run updates of stats
        // character screen
        JPanel characterScreen = new JPanel();
        JLabel characterName = new JLabel("Name: " + this.game.character.getName());
        JLabel characterLevel = new JLabel("Level: " + this.game.character.getLevel());
        JLabel characterExperience = new JLabel("Experience: " + this.game.character.getExperience());
        JLabel characterDamage = new JLabel("Damage: " + this.game.character.getDamage());
        JLabel characterHealth = new JLabel("Health: " + this.game.character.getHealth());
        JLabel characterDefence = new JLabel("Defence: " + this.game.character.getDefence());
        JLabel characterFireResistance = new JLabel("Fire Resistance: " + this.game.character.getFireResistance());
        JLabel characterColdResistance = new JLabel("Cold Resistance: " + this.game.character.getColdResistance());
        JLabel characterLightningResistance = new JLabel("Lightning Resistance: " + this.game.character.getLightningResistance());
        JLabel characterPoisonResistance = new JLabel("Poison Resistance: " + this.game.character.getPoisonResistance());
        JLabel characterMagicResistance = new JLabel("Magic Resistance: " + this.game.character.getMagicResistance());

        characterScreen.add(characterName);
        characterScreen.add(characterLevel);
        characterScreen.add(characterExperience);
        characterScreen.add(characterDamage);
        characterScreen.add(characterHealth);
        characterScreen.add(characterDefence);
        characterScreen.add(characterFireResistance);
        characterScreen.add(characterColdResistance);
        characterScreen.add(characterLightningResistance);
        characterScreen.add(characterPoisonResistance);
        characterScreen.add(characterMagicResistance);




        // character creation screen
        JPanel createCharacterScreen = new JPanel();
        JLabel createCharLabel = new JLabel("Create a New Character");
        JLabel enterCharNameLabel = new JLabel("Enter a character name");
        JTextField charNameTextField = new JTextField(20);
        JButton submitCharNameButton = new JButton("Submit");
        JButton backToHomeScreenButtonTwo = new JButton("Return to Home Screen");
        createCharacterScreen.add(createCharLabel);
        createCharacterScreen.add(enterCharNameLabel);
        createCharacterScreen.add(charNameTextField);
        createCharacterScreen.add(submitCharNameButton);
        createCharacterScreen.add(backToHomeScreenButtonTwo);

        backToHomeScreenButtonTwo.addActionListener(e -> {
            this.cardLayout.show(this.mainPanel, "Intro Screen");
        });






        this.mainPanel.add(introScreen, "Intro Screen");
        this.mainPanel.add(loadCharacterScreen, "Load Character Screen");
        this.mainPanel.add(createCharacterScreen, "Create Character Screen");
        this.mainPanel.add(characterScreen, "Character Screen");
        
        this.add(mainPanel);
        this.setVisible(true);

    }
    
    
    public void introScreen() {

    }





    public static void main(String[] args) {
        
        Gui gui = new Gui();
        

    }


}
