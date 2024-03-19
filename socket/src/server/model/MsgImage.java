package server.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;


public class MsgImage implements MessageContent , Serializable {
    BufferedImage image;

    public MsgImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void afficherMessage() {
        Scanner scanner = new Scanner(System.in);
        // Prompt user to choose whether to display the image
        System.out.print("send an Image Do you want to display it? (Y/N): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            // Display the image
            SwingUtilities.invokeLater(() -> {
                displayImage(image);
            });

        } else {
            System.out.println("Image not displayed.");
        }
    }
    private static void displayImage(BufferedImage image) {
        // Create a JFrame to display the image
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JLabel to hold the image
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);

        // Pack and display the frame
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }
}
