package programming3.chatsys.data;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    // For the time being, the class will have one attributes referencing the file for the message database

    private File DB_messages_file = new File("src\\main\\resources\\database_messages_file.txt");
    private File DB_users_file = new File("src\\main\\resources\\database_users_file.txt");

    private List<ChatMessage> messageList = new ArrayList<>(); // message容器
    private Map <String,User> userList = new HashMap<>(); // user容器

    // constructor && setter
    public Database(){}
    public Database(File DB_messages_file,File DB_users_file) {
        this.DB_messages_file = DB_messages_file;
        this.DB_users_file = DB_users_file;
    }

    public void setDB_messages_file(File DB_messages_file) {
        this.DB_messages_file = DB_messages_file;
    }

    public void setDB_users_file(File DB_users_file) {
        this.DB_users_file = DB_users_file;
    }

    //
    private void addMessage(ArrayList<String> temp) throws ParseException {
        ChatMessage chatMessage = new ChatMessage();
        int id = chatMessage.parseId(temp.get(0));
        chatMessage.setId(id);
        String username = temp.get(1);
        chatMessage.setUsername(username);
        String message = temp.get(2);
        chatMessage.setMessage(message);
        Timestamp timeStamp = chatMessage.parseTimestamp(temp.get(3));
        chatMessage.setTimestamp(timeStamp);
        messageList.add(chatMessage);
    }
    private void addUser(ArrayList<String> temp) {
        // init
        User user = new User();
        String username = temp.get(0); user.setUserName(username);
        String fullName = temp.get(1); user.setFullName(fullName);
        String password = temp.get(2); user.setPassWard(password);
        String lastReadId = temp.get(3); user.setLastReadID(user.parseLastReadID(lastReadId));

        // add
        userList.put(username,user);
    }

    public List<ChatMessage> readMessages() throws IOException, ParseException {
        // clean messageList
        messageList.clear();

        BufferedReader reader = new BufferedReader(new FileReader(DB_messages_file));
        ArrayList<String> temp = new ArrayList<>();
        String line ;

        // readLine() will keep on reading the next line from the file
        // and once it reaches the end of the file it returns null.

        while ((line=reader.readLine())!=null) {
            // according to line init messages and add to messageList
            if (temp.size()<4){
                temp.add(line);
            }else {
                addMessage(temp);
                temp.clear();
                temp.add(line);
            }
        }
        if (temp.size()==4){
            addMessage(temp);
            temp.clear();
        }

        reader.close();


        return messageList;
    }

    public Map<String,User> readUsers() throws IOException {
        userList.clear(); // 预处理

        BufferedReader reader = new BufferedReader(new FileReader(DB_users_file));
        ArrayList<String> temp = new ArrayList<>();
        String line;

        while ((line=reader.readLine())!=null){
            if (temp.size()<4){
                temp.add(line);
            }else {
                addUser(temp);
                temp.clear();
                temp.add(line);
            }
        }
        return userList;
    }



    /**
     * Add to the Database a method addMessage that takes a ChatMessage as argument and adds it to the database
     * file. The chat message added should have an id greater than all the ids of all the chat messages already in the
     * database. If it doesn’t, the method should throw an exception.
     */

    public void addMessage(ChatMessage chatMessage) throws Exception {
        // check message
        if (isValidId(chatMessage.getId()) && isValidMessage(chatMessage)){
            chatMessage.save(DB_messages_file);
        }else {
            throw new Exception(chatMessage+" is not valid ");
        }

    }



    /**
     * 从数据库读取信息判断id是否合理
     */
    private boolean isValidId(int id) throws IOException, ParseException {

        messageList = readMessages();
        for (ChatMessage message : messageList) {
            if(message.getId()>id)
                return false;
        }
        return true;

    }

    /**
     * 判断输入内容是否是合法
     * 1. message不可以有'\n'
     * 2. username only has : num , _ ,letter
     */
    private boolean isValidMessage(ChatMessage chatMessage) {
        String username = chatMessage.getUsername();
        String message = chatMessage.getMessage();
        return isValidUsername(username)&&noLineBreak(message);
    }

    private boolean noLineBreak(String message) {
        // 只需检查message
        char[] chars = message.toCharArray();
        for (char aChar : chars) {
            if (aChar == '\n'){
                return false;
            }
        }

        return true;
    }

    private boolean isValidUsername(String username) {

        char[] chars = username.toCharArray();
        for (char aChar : chars) {
            // valid range
            if(aChar!='_'&&(aChar<48||(aChar>57&&aChar<65)||(aChar>90&&aChar<97)||aChar>122)){
                return false;
            }
        }
        return true;
    }


}
