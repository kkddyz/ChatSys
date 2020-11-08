package programming3.chatsys.data;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class DatabaseTest {

    @After // invoke after each test()
    public void clean() throws Exception {
        File file = new File("src\\test\\resources\\messages_test.txt");

        if (file.exists()){
            boolean deleted = file.delete();
            if (!deleted){
                throw new Exception("文件删除失败");
            }
        }
    }
    //  Use a BufferedReader to read each line of text and parse them using the parse method of ChatMessage.
    @Test
    public void readMessage() {
        File testFile = new File("src\\test\\resources\\messages_test.txt");
        Database database = new Database(testFile);
        try {
            List<ChatMessage> chatMessages = database.readMessages();
            for (ChatMessage chatMessage : chatMessages) {
                System.out.println(chatMessage.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // need improvement : test more message
    @Test
    public void addMessage() {
        ChatMessage validChatMessage = new ChatMessage(1,"abcy",new Timestamp(System.currentTimeMillis()),"你好 ！");
        ChatMessage validChatMessage1 = new ChatMessage(1,"abcy",new Timestamp(System.currentTimeMillis()),"你好 !");
        ChatMessage invalidChatMessage = new ChatMessage(1,"abcy$",new Timestamp(System.currentTimeMillis()),"你好 ！");
        ChatMessage invalidChatMessage1 = new ChatMessage(1,"abcy",new Timestamp(System.currentTimeMillis()),"你好 !");

        Database database = new Database(new File("src\\main\\resources\\database_file.txt"));
        try {
            database.addMessage(validChatMessage);
            database.addMessage(validChatMessage1);
            database.addMessage(invalidChatMessage);
            database.addMessage(invalidChatMessage1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}