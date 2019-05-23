package ru.iopoz.courseTwo.homework7.chatByLesson.srv;

import ru.iopoz.courseTwo.homework7.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private static ClientStorage clientStorage = new ClientStorage();
    private static MessageService messageService = new MessageService(clientStorage);

    public static void main(String[] args) throws IOException {

        try (ServerSocket ss = new ServerSocket(8099)) {
            System.out.println("server started");
            while (true) {
                Socket socket = ss.accept();
                DataInputStream is = new DataInputStream(socket.getInputStream());
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());

                User client = new User(is.readUTF(), is.readUTF(), is, os);
                System.out.println("client connected::" + client + "::" + socket);
                clientStorage.addClient(client);
                new Thread(() -> new ClientServiceImpl(client, messageService, clientStorage)
                        .processMessage()).start();
            }
        }
    }
}
