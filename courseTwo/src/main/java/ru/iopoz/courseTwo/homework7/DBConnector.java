package ru.iopoz.courseTwo.homework7;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBConnector {
    private static final String DB_STR = "jdbc:sqlite:db.sqllite";

    private static DBConnector instance = null;

    public static synchronized DBConnector getInstance() throws SQLException {
        if (instance == null) instance = new DBConnector();
        return instance;
    }

    private Connection connection;

    private DBConnector() throws SQLException{
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(DB_STR);
    }

    public void addUser(String userNick, String userPassword){
        try(PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO User('user_nick', 'user_password') VALUES(? ,?)")){
            statement.setObject(1, userNick);
            statement.setObject(2, userPassword);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isUserExist(String userNick){
        try(Statement statement = this.connection.createStatement()){
            String sqlStr = "Select * from User where 'user_nick'=" + userNick;
            return statement.execute(sqlStr);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserAuthorized(String userNick, String userPassword){
        try(Statement statement = this.connection.createStatement()){
            String sqlStr = "Select * from User where 'user_nick'=" + userNick + " and 'user_password=" + userPassword;
            return statement.execute(sqlStr);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllMessages(String userNick){
        try(Statement statement = this.connection.createStatement()){
            ResultSet userResSet = statement.executeQuery("Select user_id from User where user_nick=" + userNick);
            String userId = "";
            while (userResSet.next()){
                 userId = Integer.toString(userResSet.getInt("user_id"));
            }
            String sqlStr = "Select t1.msg_text, t1.msg_date, t2.user_nick from Messages as t1, User as t2 where t2.user_id=t1.user_from and ('user_to'=" + userId + " or 'user_to is null)";

            ResultSet resultSet = statement.executeQuery(sqlStr);

            List<String> msgList = new ArrayList<>();

            while (resultSet.next()){

                msgList.add("");
            }
        } catch (SQLException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
