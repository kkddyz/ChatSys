package programming3.chatsys.data;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Create a test class in order to write unit tests for ChatMessage: ChatMessageTest.
 * Create a test method for the format and parse methods.
 */
public class ChatMessageTest {

    //  After your test are executed, don’t forget to remove the temporary file.
    //  You can do this by defining a method “clean” inside your test class and use the annotations @AfterAll or @AfterEach.

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

    @org.junit.Test
    public void format() {
    }

    @org.junit.Test
    public void parse() {
    }

    @Test
    public void save() {
        File testFile = new File("src\\test\\resources\\messages_test.txt");
        System.out.println(testFile.getAbsolutePath());
        ChatMessage chatMessage = new ChatMessage(1,"user1",new Timestamp(System.currentTimeMillis()),"hello!");
        try {
            // assertTrue : apply for method return boolean
            assertTrue(chatMessage.save(testFile));
        } catch (IOException e) {
            assert false;
        }
    }
}