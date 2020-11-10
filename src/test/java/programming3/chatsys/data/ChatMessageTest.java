package programming3.chatsys.data;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Create a test class in order to write unit tests for ChatMessage: ChatMessageTest.
 * Create a test method for the format and parse methods.
 */
public class ChatMessageTest {

    //  After your test are executed, don’t forget to remove the temporary file.
    //  You can do this by defining a method “clean” inside your test class and use the annotations @AfterAll or @AfterEach.

    // format含有'\n'的username
    @Ignore
    @org.junit.Test
    public void format() {
        String message = "你好\nhello";
        ChatMessage chatMessage = new ChatMessage(100, "fff", message, new Timestamp(System.currentTimeMillis()));
        // because '\n' we can only read "你好" and hello will left to timestamp
        try {
            chatMessage.setTimestamp(new Timestamp(Long.parseLong("hello")));
            chatMessage.formatTimestamp();
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    // parse含有'\n'的id
    @Ignore
    @org.junit.Test
    public void parse() {

        String id = "9\n";
        ChatMessage chatMessage = new ChatMessage();
        try {
            chatMessage.parseId(id);
        } catch (NumberFormatException e) {
            System.out.println("parse failed");
            assert false;
        }


    }

    @Test
    public void save() {
        File testFile = new File("src\\test\\resources\\test_unreadMessages_DB_message_file.txt");

        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(1, "user1", "hello1!", new Timestamp(System.currentTimeMillis())));
        chatMessages.add(new ChatMessage(2, "user2", "hello2!", new Timestamp(System.currentTimeMillis())));
        chatMessages.add(new ChatMessage(3, "user2", "hello3!", new Timestamp(System.currentTimeMillis())));
        chatMessages.add(new ChatMessage(4, "user2", "hello4!", new Timestamp(System.currentTimeMillis())));
        chatMessages.add(new ChatMessage(5, "user2", "hello5!", new Timestamp(System.currentTimeMillis())));
        chatMessages.add(new ChatMessage(6, "user3", "hello6!", new Timestamp(System.currentTimeMillis())));
        chatMessages.add(new ChatMessage(7, "user3", "hello7!", new Timestamp(System.currentTimeMillis())));
        try {
            for (ChatMessage chatMessage : chatMessages) {
                chatMessage.save(testFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
    }
}