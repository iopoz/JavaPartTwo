package ru.iopoz.courseTwo.homework6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    private Socket socket;

    public ChatServer(){
        initConnection();
        initReceiver();
        initSender();
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        server.initConnection();
        while (true){
            server.initSender();
            server.initSender();
        }
    }

    private void initConnection(){
        try (ServerSocket ss = new ServerSocket(8100)) {
            System.out.println("server started");
            socket = ss.accept();
            System.out.println("client connected" + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initReceiver(){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    DataInputStream is = new DataInputStream(socket.getInputStream());
                    String message = is.readUTF();
                    System.out.println("received message: " + message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("receiver started");
    }

    private void initSender(){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                    Scanner scanner = new Scanner(System.in);
                    String str = scanner.nextLine();
                    os.writeUTF("fromServer:" + str + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("sender started");
    }
}
