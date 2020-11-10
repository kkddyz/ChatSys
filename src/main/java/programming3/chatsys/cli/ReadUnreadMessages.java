package programming3.chatsys.cli;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import programming3.chatsys.data.Database;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class ReadUnreadMessages {
    private Database database = new Database();
    public void unreadMessages(String username) throws IOException, ParseException {
        database.getUnreadMessage(username);
    }

    public void run() throws IOException, ParseException {
        Scanner in = new Scanner(System.in);
        System.out.println("entry your username");
        String username = in.nextLine();
        unreadMessages(username);

    }
    public static void main(String[] args) {
        try {
            new ReadUnreadMessages().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


