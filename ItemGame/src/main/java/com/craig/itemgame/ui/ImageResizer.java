package com.craig.itemgame.ui;

import javax.swing.ImageIcon;
import java.awt.Image;

public class ImageResizer {

    /**
     * Resizes an ImageIcon to the specified width and height.
     *
     * @param icon The original ImageIcon to resize.
     * @param newWidth The desired new width.
     * @param newHeight The desired new height.
     * @return A new ImageIcon with the specified dimensions.
     */
    public static ImageIcon resizeImageIcon(ImageIcon icon, int newWidth, int newHeight) {
        if (icon == null) {
            return null;
        }

        Image image = icon.getImage();
        // Scale the image using getScaledInstance for simplicity and quality options
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Create a BufferedImage to ensure proper rendering and avoid issues with some platforms
        /*BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();*/

        return new ImageIcon(scaledImage);
    }


}
