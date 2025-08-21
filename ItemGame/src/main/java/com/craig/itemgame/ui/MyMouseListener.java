package com.craig.itemgame.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class MyMouseListener extends MouseAdapter {

    ItemPanel itemPanel;
    HoverPanel hoverPanel;

    public MyMouseListener(ItemPanel itemPanel) {
        this.itemPanel = itemPanel;
        this.hoverPanel = itemPanel.getHoverPanel();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel hoveredLabel = (JLabel) e.getSource();
        // System.out.println("Hover over: " + hoveredLabel.getText());
        this.hoverPanel.updateHoverWindow(hoveredLabel);
        this.hoverPanel.showHoverPanel();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.hoverPanel.hideHoverPanel();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.hoverPanel.hideHoverPanel();
    }

}