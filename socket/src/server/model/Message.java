package server.model;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType messageType;
    private User sender;
    private User receiver;
    private MessageContent messageContent;


    // Constructor
    public Message(MessageType messageType, User sender, User receiver, MessageContent messageContent) {
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        switch (messageType) {
            case FILE -> this.messageContent = (MsgFile) messageContent;
            case TEXT -> this.messageContent = (MsgText) messageContent;
            case IMAGE -> this.messageContent = (MsgImage) messageContent;
            case CMD -> this.messageContent =(MsgCMD) messageContent;
        }
    }

    // Getters and setters
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void afficherMessage(){
        System.out.print(sender.getUsername()+":");
        messageContent.afficherMessage();
    }
}

