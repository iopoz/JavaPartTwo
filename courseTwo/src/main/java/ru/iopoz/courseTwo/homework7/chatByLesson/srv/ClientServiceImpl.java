package ru.iopoz.courseTwo.homework7.chatByLesson.srv;

import ru.iopoz.courseTwo.homework7.User;

import java.io.IOException;


public class ClientServiceImpl implements ClientService {
    private final User client;
    private final MessageService messageService;
    private final ClientStorage clientStorage;

    public ClientServiceImpl(User client, MessageService messageService, ClientStorage clientStorage) {
        this.client = client;
        this.messageService = messageService;
        this.clientStorage = clientStorage;
    }

    @Override
    public void processMessage() {
        try {
            while (true) {
                String message = client.getIs().readUTF();
                System.out.println(String.format("received message '%s' to '%s'", message, client));

                messageService.sendMessages(client.getUserNick() + "::" + message);
            }
        } catch (IOException io) {
            clientStorage.removeClient(client);
            io.printStackTrace();
        }
    }


}
