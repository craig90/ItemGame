package com.craig.itemgame.ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.craig.itemgame.controller.GameController;
import com.craig.itemgame.model.Game;

public class Gui extends JFrame {

    private static final int WINDOW_WIDTH = 975;
    private static final int WINDOW_HEIGHT = 750;
    
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

    private GameController gameController;

    // private Game game;

    public Gui (GameController gameController) {

        this.gameController = gameController;

        // Create the main frame
        this.setTitle("Item Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        this.cardLayout = new CardLayout();
        this.cardLayoutPanel = new JPanel(this.cardLayout);
        
        // create the panels
        this.introScreen = new IntroScreen(this);
        this.characterSelectionScreen = new CharacterSelectionScreen(this, this.gameController);
        this.characterScreen = new CharacterScreen(this, this.gameController);
        this.characterCreationScreen = new CharacterCreationScreen(this, this.gameController);
        this.errorScreen = new ErrorScreen();

        // add the panels to the card layout
        this.cardLayoutPanel.add(this.introScreen, INTRO_SCREEN);
        this.cardLayoutPanel.add(this.characterSelectionScreen, CHARACTER_SELECTION_SCREEN);
        this.cardLayoutPanel.add(this.characterCreationScreen, CHARACTER_CREATION_SCREEN);
        this.cardLayoutPanel.add(this.characterScreen, CHARACTER_SCREEN);

        this.cardLayoutPanel.add(this.errorScreen, ERROR_SCREEN);

        this.add(this.cardLayoutPanel);
        this.setVisible(true);


        // set the views
        this.gameController.getCharacterController().setView(this.characterScreen, this.characterScreen.getItemPanel());

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

    /**
     * simply to get rid of IDE warning, it has no real purpose
     */
    public void init() {}

    public static void main(String[] args) {
        
        String directory = System.getProperty("user.dir");
        System.out.println("Current directory: " + directory);

        Game game = new Game();
        GameController gameController = new GameController(game);
        Gui gui = new Gui(gameController);

        gui.init();

    }


}
