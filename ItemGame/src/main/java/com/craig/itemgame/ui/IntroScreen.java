package com.craig.itemgame.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroScreen extends JPanel {

    Gui gui;

    public IntroScreen(Gui gui) {

        this.gui = gui;

        JLabel welcomeLabel = new JLabel("Welcome to the Item Game");

        JButton createCharButton = new JButton("Create New Character");
        JButton loadCharButton = new JButton("Load Character From File");

        this.add(welcomeLabel);
        this.add(createCharButton);
        this.add(loadCharButton);

        loadCharButton.addActionListener(e -> {
            if (this.gui.characterSelectionScreen.update() == true) {
                System.out.println("Switching to: " + Gui.CHARACTER_SELECTION_SCREEN);
                this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.CHARACTER_SELECTION_SCREEN);
            }
        });

        createCharButton.addActionListener(e -> {
            System.out.println("Switching to: " + Gui.CHARACTER_CREATION_SCREEN);
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.CHARACTER_CREATION_SCREEN);
        });

    }

}
