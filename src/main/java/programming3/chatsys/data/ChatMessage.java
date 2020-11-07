package programming3.chatsys.data;


import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;


public class ChatMessage {
    /**
     *  Create the class with its attributes, getter methods and constructors to initialize them.
     *  Also implement the toString, equals and hashCode methods.
     */
    private int id;
    private String username;
    private Timestamp timestamp;
    private String message;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (id != that.id) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public ChatMessage(int id, String username, Timestamp timestamp, String message) {
        this.id = id;
        this.username = username;
        this.timestamp = timestamp;
        this.message = message;
    }


     //add two empty methods
     //For the time being, return null for theformat method and leave the parse method empty.


    /**
     *
     * 和toString()有什么不同？？
     * @return 返回表示对象的格式化String
     */

    public String format(){
        return null;
    }

    public void parse(){}

    /**
     * The method takes a file as an argument
     * @param file to save the object into that file
     * note :  Make sure that the file is opened in “append” mode.
     */
    public boolean save(File file) throws IOException {
        // You can do this by creating first a FileWriter like this: “new FileWriter(filename, true)”. Then
        // you can pass the FileWriter to a PrintWriter.
        FileWriter writer = new FileWriter(file,true);

        // save your results in a file “messages_test.txt”.
        writer.write(id+"\n");
        writer.write(username+"\n");
        writer.write(message+"\n");
        writer.write(timestamp.toString()+"\n");

        writer.close();

        // 用于断言
        return true;

        //After your test are executed, don’t forget to remove the temporary file. You can do this
        //by defining a method “clean” inside your test class and use the annotations @AfterAll or @AfterEach
    }
}
