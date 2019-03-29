package ru.iopoz.courseTwo.homework7.chatByLesson.srv;

import ru.iopoz.courseTwo.homework7.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientStorage {
    private static List<User> clients = Collections.synchronizedList(new ArrayList<>());

    public void addClient(User client) {
        System.out.println("client added::"+client);
        clients.add(client);
    }

    public void removeClient(User client) {
        System.out.println("client removed::"+client);
        clients.remove(client);
    }

    public List<User> getClients() {
        return clients;
    }
}
