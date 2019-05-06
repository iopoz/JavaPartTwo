package ru.iopoz.courseTwo.homework7;

import org.sqlite.JDBC;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DBConnector {
    private static final String DB_STR = "jdbc:sqlite:D:\\git\\lessons\\JavaPartTwo\\courseTwo\\src\\main\\java\\ru\\iopoz\\courseTwo\\homework7\\db.sqlite";

    private static DBConnector instance = null;

    public static synchronized DBConnector getInstance() throws SQLException {
        if (instance == null) instance = new DBConnector();
        return instance;
    }

    private Connection connection;

    public DBConnector() throws SQLException{
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(DB_STR);
    }

    public void addUser(String userNick, String userPassword){
        try(PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO user('user_nick', 'user_password') VALUES(? ,?)")){
            statement.setObject(1, userNick);
            statement.setObject(2, userPassword);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getUserId(String userNick){
        try(Statement statement = this.connection.createStatement()){
            String sqlStr = "Select user_id from user where user_nick='" + userNick + "'";
            ResultSet resultSet = statement.executeQuery(sqlStr);
            return resultSet.getInt("user_id");
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isUserAuthorized(String userNick, String userPassword){
        try(Statement statement = this.connection.createStatement()){
            String sqlStr = "Select user_id from user where user_nick='" + userNick + "' and user_password='" + userPassword + "'";
            ResultSet resultSet = statement.executeQuery(sqlStr);
            return !resultSet.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllMessages(String userId){
        try(Statement statement = this.connection.createStatement()){
            String sqlStr = "Select t1.msg_text, t1.msg_date, t2.user_nick from messages as t1, user as t2 where t2.user_id=t1.user_from and (user_to='" + userId + "' or user_to=0)";

            ResultSet resultSet = statement.executeQuery(sqlStr);

            List<String> msgList = new ArrayList<>();

            while (resultSet.next()){

                msgList.add("from: "+ resultSet.getString("user_nick")+ " at "
                        + resultSet.getString("msg_date") + "\t\n"
                        + resultSet.getString("msg_text"));
            }
            return msgList;
        } catch (SQLException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void sendMsg(String msgText, String userNickFrom, String userNickTo){
        int userToID=0;
        int userFromID = Integer.parseInt(userNickFrom);
        ResultSet resultSet;
        if (!userNickTo.isEmpty()) {
            try (Statement statement = this.connection.createStatement()) {
                String sqlStr = "Select user_id from user where user_nick='" + userNickTo + "'";
                resultSet = statement.executeQuery(sqlStr);
                while (resultSet.next()) {
                    userToID = resultSet.getInt("user_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try(PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO messages('msg_text', 'msg_date', 'user_to', 'user_from') VALUES(? ,?, ?, ?)")){
            statement.setObject(1, msgText);
            statement.setObject(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            statement.setObject(3, userToID);
            statement.setObject(4, userFromID);
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public String getUser(String userId){
        try(Statement statement = this.connection.createStatement()){
            String sqlStr = "Select user_nick from user where user_id=" + userId + "";

            ResultSet resultSet = statement.executeQuery(sqlStr);

            String user="";

            while (resultSet.next()){
                user = resultSet.getString("user_nick");
            }
            return user;
        } catch (SQLException e){
            e.printStackTrace();
            return "";
        }
    }

}
