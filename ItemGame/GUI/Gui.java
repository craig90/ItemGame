import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Gui extends JFrame {

    public static final String INTRO_SCREEN = "Intro Screen";
    public static final String CHARACTER_SELECTION_SCREEN = "Character Selection Screen";
    public static final String CREATE_CHARACTER_SCREEN = "Create Character Screen";
    public static final String CHARACTER_SCREEN = "Character Screen";
    public static final String ERROR_SCREEN = "Error Screen";

    public CardLayout cardLayout;
    public JPanel cardLayoutPanel;

    private ErrorScreen errorScreen;
    protected CharacterScreen characterScreen;
    private CharacterSelectionScreen characterSelectionScreen;

    private Game game;

    public Gui () {

        this.game = new Game();

        // Create the main frame
        this.setTitle("Item Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);

        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(this.cardLayout);
        

        // home screen
        JPanel introScreen = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to the Item Game");

        JButton createCharButton = new JButton("Create New Character");
        JButton loadCharButton = new JButton("Load Character From File");

        introScreen.add(welcomeLabel);
        introScreen.add(createCharButton);
        introScreen.add(loadCharButton);


        this.errorScreen = new ErrorScreen();
        this.characterSelectionScreen = new CharacterSelectionScreen(this);
        this.characterScreen = new CharacterScreen(this);


        loadCharButton.addActionListener(e -> {
            if (this.characterSelectionScreen.update() == true) {
                System.out.println("Switching to: " + CHARACTER_SELECTION_SCREEN);
                this.cardLayout.show(this.cardLayoutPanel, CHARACTER_SELECTION_SCREEN);
            }
        });

        createCharButton.addActionListener(e -> {
            System.out.println("Switching to: " + CREATE_CHARACTER_SCREEN);
            this.cardLayout.show(this.cardLayoutPanel, CREATE_CHARACTER_SCREEN);
        });



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
            System.out.println("Switching to: " + INTRO_SCREEN);
            this.cardLayout.show(this.cardLayoutPanel, INTRO_SCREEN);
        });


        this.cardLayoutPanel.add(introScreen, INTRO_SCREEN);
        this.cardLayoutPanel.add(this.characterSelectionScreen, CHARACTER_SELECTION_SCREEN);
        this.cardLayoutPanel.add(createCharacterScreen, CREATE_CHARACTER_SCREEN);
        this.cardLayoutPanel.add(this.characterScreen, CHARACTER_SCREEN);

        this.cardLayoutPanel.add(this.errorScreen, ERROR_SCREEN);


        this.add(this.cardLayoutPanel);
        this.setVisible(true);

    }
    
    public Game getGame() {
        return this.game;
    }

    public void errorSequence(String errorMessage, String fallbackScreen) {
        
        // boolean hasErrorScreen = Arrays.stream(cardLayoutPanel.getComponents()).anyMatch(c -> c == errorScreen);   
        // System.out.println("Error screen added to card layout? " + hasErrorScreen);

        System.out.println("Error Sequence");
        this.errorScreen.setErrorLabel(errorMessage);
        System.out.println("Switching to: " + ERROR_SCREEN);
        this.cardLayout.show(this.cardLayoutPanel, ERROR_SCREEN);

        Timer timer = new Timer(3000, (ActionEvent e) -> {
            System.out.println("Switching to: " + fallbackScreen);
            this.cardLayout.show(this.cardLayoutPanel, fallbackScreen);
        });
        
        timer.setRepeats(false);
        timer.start();

    }


    public static void main(String[] args) {
        
        Gui gui = new Gui();
        

    }


}
