import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Gui extends JFrame {

    public CardLayout cardLayout;
    public JPanel mainPanel;

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
            this.cardLayout.show(this.mainPanel, "Character Selection Screen");
        });

        createCharButton.addActionListener(e -> {
            this.cardLayout.show(this.mainPanel, "Create Character Screen");
        });


        this.errorScreen = new ErrorScreen();
         // TODO: should be recreated -- test what happens when there are no files found
        this.characterSelectionScreen = new CharacterSelectionScreen(this);
        this.characterScreen = new CharacterScreen(this);



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
        this.mainPanel.add(this.characterSelectionScreen, "Character Selection Screen");
        this.mainPanel.add(createCharacterScreen, "Create Character Screen");
        this.mainPanel.add(this.characterScreen, "Character Screen");

        this.mainPanel.add(this.errorScreen, "Error Screen");


        this.add(mainPanel);
        this.setVisible(true);

    }
    
    public Game getGame() {
        return this.game;
    }

    public void errorSequence(String errorMessage) {
        
        this.errorScreen.setErrorLabel(errorMessage);
        this.cardLayout.show(this.mainPanel, "Error Screen");

        Timer timer = new Timer(3000, (ActionEvent e) -> {
            this.errorScreen.setVisible(false);
            // frame.revalidate(); // Refresh layout
            // frame.repaint();
        });
        timer.start();

    }


    public static void main(String[] args) {
        
        Gui gui = new Gui();
        

    }


}
