package TESTING_EX;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageDisplay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the image file: ");
        String imagePath = scanner.nextLine();

        try {
            // Read the image from the specified file path
            File file = new File(imagePath);
            BufferedImage image = ImageIO.read(file);

            // Print image dimensions
            int width = image.getWidth();
            int height = image.getHeight();
            System.out.println("Image dimensions: " + width + "x" + height);

            // Print image name
            String imageName = file.getName();
            System.out.println("Image name: " + imageName);

            // Prompt user to choose whether to display the image
            System.out.print("Do you want to display the image? (Y/N): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                // Display the image
                displayImage(image);
            } else {
                System.out.println("Image not displayed.");
            }
        } catch (IOException e) {
            System.out.println("Error reading or displaying the image: " + e.getMessage());
        }
    }

    // Method to display the image
    private static void displayImage(BufferedImage image) {
        // Create a JFrame to display the image
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel to hold the image
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);

        // Pack and display the frame
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

}
