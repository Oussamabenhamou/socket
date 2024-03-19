package server.model;

import java.io.Serializable;

public class MsgText implements MessageContent, Serializable {
    private String text;

    public MsgText(String text) {
        this.text = text;
    }

    @Override
    public void afficherMessage() {
        System.out.println(text);
    }
}
