package client;
//Uses two threads: 1 to receive messages from server, 2 takes in console line input
import client.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    private User me = new User("ibrahim","ibrahim");
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean done;

    @Override
    public void run() {
        try {
            // Connect to the server
            client = new Socket("127.0.0.1", 9999);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            Object receivedObject;
            while ((receivedObject = in.readObject()) != null) {
                // Handle received object
                if (receivedObject instanceof String) {
                    // If the received object is a String
                    String inMessage = (String) receivedObject;
                    System.out.println(inMessage);
                } else if (receivedObject instanceof Message) {
                    // If the received object is of a Message class
                    Message message = (Message) receivedObject;
                    message.afficherMessage();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            shutdown();
        }
    }

    // shutdown method
    public void shutdown(){
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
        } catch(IOException e){
//                ignore exception here...
        }

    }



    class InputHandler implements Runnable {
//                    constantly ask for new input line

        @Override
        public void run() {
//                accepts command line input
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            while (!done) {
                try {
//                    String input = inReader.readLine();
//                    Message message = new Message(MessageType.TEXT,me,null,new MsgText(input));
//                    if(input.equals("/quit:")) {
//                        inReader.close();
////                            call shutdown function
//                        shutdown();
//                    } else{
//                        out.writeObject(message);
//                        out.flush();
//                    }
                    sendMessages(out);

                } catch (IOException e) {
                    shutdown();
                }
            }
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void sendMessage(ObjectOutputStream out, Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    private void sendImageMessage(ObjectOutputStream out) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BufferedImage image = ImageIO.read(selectedFile);
            System.out.println(fileChooser.getSelectedFile().getName());
            sendMessage(out, new Message(MessageType.IMAGE, me, null, new MsgImage(image)));
            System.out.println("Image sent successfully.");
        } else {
            System.out.println("No image file selected.");
        }
    }




    private void sendFileMessage(ObjectOutputStream out) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            sendMessage(out, new Message(MessageType.FILE, me, null, new MsgFile(selectedFile)));
            System.out.println("File sent successfully.");
        } else {
            System.out.println("No file selected.");
        }
    }
    private void sendMessages(ObjectOutputStream out) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter your message (/quit to exit): ");
                String input = scanner.nextLine();
                if ("/quit".equalsIgnoreCase(input)) {
                    break;
                } else if ("/sendimage".equalsIgnoreCase(input)) {
                    sendImageMessage(out);
                } else if ("/sendfile".equalsIgnoreCase(input)) {
                    sendFileMessage(out);
                } else {
                    sendMessage(out, new Message(MessageType.TEXT, me, null, new MsgText(input)));
                }
            }
        }
    }

}

