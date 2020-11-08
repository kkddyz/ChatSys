package programming3.chatsys.data;

import java.sql.Timestamp;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

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
            List<ChatMessage> chatMessages = database.readMessage();
            for (ChatMessage chatMessage : chatMessages) {
                System.out.println(chatMessage.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}