package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/**
 * as send_client
 * createChatMessage() 生产消息
 * send() 发送消息
 */
public class SendMessages {
    Database database;
    int lastId = 0;

    public void setDatabase(Database database) {
        this.database = database;
    }

    // Ask for a username
    private String getUsername() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("entry your username");
        return scanner.next();
    }

    // Check if the database exists and is not empty and get lastId
    private int getLastId() {

        try {
            List<ChatMessage> chatMessages = database.readMessages();
            if (!chatMessages.isEmpty()) {
                for (ChatMessage chatMessage : chatMessages) {
                    if (chatMessage.getId() > lastId) {
                        lastId = chatMessage.getId();
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    private String getMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("entry text please.");
        return scanner.nextLine();
    }

    public ChatMessage createChatMessage() {
        lastId = getLastId();
        lastId++;
        return new ChatMessage(lastId, getUsername(), new Timestamp(System.currentTimeMillis()), getMessage());
    }

    public void send(ChatMessage chatMessage) throws Exception {
        database.addMessage(chatMessage);
    }

    public static void main(String[] args) {
        SendMessages send_client = new SendMessages();
        send_client.setDatabase(new Database());

        // Create a ChatMessage using the incremented last id as id,
        // the current time for timestamp and the username of the authenticated user.

        ChatMessage chatMessage = send_client.createChatMessage();

        //Save it in the message database.

        try {
            send_client.send(chatMessage);
        } catch (Exception e) {
            System.out.println("send failed");
            e.printStackTrace();
        }
    }
}
