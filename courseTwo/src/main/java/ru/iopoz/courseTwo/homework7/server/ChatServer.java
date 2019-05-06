package ru.iopoz.courseTwo.homework7.server;

import ru.iopoz.courseTwo.homework7.DBConnector;
import ru.iopoz.courseTwo.homework7.net.TCPConnection;
import ru.iopoz.courseTwo.homework7.net.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) throws SQLException {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private final HashMap<TCPConnection, Integer> activeUsers = new HashMap<>();
    private final DBConnector dbConnector = new DBConnector();

    private ChatServer() throws SQLException {
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(8190)){
           while (true){
               try{
                   new TCPConnection(this, serverSocket.accept());
               } catch (IOException e){
                   System.out.println("TCPConnection exception: " + e);
               }
           }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
    }

    @Override
    public synchronized void onReceiverString(TCPConnection tcpConnection, String value) {
        String[] strList = value.split(":");
        if(strList[0].equals("auth")){
            isUserAuth(strList[1], strList[2], tcpConnection);
        } else if (!strList[2].equals(" ")){
            sendPrivateMsg(strList[1], strList[2], strList[3]);
        } else {
            sendBroadCast(strList[1], strList[3]);
        }
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendBroadCast("server", "Client disconnected: " + tcpConnection);

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendBroadCast(String from, String value){
        System.out.println(value);
        if (!from.equals("server")) {
            String msg_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            dbConnector.sendMsg(value, from, "");
            for (TCPConnection connection : connections) {
                connection.sendMsg("from:" + dbConnector.getUser(from) + " at " + msg_time + "\t\n" + value);
            }
        }
    }

    private boolean isUserAuth(String user, String password, TCPConnection tcpConnection){
        boolean flag = dbConnector.isUserAuthorized(user, password);
        if (flag) {
            sendBroadCast("server","Client connected: " + user + "\n");
            int userId = dbConnector.getUserId(user);
            activeUsers.put(tcpConnection, userId);
            tcpConnection.sendMsg("userId:"+ userId);

            List<String> msgList = new ArrayList<>(dbConnector.getAllMessages(Integer.toString(activeUsers.get(tcpConnection))));
            for (String msg: msgList) {
                tcpConnection.sendMsg(msg);
            }

            return true;
        }
        //onDisconnect(tcpConnection);
        tcpConnection.sendMsg("authError");

        return false;
    }

    private void sendPrivateMsg(String from, String to, String msg){
        String msg_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        dbConnector.sendMsg(msg, from, to);
//        for (TCPConnection connection : connections) {
//            connection.sendMsg("from:" + dbConnector.getUser(from) + " at " + msg_time + "\t\n" + value);
//        }
        for (Map.Entry entry: activeUsers.entrySet()){
            if (to.equals(entry.getValue())){
                TCPConnection userConnection = (TCPConnection) entry.getKey();
                userConnection.sendMsg("from:" + dbConnector.getUser(from) + " at " + msg_time + "\t\n" + msg);
            }
        }
    }
}
