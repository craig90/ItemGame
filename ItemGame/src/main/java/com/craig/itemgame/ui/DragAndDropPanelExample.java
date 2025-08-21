package com.craig.itemgame.ui;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragAndDropPanelExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drag and Drop Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        JPanel sourcePanel = new JPanel();
        sourcePanel.setBorder(BorderFactory.createTitledBorder("Source Panel"));
        JLabel draggableLabel = new JLabel("Drag Me!");
        draggableLabel.setTransferHandler(new MyLabelTransferHandler());
        draggableLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, DnDConstants.ACTION_MOVE);
            }
        });
        sourcePanel.add(draggableLabel);

        JPanel targetPanel = new JPanel();
        targetPanel.setBorder(BorderFactory.createTitledBorder("Target Panel"));
        targetPanel.setTransferHandler(new MyPanelTransferHandler());

        frame.add(sourcePanel);
        frame.add(targetPanel);
        frame.setVisible(true);
    }

    static class MyLabelTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            JLabel label = (JLabel) c;
            return new StringSelection(label.getText());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action == DnDConstants.ACTION_MOVE) {
                // Optionally remove the label from the source panel after a successful move
                JPanel parentPanel = (JPanel)source.getParent();
                source.getParent().remove(source);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        }
    }

    static class MyPanelTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                System.out.println("Test");
                return false;
            }
            try {
                String data = (String) support.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
                JPanel targetPanel = (JPanel) support.getComponent();
                targetPanel.add(new JLabel(data + " (dropped)"));
                targetPanel.revalidate();
                targetPanel.repaint();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}