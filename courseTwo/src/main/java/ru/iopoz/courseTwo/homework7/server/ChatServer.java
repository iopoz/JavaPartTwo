package ru.iopoz.courseTwo.homework7.server;

import ru.iopoz.courseTwo.homework7.net.TCPConnection;
import ru.iopoz.courseTwo.homework7.net.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer(){
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
        sendBroadCast("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiverString(TCPConnection tcpConnection, String value) {
        sendBroadCast(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendBroadCast("Client disconnected: " + tcpConnection);

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendBroadCast(String value){
        System.out.println(value);
        final int cnt = connections.size();
        for (int i=0; i < cnt; i++) connections.get(i). sendMsg(value);
    }
}
