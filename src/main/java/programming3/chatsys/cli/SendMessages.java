package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Scanner;

/**
 * as send_client
 * createChatMessage() 生产消息
 * send() 发送消息
 */
public class SendMessages {
    int lastReadID = 0;
    Database database = new Database();  //CLI使用默认的database 默认database使用默认的DBfile

    public void setDatabase(Database database) {
        this.database = database;
    }

    //
    protected boolean databaseEmpty() throws IOException, ParseException {
        return database.isEmpty();
    }

    protected int getLastReadID() {
        return database.getLastMessageID();
    }

    protected String getMessage() {
        System.out.println("entry text please.");
        Scanner in = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals(""))
                break;
            stringBuilder.append(line + "\\r\\n");
            lastReadID++;
        }
        return stringBuilder.toString();
    }

    protected void send(ChatMessage chatMessage) throws Exception {
        database.addMessage(chatMessage);
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------send message cli-----------------------------------");
        System.out.println();

        // Ask for a username
        System.out.println("entry your username");
        String username = in.nextLine();

        // createMessage
        try {
            if (!databaseEmpty()) {
                lastReadID = getLastReadID();
                String message = getMessage();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                ChatMessage chatMessage = new ChatMessage(lastReadID, username, message, timestamp);
                send(chatMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SendMessages().run();
    }
}
