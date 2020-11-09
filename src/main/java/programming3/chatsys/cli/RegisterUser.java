package programming3.chatsys.cli;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.User;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Now create a class, named RegisterUser (in programming3.chatsys.cli), with a static main method.
 * It should do the following:
 * •
 * • If the username is in the database, exit with an error message
 * • If the username is not in the database, save the information in the database
 * Now create a class SendMessagesAuthentication. This class is similar to SendMessages but adds a few
 * operations (the text in bold represents the operations that are new in comparison with SendMessages):
 * • Ask for a username and password.
 * • Reads the user database and search for the username.
 * • If the username it is not in the database or the password doesn’t match, return an error message and
 * exit
 * • Check if the database exists and is not empty.
 * • If it exists and isn’t empty, read all the messages and find the chat message with the biggest id and save
 * that id as the “last id”.
 * • If the DB doesn’t exist or is empty, save 0 as the last id.
 * • Then, use a Scanner in Java to ask the user to enter text.
 * • For each line of text entered, increment the last id by 1. Create a ChatMessage using the incremented
 * last id as id, the current time for timestamp and the username of the authenticated user. Save it in the
 * message database.
 * Note: asking for the number of messages to read (for ReadMessages) or the user information (for
 * SendMessagesAuthentication and RegisterUser) can be done either with a Scanner or using the command line
 * arguments.
 */
public class RegisterUser {
    private Database database = new Database();

    private Set<String> getUserSet() throws IOException {
        return database.readUsers().keySet();
    }
    public boolean userExists(String userName) throws IOException {
        Set<String> users = getUserSet();
        return users.contains(userName);
    }

    public void saveUser(User user) throws IOException {
        database.addUsr(user);
    }

    public void register(){
        System.out.println("--------------------register user cli-----------------------------------");
        System.out.println();

        // ask for a username, full name and password
        Scanner in = new Scanner(System.in);
        System.out.println("entry you userName");
        String userName = in.nextLine();
        System.out.println("entry you fullName");
        String fullName = in.nextLine();
        System.out.println("entry you password");
        String password = in.nextLine();

        try {
            if (!userExists(userName)){
                User user = new User(userName,fullName,password,0);
                saveUser(user);
                System.out.println("save user");
            }else {
                System.err.println("username has exited");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        new RegisterUser().register();


    }
}
