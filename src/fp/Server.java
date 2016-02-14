package fp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import fp.UserData.*;

public class Server {
    private Connection connect = null;
    private Statement state = null;
    private ResultSet result = null;
    private PreparedStatement prepared_state = null;

    private String hostname = "localhost";
    private int port = 3306;
    private String database = "bowl-aquarium";
    private String db_user = "test";
    private String db_password = "test";

    private String dropSQL = "DROP TABLE `User`";
    private String createSQL = "CREATE TABLE IF NOT EXISTS `User` (" +
            " `id` int(10) unsigned NOT NULL AUTO_INCREMENT," +
            " `name` VARCHAR(20) NOT NULL," +
            " `passwd` VARCHAR(20) NOT NULL," +
            " `money` int(10) unsigned NOT NULL," +
            " `total_fish_number` int(10) unsigned NOT NULL," +
            " `kind1_fish_number` int(10) unsigned NOT NULL," +
            " `kind2_fish_number` int(10) unsigned NOT NULL,PRIMARY KEY (`id`)) DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";
    private String selectSQL = "SELECT * FROM `User` ";
    private String updateSQL = "UPDATE `User` SET `money`=?,`total_fish_number`=?,`kind1_fish_number`=?,`kind2_fish_number`=? WHERE `name`=?";
    private String insertSQL = "INSERT INTO User(`name`,`passwd`,`money`,`total_fish_number`,`kind1_fish_number`,`kind2_fish_number`) VALUES (?,?,?,?,?,?)";

    ObjectInputStream inputFromClient;
    ObjectOutputStream outputToClient;

    public Server() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?user=" + db_user + "&password=" + db_password + "&autoReconnect=true"
            );
            createTable();

            System.out.println("Server database connected");
        } catch (ClassNotFoundException e) {
            System.out.println("DriverClassNotFound :" + e.toString());
        } catch(SQLException x) {
            System.out.println("Exception :" + x.toString());
        }

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Socket server = serverSocket.accept();
            System.out.println("Server start");

            while ( true ) {
                try {
                    inputFromClient = new ObjectInputStream(server.getInputStream());
                    UserData userData = (UserData)inputFromClient.readObject();
                    execute(userData, server);
                } catch (ClassNotFoundException e) {
                    System.out.println("Server: ReadObject go wrong");
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public static void main(String[] args) { Server server = new Server(); }

    protected void createTable() {
        try {
            state = connect.createStatement();
            state.executeUpdate(createSQL);
        } catch(SQLException e) {
            System.out.println("CreateDB Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }
    }

    protected void insertTable(String name, String passwd, int money, int total_fish_number, int kind1_fish_number, int kind2_fish_number) {
        try {
            prepared_state = connect.prepareStatement(insertSQL);
            prepared_state.setString(1, name);
            prepared_state.setString(2, passwd);
            prepared_state.setLong(3, money);
            prepared_state.setLong(4, total_fish_number);
            prepared_state.setLong(5, kind1_fish_number);
            prepared_state.setLong(6, kind2_fish_number);
            prepared_state.executeUpdate();
        } catch(SQLException e) {
            System.out.println("InsertDB Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }
    }

    protected void dropTable() {
        try {
          state = connect.createStatement();
          state.executeUpdate(dropSQL);
        } catch(SQLException e) {
          System.out.println("DropDB Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }
    }

    public void selectTable() {
        try {
            state = connect.createStatement();
            result = state.executeQuery(selectSQL);

            while( result.next() ) {
                result.getString("name");
                result.getString("passwd");
            }
        } catch(SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }
    }

    public void sendToClient(Socket server, UserData userData) {
        try {
            outputToClient = new ObjectOutputStream(server.getOutputStream());
            outputToClient.writeObject(userData);
            outputToClient.flush();
        } catch (IOException e) {
            System.out.println("Server: SentObject go wrong");
        }
    }
    
    protected void execute(UserData userData, Socket server) {
        if ( userData.getUserType() == 0 ) {
            if ( login(userData.getUserName(), userData.getUserPassword()) ) {
                sendToClient(server, getUserData(userData));
            } else {
                System.out.println("User Input error");
            }
        } else if ( userData.getUserType() == 1) {
            createNewUser(userData.getUserName(), userData.getUserPassword());
            sendToClient(server, getUserData(userData));
        } else if ( userData.getUserType() == 2 ) {
            updateUserData(userData.getUserName(), userData.getMoney(), userData.getTotalFish(), userData.getKind1FishNumber(), userData.getKind2FishNumber());
            sendToClient(server, getUserData(userData));
        }
    }
    
    protected void createNewUser(String name, String passwd) { insertTable(name, passwd, 500, 0, 0, 0); }

    protected boolean login(String name, String passwd) {
        String db_name = new String();
        String db_passwd = new String();
        
        try {
            state = connect.createStatement();
            result = state.executeQuery(selectSQL + "WHERE `name`=\"" + name + "\"");

            while( result.next() ) {
                db_name = result.getString("name");
                db_passwd = result.getString("passwd");
            }
        } catch(SQLException e) {
            System.out.println("Login Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }

        return ( db_name.equals(name) && db_passwd.equals(passwd) ) ? true : false;
    }

    protected UserData getUserData(UserData userData) {
        UserData db_userData = new UserData();

        try {
            state = connect.createStatement();

            result = state.executeQuery(selectSQL + "WHERE name=\"" + userData.getUserName() + "\"");
            
            while( result.next() ) {
                db_userData.setUserName(userData.getUserName());
                db_userData.setMoney((int)result.getLong("money"));
                db_userData.setTotalFish((int)result.getLong("total_fish_number"));
                db_userData.setKind1FishNumber((int)result.getLong("kind1_fish_number"));
                db_userData.setKind2FishNumber((int)result.getLong("kind2_fish_number"));
            }
        } catch(SQLException e) {
            System.out.println("GetUserData Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }

        return db_userData;
    }
    
    protected void updateUserData(String name, int money, int total_fish_number, int kind1_fish_number, int kind2_fish_number) {
        try {
            prepared_state = connect.prepareStatement(updateSQL);
            prepared_state.setLong(1, money);
            prepared_state.setLong(2, total_fish_number);
            prepared_state.setLong(3, kind1_fish_number);
            prepared_state.setLong(4, kind2_fish_number);
            prepared_state.setString(5, name);
            prepared_state.executeUpdate();
        } catch(SQLException e) {
            System.out.println("UpdateUserSata Exception :" + e.toString());
        } finally {
            CloseDataBase();
        }
    }

    private void CloseDataBase() {
        try {
            if( result != null ) {
                result.close();
                result = null;
            }
            if( state != null ) {
                state.close();
                state = null;
            }
            if( prepared_state != null ) {
                prepared_state.close();
                prepared_state = null;
            }
        } catch(SQLException e) {
          System.out.println("Close Exception :" + e.toString());
        }
    }
}
