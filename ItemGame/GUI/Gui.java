import java.awt.CardLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {

    public CardLayout cardLayout;
    public JPanel mainPanel;

    private CharacterScreen characterScreen;

    private Game game;

    public Gui () {

        this.game = new Game();

        // Create the main frame
        this.setTitle("Item Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);

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
        
        ArrayList<CharacterFile> characterFiles = new ArrayList<CharacterFile>();
        ArrayList<String> fileNames = new ArrayList<String>();

        try {
            fileNames = FileReader.readFileNames();
            characterFiles = this.game.getCharacterNamesFromFile(fileNames);
        } catch (FileNotFoundException e) {
           // TODO: catch here -- say no files found then return to home screen
           // maybe make a new screen to illustrate it
        }

        for (int i = 0; i < characterFiles.size(); i++) {
            JLabel label = new JLabel(characterFiles.get(i).getCharacterName() + " Level: " + characterFiles.get(i).getLevel());
            JButton button = new JButton("Load");
            loadCharacterScreen.add(label);
            loadCharacterScreen.add(button);
            CharacterFile characterFile = characterFiles.get(i);
            button.addActionListener(e -> {
                this.cardLayout.show(this.mainPanel, "Character Screen");
                // System.out.println(fileName);
                try {
                    this.game.setCharacter(this.game.fileParcer.loadCharacterFromFile(characterFile));
                    this.game.getCharacter().updateStats();
                    this.characterScreen.setCharacter(this.game.getCharacter()); // TODO: not sure if this is the best way 
                    this.characterScreen.update();
                } catch (FileNotFoundException ex) {

                }

            });
        }

        // JLabel testCharacterLabel = new JLabel("Character 1");
        JButton backToHomeScreenButton = new JButton("Return to Home Screen");
        // loadCharacterScreen.add(testCharacterLabel);
        loadCharacterScreen.add(backToHomeScreenButton);

        backToHomeScreenButton.addActionListener(e -> {
            this.cardLayout.show(this.mainPanel, "Intro Screen");
        });



        this.characterScreen = new CharacterScreen(this.game.getCharacter(), this);



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
        this.mainPanel.add(this.characterScreen, "Character Screen");
        
        this.add(mainPanel);
        this.setVisible(true);

    }
    
    public Game getGame() {
        return this.game;
    }


    public static void main(String[] args) {
        
        Gui gui = new Gui();
        

    }


}
