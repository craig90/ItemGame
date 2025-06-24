import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MyMouseListener extends MouseAdapter {

    ItemWindow itemWindow;
    JPanel hoverPanel;

    public MyMouseListener(ItemWindow itemWindow, JPanel hoverPanel) {
        this.itemWindow = itemWindow;
        this.hoverPanel = hoverPanel;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel hoveredLabel = (JLabel) e.getSource();
        // System.out.println("Hover over: " + hoveredLabel.getText());
        this.itemWindow.updateHoverWindow(hoveredLabel);
        this.hoverPanel.setVisible(true);
        this.hoverPanel.revalidate(); // Revalidate the layout to ensure it's displayed correctly
        this.hoverPanel.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.hoverPanel.setVisible(false);
        this.hoverPanel.revalidate();
        this.hoverPanel.repaint();
    }

}