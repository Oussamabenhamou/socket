package client.model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class represents a list of contacts. It allows adding, removing, and managing contacts,
 * as well as saving them to and loading them from a file.
 */
public class Contact {
    private ArrayList<User> contacts;
    private String fileName = "contact.txt";

    /**
     * Constructs a new Contact object with an empty list of contacts.
     */
    public Contact() {
        contacts = new ArrayList<>();
    }

    /**
     * Adds a new contact to the list and updates the contacts file.
     * @param user The user to add as a contact.
     */
    public void addContact(User user) {
        contacts.add(user);
        uploadContactsToFile();
    }

    /**
     * Removes a contact from the list and updates the contacts file.
     * @param user The user to remove from contacts.
     */
    public void removeContact(User user) {
        contacts.remove(user);
        uploadContactsToFile();
    }

    /**
     * Uploads the current list of contacts to the contacts file.
     */
    private void uploadContactsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (User user : contacts) {
                writer.println(user.getEmail() + "," + user.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads contacts from the contacts file into the list.
     */
    public void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String email = parts[0];
                    String username = parts[1];
                    contacts.add(new User(email, username));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
