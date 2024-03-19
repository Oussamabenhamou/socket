package TESTING_EX;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MsgFile {
    public void afficherMessage() {
        Scanner scanner = new Scanner(System.in);
        // Prompt user for file path
        System.out.print("Enter the path to the file: ");
        String filePath = scanner.nextLine().trim();

        // Check if file exists
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File not found or invalid path.");
            return;
        }

        // Prompt user to choose whether to download the file
        System.out.print("Do you want to download the file? (Y/N): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            System.out.println("Download...");
            SwingUtilities.invokeLater(() -> {
                downloadFileAsync(file);
            });
        } else {
            System.out.println("File not downloaded.");
        }
    }

    private static void downloadFileAsync(File file) {
        System.out.println(file.getName());
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Create a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose download location");
                fileChooser.setSelectedFile(new File(file.getName())); // Set default selected file

                // Get the parent JFrame
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(fileChooser);

                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file path
                    File selectedFile = fileChooser.getSelectedFile();

                    try {
                        // Read the file into bytes
                        byte[] fileBytes = Files.readAllBytes(file.toPath());

                        // Save the file to the selected location
                        Path downloadPath = Paths.get(selectedFile.getAbsolutePath());
                        Files.write(downloadPath, fileBytes);

                        System.out.println("File downloaded successfully as: " + selectedFile.getName());
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Download canceled.");
                }
                return null;
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
//        File file = new File("C:\\Users\\cli\\Desktop\\Nouveau document texte.txt");
//        SwingUtilities.invokeLater(() -> {
//            downloadFileAsync(file);
//        });
        MsgFile file = new MsgFile();
        file.afficherMessage();
    }
}
