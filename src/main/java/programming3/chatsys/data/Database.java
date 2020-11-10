package programming3.chatsys.data;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public class Database {

    // For the time being, the class will have one attributes referencing the file for the message database

    private File DB_messages_file = new File("src\\main\\resources\\database_messages_file.txt");
    private File DB_users_file = new File("src\\main\\resources\\database_users_file.txt");

    private List<ChatMessage> messageList = new ArrayList<>(); // message容器
    private Map<String, User> userMap = new HashMap<>(); // user容器

    // constructor && setter
    public Database() {
    }

    public Database(File DB_messages_file, File DB_users_file) {
        this.DB_messages_file = DB_messages_file;
        this.DB_users_file = DB_users_file;
    }

    public void setDB_messages_file(File DB_messages_file) {
        this.DB_messages_file = DB_messages_file;
    }

    public void setDB_users_file(File DB_users_file) {
        this.DB_users_file = DB_users_file;
    }

    // init指将对象从文件取出放入BD容器
    private void initMessage(ArrayList<String> temp) throws ParseException {
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

    private void initUser(ArrayList<String> temp) {
        // init
        User user = new User();
        String username = temp.get(0);
        user.setUserName(username);
        String fullName = temp.get(1);
        user.setFullName(fullName);
        String password = temp.get(2);
        user.setPassWard(password);
        String lastReadId = temp.get(3);
        user.setLastReadID(user.parseLastReadID(lastReadId));

        // add
        userMap.put(username, user);
    }

    public List<ChatMessage> readMessages() throws IOException, ParseException {
        // clean messageList
        messageList.clear();

        BufferedReader reader = new BufferedReader(new FileReader(DB_messages_file));
        ArrayList<String> temp = new ArrayList<>();
        String line;

        // readLine() will keep on reading the next line from the file
        // and once it reaches the end of the file it returns null.

        while ((line = reader.readLine()) != null) {
            // according to line init messages and add to messageList
            if (temp.size() < 4) {
                temp.add(line);
            } else {
                initMessage(temp);
                temp.clear();
                temp.add(line);
            }
        }
        if (temp.size() == 4) {
            initMessage(temp);
            temp.clear();
        }

        reader.close();


        return messageList;
    }

    public Map<String, User> readUsers() throws IOException {
        userMap.clear(); // 预处理

        BufferedReader reader = new BufferedReader(new FileReader(DB_users_file));
        ArrayList<String> temp = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            if (temp.size() < 4) {
                temp.add(line);
            } else {
                initUser(temp);
                temp.clear();
                temp.add(line);
            }
        }
        if (temp.size()==4){
            initUser(temp);
        }
        return userMap;
    }



    // add指将对象存入BD文件
    public void addMessage(ChatMessage chatMessage) throws Exception {
        // check message
        if (isValidId(chatMessage.getId()) && isValidMessage(chatMessage)) {
            chatMessage.save(DB_messages_file);
        } else {
            throw new Exception(chatMessage + " is not valid ");
        }

    }

    public void addUsr(User user) throws IOException {
        String username = user.getUserName();
        String fullName = user.getFullName();
        String password = user.getPassword();
        if (isValidName(username) && isValidName(fullName) && noLineBreak(password)) {
            user.save(DB_users_file);
        } else {
            System.out.println("invalid user name");
            System.exit(0);
        }

    }

    /**
     * 从数据库读取信息判断id是否合理 不合理抛出异常
     */
    private boolean isValidId(int id) throws Exception {


        messageList = readMessages();
        for (ChatMessage message : messageList) {
            if (message.getId() > id)
                throw new Exception("invalid id");
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
        return isValidName(username) && noLineBreak(message);
    }

    /**
     * 规则 没有'\n'
     * 应用：ChatMessage.message User.password
     */
    private boolean noLineBreak(String message) {
        char[] chars = message.toCharArray();
        for (char aChar : chars) {
            if (aChar == '\n') {
                return false;
            }
        }

        return true;
    }

    /**
     * 规则 只有 num,letter,_
     * 应用于 user : User .username , fullName
     */
    private boolean isValidName(String username) {

        char[] chars = username.toCharArray();
        for (char aChar : chars) {
            // valid range
            if (aChar != '_' && (aChar < 48 || (aChar > 57 && aChar < 65) || (aChar > 90 && aChar < 97) || aChar > 122)) {
                return false;
            }
        }
        return true;
    }

    // -------------------------------------------------------------------------------------------

    // 在getUnreadMessage中调用 已经读取userMap
    private void updateLastReadID(String username,int id) throws IOException {

        //Update the last read id of the user matching the username provided as argument with the id provided as argument
        userMap.get(username).setLastReadID(id);
        File newFile = DB_users_file;
        // 删除user文件
        newFile.delete();newFile.createNewFile();
        Set<String> usernames = userMap.keySet();
        for (String username1 : usernames) {
            User user = userMap.get(username1);
            user.save(DB_users_file);
        }

    }
    public List<ChatMessage> getUnreadMessage(String username) throws IOException, ParseException {
        ArrayList<ChatMessage> unreadMessages = new ArrayList<>();
        messageList = readMessages();
        userMap = readUsers();
        User user = userMap.get(username);
        // select user match，并获取其上次读取的ID
        int user_lastReadID = user.getLastReadID();
        // Select the subset of messages that have an id greater than the last read id
        for (ChatMessage chatMessage : messageList) {
            if (chatMessage.getId()>user_lastReadID){
                unreadMessages.add(chatMessage);
            }
        }
        // Call updateLastRead with the id of the message that has the greatest id
        updateLastReadID(username,getLastMessageID());

        return unreadMessages;
    }
    /**
     * 判断message是否空
     */
    public boolean isEmpty() throws IOException, ParseException {

        messageList = readMessages();
        return messageList.isEmpty();
    }

    /**
     *
     */
    public int getLastMessageID() {
        int lastReadID = 0;
        try {
            messageList = readMessages();
            if (!messageList.isEmpty()) {
                for (ChatMessage chatMessage : messageList) {
                    if (chatMessage.getId() > lastReadID) {
                        lastReadID = chatMessage.getId();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastReadID;
    }
}




