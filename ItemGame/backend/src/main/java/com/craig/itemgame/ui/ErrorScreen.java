package com.craig.itemgame.ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public final class ErrorScreen extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel errorLabel;

    public ErrorScreen() {
        
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        this.add(errorLabel);

    }

    public void setErrorLabel(String errorMessage) {
        this.errorLabel.setText(errorMessage);
    }


}
