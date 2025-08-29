package com.craig.itemgame.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.craig.itemgame.controller.GameController;

public class CharacterCreationScreen extends JPanel {

    Gui gui;
    private GameController gameController;

    public CharacterCreationScreen(Gui gui, GameController gameController) {

        this.gui = gui;
        this.gameController = gameController;


        JLabel createCharLabel = new JLabel("Create a New Character");
        JLabel enterCharNameLabel = new JLabel("Enter a character name");
        JTextField charNameTextField = new JTextField(20);
        JButton submitCharNameButton = new JButton("Submit");
        JButton backToHomeScreenButtonTwo = new JButton("Return to Home Screen");
        this.add(createCharLabel);
        this.add(enterCharNameLabel);
        this.add(charNameTextField);
        this.add(submitCharNameButton);
        this.add(backToHomeScreenButtonTwo);


        submitCharNameButton.addActionListener(e -> {
            
            if (!this.gameController.createCharacter(charNameTextField.getText())) {
                this.gui.errorSequence("Name must be at least three characters", Gui.CHARACTER_CREATION_SCREEN);
                return;
            }

            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.CHARACTER_SCREEN);
        
        });

        backToHomeScreenButtonTwo.addActionListener(e -> {
            System.out.println("Switching to: " + Gui.INTRO_SCREEN);
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);
        });


    }

}
