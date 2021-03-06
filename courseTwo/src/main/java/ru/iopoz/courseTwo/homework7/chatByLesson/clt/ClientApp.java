package ru.iopoz.courseTwo.homework7.chatByLesson.clt;

import ru.iopoz.courseTwo.homework7.client.ClientWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class ClientApp extends JFrame {

    private Socket socket;
    private JTextArea outputTextArea;
    private JTextField inputTextField;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private boolean isAuth;

    public ClientApp() {
        initGui();
    }

    private void initReceiver() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String echoMessage = inputStream.readUTF();
                    System.out.println("received message::" + echoMessage);
                    if(!echoMessage.equals("authError")){
                        isAuth = true;
                        outputTextArea.append(echoMessage);
                    } else {
                        isAuth = false;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("receiver started");
    }

    private void initConnection() {
        try {
            socket = new Socket("localhost", 8190);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("connection initialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessage() {
        if (!inputTextField.getText().equals("")) {
            String message = inputTextField.getText();
            inputTextField.setText("");
            sendMessage(message);
        }

    }

    private void sendMessage(String message) {
        try {
            System.out.println("sent message:" + message);
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initGui() {
        outputTextArea = new JTextArea();
        inputTextField = new JTextField();

        setTitle("client.ClientApp");
        setBounds(500, 200, 200, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel textPanel = createTextPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel authPanel = createAuthPanel(textPanel, buttonPanel);

        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(authPanel, BorderLayout.NORTH);

        setVisible(true);

        System.out.println("gui initialized ");
    }

    private JPanel createTextPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());


        textPanel.add(new JScrollPane(outputTextArea));
        textPanel.setVisible(false);

        outputTextArea.setEditable(false);     //чтобы нельзя было печатать текст в поле
        return textPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));


        inputTextField.addActionListener(e -> processMessage());

        JButton button = new JButton("Send");

        buttonPanel.add(inputTextField);
        buttonPanel.add(button);
        buttonPanel.setVisible(false);
        //нажатие кнопки
        button.addActionListener(e -> {
            processMessage();
        });
        return buttonPanel;
    }

    private JPanel createAuthPanel(JPanel textPanel, JPanel buttonPanel) {
        JPanel authPanel = new JPanel();
        JTextField loginField = new JTextField();
        JTextField passwordField = new JTextField();
        JLabel errorState = new JLabel();
        //loginField.addActionListener(e -> processMessage());

        JButton authButton = new JButton("Auth");
        authButton.addActionListener(e -> {
            initConnection();
            initReceiver();
            sendMessage("auth:" + loginField.getText() + ":" + passwordField.getText());
            if (isAuth){
                authPanel.setVisible(false);
                setBounds(500, 200, 700, 700);
                buttonPanel.setVisible(true);
                textPanel.setVisible(true);
            } else {
                errorState.setText("Auth problems! Use correct user date");
            }

        });

        authPanel.add(errorState);
        authPanel.add(loginField);
        authPanel.add(passwordField);
        authPanel.add(authButton);
        authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
        //authPanel.setBounds(500, 200, 100, 100);
        authPanel.setVisible(true);
        return authPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientApp();
            }
        });
    }
}
