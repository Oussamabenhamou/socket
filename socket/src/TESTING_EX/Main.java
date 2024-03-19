package TESTING_EX;

import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
        Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        File file = new File("C:\\Users\\cli\\Documents\\JAVA_INTELJI_PROJECT\\socket\\src\\TESTING_EX\\simpleFile");
        System.out.println(file.exists());
        System.out.println(file.getName());

       client.model.MsgFile msgFile = new client.model.MsgFile(file);
       msgFile.afficherMessage();

//        File file = new File("C:\\Users\\cli\\Documents\\JAVA_INTELJI_PROJECT\\socket\\src\\TESTING_EX\\simpleFile"); // Replace this with the path to your file
//        FileHandler.readAndPrintFile(file);
    }
}

class FileHandler {
    public static void readAndPrintFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}