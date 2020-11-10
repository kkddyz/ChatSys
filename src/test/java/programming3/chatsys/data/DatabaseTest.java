package programming3.chatsys.data;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.jar.JarOutputStream;

public class DatabaseTest {

    @AfterClass // invoke after all test finished
    public static void clean() {
        File test_messages = new File("src\\test\\resources\\message_test.txt");
        if (test_messages.exists()){
            test_messages.delete();
        }
        File test_users = new File("src\\test\\resources\\users_test.txt");
        if (test_users.exists()){
            test_users.delete();
        }
    }
    //  Use a BufferedReader to read each line of text and parse them using the parse method of ChatMessage.
    @Test
    public void readMessage() {
        File testFile = new File("src\\test\\resources\\test_unreadMessages_DB_message_file.txt");
        Database database = new Database();database.setDB_messages_file(testFile);
        try {
            List<ChatMessage> chatMessages = database.readMessages();
            for (ChatMessage chatMessage : chatMessages) {
                System.out.println(chatMessage.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    public void readUser() {
        File testFile = new File("src\\test\\resources\\users_test.txt");
        Database database = new Database();
        database.setDB_users_file(testFile);
        try {
            Map<String, User> usersMap = database.readUsers();
            for (String s : usersMap.keySet()) {
                User user = usersMap.get(s);
                System.out.println(s+"="+user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    private void writeUser() throws IOException {
        File file = new File("src\\test\\resources\\test_unreadMessages_DB_user_file.txt");
        file.createNewFile();
        FileWriter writer= new FileWriter(file,true);
        writer.write("user1\n");
        writer.write("zcc\n");
        writer.write("123\n");
        writer.write("0\n");
        writer.write("user2\n");
        writer.write("qqq\n");
        writer.write("123\n");
        writer.write("2\n");
        writer.close();
    }
    @Test
    public void getUnreadMessage() throws IOException, ParseException {

        writeUser();
        File testUserFile = new File("src\\test\\resources\\test_unreadMessages_DB_user_file.txt");
        File testMessageFile = new File("src\\test\\resources\\test_unreadMessages_DB_message_file.txt");


        Database database = new Database(testMessageFile,testUserFile);
        System.out.println("print user1 unread");
        List<ChatMessage> user1 = database.getUnreadMessage("user1");
        for (ChatMessage chatMessage : user1) {
            System.out.println(chatMessage);
        }

        System.out.println("print user2 unread");
        List<ChatMessage> user2 = database.getUnreadMessage("user2");
        for (ChatMessage chatMessage : user2) {
            System.out.println(chatMessage);
        }


        testUserFile.delete();

    }
}