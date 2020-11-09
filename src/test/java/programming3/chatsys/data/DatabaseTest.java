package programming3.chatsys.data;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.jar.JarOutputStream;

public class DatabaseTest {

    @AfterClass // invoke after all test finished
    public static void clean() {
        File test_messages = new File("src\\test\\resources\\messages_test.txt");
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
        File testFile = new File("src\\test\\resources\\messages_test.txt");
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
}