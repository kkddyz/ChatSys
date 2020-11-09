package programming3.chatsys.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


// create the class User (in programming3.chatsys.data) in a similar way that you created ChatMessage.

public class User {
    private String userName;
    private String fullName;
    private String password;
    private int lastReadID = 0; // default, the lastReadId of a User is 0

    public User() {
    }

    public User(String userName, String fullName, String password, int lastReadID) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.lastReadID = lastReadID;
    }

    // getter() setter() toString() equals() hashCode()
    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public int getLastReadID() {
        return lastReadID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassWard(String passWard) {
        this.password = passWard;
    }

    public void setLastReadID(int lastReadID) {
        this.lastReadID = lastReadID;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", passWard='" + password + '\'' +
                ", lastReadID=" + lastReadID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (lastReadID != user.lastReadID) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + lastReadID;
        return result;
    }

    // format && parse
    public String formatLastReadID() {
        return Integer.toString(lastReadID);
    }

    // 将数据库中的lastReadID解析为变量类型
    public int parseLastReadID(String lastReadID) {
        return Integer.parseInt(lastReadID);
    }

    // save
    public void save(File file) throws IOException {
        // You can do this by creating first a FileWriter like this: “new FileWriter(filename, true)”. Then
        // you can pass the FileWriter to a PrintWriter.
        FileWriter writer = new FileWriter(file, true);

        // save your results in a file “user_test.txt”.
        writer.write(userName + "\n");
        writer.write(fullName + "\n");
        writer.write(password + "\n");
        writer.write(this.formatLastReadID() + "\n");

        writer.close();


    }
}
