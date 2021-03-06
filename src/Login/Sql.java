package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Sql {

    Connection conn;

    public Sql() {
        String dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=APFinal";
        String username = "localhost";
        String password = "0902266985";

        try {

            conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//        insert("amir","mahdi","hassan","fddf","fsgsdf");
//          System.out.println(searchLogin("amirmahd", "chiti"));
    }

    public int insert(String username, String email, String question, String answer, String pass) {
        if (!isValidEmailAddress(email)) {
            return -1; // -1 for incorrect information , 0 for duplicated information , 1 for correct information
        }
        if (checkCreate(email, pass, username) != 1) {
            return checkCreate(email, pass, username);
        }
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO [dbo].[user2] VALUES " + "('" + username + "','" + email + "','" + question
                    + "','" + answer + "','" + pass + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ridiiiiiiiii");
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
            return 1;
        }
        return -1;
    }

    public boolean searchLogin(String username, String pass) {
        String sql = "SELECT * FROM [dbo].[user2]";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                if (username.equals(result.getString("username").trim()) && pass.equals(result.getString("password").trim())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }

        return false;
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private int checkCreate(String email, String pass, String username) {
        if (pass.length() < 8) {
            return -1;
        }
        String sql = "SELECT * FROM [dbo].[user2]";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                if (username.equals(result.getString("username"))) {
                    return 0;
                }
                if (email.equals(result.getString("email"))) {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }

        return 1;
    }

    public String[] recover(String email, String question, String answer) {
        String sql = "SELECT * FROM [dbo].[user2]";
        String[] str = new String[2];
        str[0] = "0";
        str[1] = "0";
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println(email.equals(result.getString("email")));
                System.out.println(question.equals(result.getNCharacterStream("selectedQuestion")));
                System.out.println(answer.equals(result.getString("answer")));

                if (email.equals(result.getString("email").trim()) && answer.equals(result.getString("answer").trim())) {
                    str[0] = result.getString("username");
                    str[1] = result.getString("password");
                    System.out.println("hasttttttttttttt");
                }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }

        return str;
    }

    public int saveMessage(String text, String sender, String reciver) {
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO private3(sender,receiver,message) VALUES " + "('" + sender + "','" + reciver + "','"
                    + text + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println(" massage saved!");
            return 1;
        }
        return -1;

    }

    public ArrayList<String> getMessage(String sender, String receiver) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT * FROM private3 Where (sender = '" + sender + "' and receiver = '" + receiver + "')or (sender = '" + receiver + "' and receiver = '" + sender + "') ORDER BY ID";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                sender = result.getString("sender").trim();
                arrayList.add(sender + ": " + result.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public int addcontact(String me, String contact) {
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO contact(me,contact) VALUES " + "('" + me + "','" + contact
                    + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println(" massage saved!");
            return 1;
        }
        return -1;

    }

    public ArrayList<String> getContact(String me) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT * FROM contact Where me = '" + me + "'";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                arrayList.add(result.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public int saveRecord(String user1, String user2, String result) {
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO record3(user1,user2,result) VALUES " + "('" + user1 + "','" + user2 + "','"
                    + result + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println(" massage saved!");
            return 1;
        }
        return -1;

    }

    /*    index 0 : win from friend
          index 1 : draw from friend
          index 2 : lose whit friend
          index 3 : win from computer
          index 4 : draw from computer
          index 5 : lose whit computer
     */
    public int[] getRecord(String user) {
        int[] array = new int[6];
        String sql = "SELECT * FROM record3 Where user1 = '" + user + "'";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                if (!result.getString("user2").trim().equals("computer")) {
                    if(result.getString("result").trim().equals("draw")){
                        array[1]++;
                    }
                    else if(result.getString("result").trim().equals("win")){
                        array[0]++;
                    }
                    else{
                        array[2]++;
                    }
                }
                else{
                    if(result.getString("result").trim().equals("draw")){
                        array[4]++;
                    }
                    else if(result.getString("result").trim().equals("win")){
                        array[3]++;
                    }
                    else{
                        array[5]++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }
    public boolean searchUser(String username) {
        String sql = "SELECT * FROM [dbo].[user2]";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                if (username.equals(result.getString("username").trim())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }

        return false;
    }
    public boolean joinGroup(String group,String user){
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO memberGroup(groupName,member) VALUES " + "('" + group + "','" + user + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        if (rowsInserted > 0) {
            System.out.println(" join group in sql ...");
            return true;
        }
        return false;

    }

    public boolean  isMember(String group, String user) {
        String sql = "SELECT * FROM memberGroup where groupName = '"+group+"'";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                if (user.equals(result.getString("member").trim())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return false;
    }
    public int saveMessageGroup(String text,String group,String sender){
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO group2(groupName,sender,message) VALUES " + "('" + group + "','" + sender + "','"
                    + text + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println(" massage group saved!");
            return 1;
        }
        return -1;
    }
    public ArrayList<String> getGroup(String me){
         ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT * FROM memberGroup Where member = '" + me + "'";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                arrayList.add("#"+result.getString("groupName").trim()+" (group)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    
     public ArrayList<String> getMessageGroup(String group){
        ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT * FROM group2 Where (groupName = '" + group +"') ORDER BY ID";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
               String sender = result.getString("sender").trim();
                arrayList.add(sender + ": " + result.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public ArrayList<String> getMember(String group){
         ArrayList<String> arrayList = new ArrayList<String>();
        String sql = "SELECT * FROM memberGroup Where groupName = '" + group + "'";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                arrayList.add(result.getString("member").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
