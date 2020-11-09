package programming3.chatsys.data;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void formatLastReadID() {
        User user = new User("user1","user1FullName","111222",10);
        assertEquals("10",user.formatLastReadID());
    }

    @Test
    public void parseLastReadID() {
        User user = new User("user1","user1FullName","111222",10);
        assertEquals(10,user.parseLastReadID(user.formatLastReadID()));
    }

    @Test
    public void save() {
        File testFile = new File("src\\test\\resources\\users_test.txt");

        User user = new User("user1","user1FullName","111222",10);
        try {
            user.save(testFile);
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }
    }
}