/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wifipasswords;

/**
 *
 * @author dionysis
 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static javax.swing.JRootPane.PLAIN_DIALOG;

public class PanelToImageSaver {
    public static void savePanelAsImage(JPanel panel,JDialog dialog) {
       
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        System.out.println("width"+panel.getWidth()+" Height:"+panel.getHeight());
        // Get the Graphics object of the image
        Graphics2D g2d = image.createGraphics();

        // Manually render the panel onto the image
        panel.paint(g2d);

        // Dispose of the Graphics object
        g2d.dispose();

        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setDialogTitle("Save Image");
        


        // Set file filter to only allow saving as PNG
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        fileChooser.setFileFilter(filter);

        // Show the file chooser dialog
        int userSelection = fileChooser.showSaveDialog(panel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            // Add the ".png" extension if necessary
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            // Save the image to the selected file
            try {
                ImageIO.write(image, "png", new File(filePath));
                System.out.println("Panel saved as image successfully.");
                dialog.dispose();
            } catch (IOException e) {
                System.out.println("Error saving panel as image: " + e.getMessage());
            }
        }
    }
}
