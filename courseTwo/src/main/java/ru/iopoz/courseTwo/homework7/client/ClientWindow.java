package ru.iopoz.courseTwo.homework7.client;

import ru.iopoz.courseTwo.homework7.net.TCPConnection;
import ru.iopoz.courseTwo.homework7.net.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientWindow extends JFrame implements TCPConnectionListener {

    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8190;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

//    private final JTextArea log = new JTextArea();
//    private final JTextField userNick = new JTextField("iopoz");
//    private final JTextField msgField = new JTextField();
    private JTextArea outputTextArea;
    private JTextField inputTextField;
    private JPanel authPanel = new JPanel();
    private JLabel errorState = new JLabel();
    private JPanel textPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JTextField loginField = new JTextField();

    private boolean isAuth;
    private boolean isChatWindow=false;
    private int userId = 0;

    private TCPConnection connection;

    private ClientWindow(){initGUI();}

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready ");
    }

    @Override
    public void onReceiverString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!msg.startsWith("Connection")) {
                    if(!msg.equals("authError")){
                        isAuth = true;
                        if(!isChatWindow){
                            showChatWindow();
                        }
                        if(!msg.startsWith("userId:")){
                            outputTextArea.append(msg + "\n");
                        } else {
                            userId = Integer.parseInt(msg.replaceAll("[\\D]", ""));
                        }

                    } else {
                        isAuth = false;
                        errorState.setText("Auth error!");
                        connection.disconnect();
                    }
                }
            }
        });
    }

    private synchronized void initConnection(){
        try {
            connection = new TCPConnection(this, IP_ADDRESS, PORT);
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }

    private synchronized void initGUI(){
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

        textPanel.setLayout(new BorderLayout());


        textPanel.add(new JScrollPane(outputTextArea));
        textPanel.setVisible(false);

        outputTextArea.setEditable(false);     //чтобы нельзя было печатать текст в поле
        return textPanel;
    }

    private JPanel createButtonPanel() {

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


        JTextField passwordField = new JTextField();

        //loginField.addActionListener(e -> processMessage());

        JButton authButton = new JButton("Auth");
        authButton.addActionListener(e -> {
            initConnection();
            connection.sendMsg("auth:" + loginField.getText() + ":" + passwordField.getText());
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

    private synchronized void processMessage() {
        if (!inputTextField.getText().equals("")) {
            String message = inputTextField.getText();
            inputTextField.setText("");
            String to = " ";
            StringBuilder msg = null;
            if (message.startsWith("@")) {
                String[] strList = message.split(" ");
                to = strList[0].replace("@", "");
                msg = new StringBuilder(to);
                for (int i = 1; i < strList.length; i++) {
                    msg.append(" ").append(strList[i]);
                }
            } else {
                msg = new StringBuilder(message);
            }
            //connection.sendMsg("msg:" + loginField.getText() + "- ");
            connection.sendMsg("msg:" + userId + ":" + to + ":" + msg);
        }

    }

    private synchronized void showChatWindow(){
        if (isAuth){
            authPanel.setVisible(false);
            setBounds(500, 200, 700, 700);
            buttonPanel.setVisible(true);
            textPanel.setVisible(true);
            isChatWindow = true;
        } else {
            errorState.setText("Auth problems! Use correct user date");
        }
    }
}
