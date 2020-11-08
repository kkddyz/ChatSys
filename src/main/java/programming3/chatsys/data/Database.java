package programming3.chatsys.data;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    // For the time being, the class will have one attributes referencing the file for the message database

    private File messages;

    private List<ChatMessage> messageList = new ArrayList<>();

    public Database(File messages) {
        this.messages = messages;
    }

    public List<ChatMessage> readMessage() throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new FileReader(messages));
        String line ;
        ArrayList<String> temp = new ArrayList<>();

        // readLine() will keep on reading the next line from the file
        // and once it reaches the end of the file it returns null.

        while ((line=reader.readLine())!=null) {
            // according to line init messages and add to messageList
            if (temp.size()<4){
                temp.add(line);
            }else {
                initMessage(temp);
            }
        }
        initMessage(temp);

        reader.close();


        return messageList;
    }

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

}
