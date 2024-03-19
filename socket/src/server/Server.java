package server;
import client.model.Message;
import client.model.MsgText;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    //    arraylist with all the connections in ity
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;

    //    need to define a thread pool that can be reused instead of always having to create new threads
    private ExecutorService pool;


    public Server() {
        connections = new ArrayList<>();

//        initialise boolean value for whether server is running or not
        done = false;
    }

    @Override
    public void run() {

//        server will constantly listen for incoming connections and then accept them

        try {
            server = new ServerSocket(9999);

            pool = Executors.newCachedThreadPool();

            while(!done) {
                //            server has the accept method which - returns a client socket
                Socket client = server.accept();

                //            ConnectionHandler is a class which connects to the server, queues and sends messages to server.
                //            ConnectionHandler also receives messages from server and passes them to clients running on client machine.
                //            need a function to send something from the server to the client - sendMessage function below
                ConnectionHandler handler = new ConnectionHandler(client);

                connections.add(handler);

//                run the executor - runs the run function
                pool.execute(handler);

            }
        } catch (IOException e) {
            try {
                shutdown();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    //        need a broadcast function to broadcast messages to all clients
    public void broadcastMessage(String message) {
        for (ConnectionHandler ch: connections) {
            try {
                ch.sendMessage(message);
            }catch (IOException e){

            }
        }
    }

    public void shutdown() throws IOException {
        done = true;
        pool.shutdown();
        if(!server.isClosed()) {
            server.close();
        }
        for(ConnectionHandler ch: connections){
            ch.shutdown();
//            shut down each connection
        }
    }



    class ConnectionHandler implements Runnable{

        private Socket client;

        //        Bufferedreader to get the stream from the socket
        private ObjectInputStream in;

        //        Printwriter when writing to the client
        private ObjectOutputStream out;

        private String nickname;


        //        create constructor which takes in the socket as a parameter
        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            //        new connection handler for each client that connects

//            initialise reader and writer
            try {
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());

//                now we can send something to the client
                out.writeObject("Please enter your email: ");
                    //todo construct a message object
                    Object receivedObject ;
                while ((receivedObject = in.readObject()) != null) {
                    System.out.println("object received");
                    Message MSG = (Message) receivedObject;

                    MSG.afficherMessage();
                }
//                nickname = ((MsgText)MSG.getMessageContent()).getText();

//                can perform checks on the nickname entered here ...

//                System.out.println(nickname + " connected!");
//                broadcastMessage(nickname + " connected...");

//              broadcast this to all the other clients who are connected

//                message will be null by default
//                String message;
//                while((message = in.readLine()) !=null) {
//                    if(message.startsWith("/nick ")) {
////                        Handle nickname change
//                        String[] messageSplit = message.split(" ", 2);
//                        if (messageSplit.length ==2) {
//                            broadcastMessage(nickname + " changed their handle to " + messageSplit[1]);
//                            System.out.println(nickname + " changed their handle to " + messageSplit[1]);
//                            nickname = messageSplit[1];
//                            out.writeObject("Successfully changed nickname to " + nickname);
//                        } else{
//                            out.writeObject("No nickname provided!");
//                        }
//                    } else if (message.startsWith("/quit")) {
//                        broadcastMessage(nickname + " left the chat.");
//                        shutdown();
//                    } else {
//                        broadcastMessage(nickname + ": " + message);
//                    }
//                }


            } catch (IOException e) {
                try {
                    shutdown();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public void shutdown() throws IOException {
//            firstly close the streams
            in.close();
            out.close();

            if(!client.isClosed()){
                client.close();
            }
        }

        public void sendMessage(String message) throws IOException{
            out.writeObject(message);
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}