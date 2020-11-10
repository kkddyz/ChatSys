package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;


public class ReadMessages {
    private Database database;

    public ReadMessages() {
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    // Ask for a number n
    public int getChatMessageId() {
        Scanner in = new Scanner(System.in);
        System.out.println("read recent chatMessage,need entry n");
        return in.nextInt();
    }

    // Read the message database and display the n most recent messages.
    public void readRecentMessage(int n) throws IOException, ParseException {
        List<ChatMessage> chatMessages = database.readMessages();
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.getId() > n) {
                System.out.println(chatMessage.getUsername() + ": " + chatMessage.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ReadMessages read_client = new ReadMessages();
        read_client.setDatabase(new Database());
        int n = read_client.getChatMessageId();

        try {
            read_client.readRecentMessage(n);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
