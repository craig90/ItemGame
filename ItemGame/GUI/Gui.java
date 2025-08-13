import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gui extends JFrame {

    public static final String INTRO_SCREEN = "Intro Screen";
    public static final String CHARACTER_SELECTION_SCREEN = "Character Selection Screen";
    public static final String CHARACTER_CREATION_SCREEN = "Create Character Screen";
    public static final String CHARACTER_SCREEN = "Character Screen";
    public static final String ERROR_SCREEN = "Error Screen";

    public static final String INVENTORY_PANEL_NAME = "Inventory Panel";
    public static final String ITEM_PANEL_NAME = "Item Layered Panel"; 

    public CardLayout cardLayout;
    public JPanel cardLayoutPanel;

    private ErrorScreen errorScreen;
    protected CharacterScreen characterScreen;
    protected CharacterSelectionScreen characterSelectionScreen;
    private CharacterCreationScreen characterCreationScreen;
    private IntroScreen introScreen;

    private Game game;

    public Gui () {

        this.game = new Game();

        // Create the main frame
        this.setTitle("Item Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 750);

        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(this.cardLayout);
        

        this.introScreen = new IntroScreen(this);
        this.characterSelectionScreen = new CharacterSelectionScreen(this);
        this.characterScreen = new CharacterScreen(this);
        this.characterCreationScreen = new CharacterCreationScreen(this);
        this.errorScreen = new ErrorScreen();


    }
    
    /**
     * Mostly created this to avoid the IDE warning.. not really necessary for project of this size.. all could be
     * done in the constructor
     */
    public void init() {

        this.cardLayoutPanel.add(this.introScreen, INTRO_SCREEN);
        this.cardLayoutPanel.add(this.characterSelectionScreen, CHARACTER_SELECTION_SCREEN);
        this.cardLayoutPanel.add(this.characterCreationScreen, CHARACTER_CREATION_SCREEN);
        this.cardLayoutPanel.add(this.characterScreen, CHARACTER_SCREEN);

        this.cardLayoutPanel.add(this.errorScreen, ERROR_SCREEN);

        this.add(this.cardLayoutPanel);
        this.setVisible(true);

    }


    public Game getGame() {
        return this.game;
    }

    public void errorSequence(String errorMessage, String fallbackScreen) {
        
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
        gui.init();

    }


}
