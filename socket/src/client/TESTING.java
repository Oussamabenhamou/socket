package client;
import client.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TESTING {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\cli\\Desktop\\logo.png");
        BufferedImage image = ImageIO.read(file);
        User U = new User("ibrahim@gmail.com","ibrahim");
        Message message = new Message(MessageType.IMAGE, U, null, new MsgImage(image));
        message.afficherMessage();

        Message message2 = new Message(MessageType.FILE, U, null, new MsgFile(file));
        message2.afficherMessage();

        Message message1 = new Message(MessageType.TEXT, U, null, new MsgText("TEST"));
        message1.afficherMessage();
    }
}
