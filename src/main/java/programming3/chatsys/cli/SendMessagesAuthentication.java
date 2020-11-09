package programming3.chatsys.cli;

import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SendMessagesAuthentication extends SendMessages {

    // check账密
    protected boolean isValidToken(String username, String userPassword) throws Exception {
        Map<String, User> userMap = database.readUsers();
        Set<String> users = userMap.keySet();
        User user = userMap.get(username);
        if (user!=null){
            String password = user.getPassword();
            return password.equals(userPassword);
        }else {
            return false;
        }


    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("--------------------send message cli-----------------------------------");
        System.out.println();

        // Ask for a username
        System.out.println("entry your username");
        String username = in.nextLine();

        System.out.println("entry your password");
        String password = in.nextLine();

        try {
            if (isValidToken(username, password)) {
                if (!databaseEmpty()) {
                    lastReadID = getLastReadID();
                    String message = getMessage();
                    Timestamp t = new Timestamp(System.currentTimeMillis());
                    ChatMessage chatMessage = new ChatMessage(lastReadID, username, message, t);
                    send(chatMessage);
                }
            }else {
                System.out.println("invalid token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SendMessagesAuthentication().run();
    }
}
