package ru.iopoz.courseTwo.homework6;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.glass.ui.Cursor.setVisible;

public class ChatClient extends JFrame {
    private Socket socket;
    private JTextArea outputTextArea;
    private JTextField inputTextField;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public ChatClient(){
    initConnection();
    initGui();
    initReceiver();
}

    private void initReceiver() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String echoMessage = inputStream.readUTF();
                    outputTextArea.append(echoMessage);

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
            socket = new Socket("localhost", 8100);
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

            outputTextArea.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n" + message + "\n");
            inputTextField.setText("");
            sendMessage(message);
        }

    }

    private void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initGui() {
        outputTextArea = new JTextArea();
        inputTextField = new JTextField();

        setTitle("Client");
        setBounds(500, 200, 700, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        panel.add(new JScrollPane(outputTextArea));

        outputTextArea.setEditable(false);


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));


        JButton button = new JButton("Send");

        panel1.add(inputTextField);
        panel1.add(button);

        //нажатие кнопки
        button.addActionListener(e -> {
            processMessage();
        });

        //нажание Enter
        inputTextField.addActionListener(e -> processMessage());

        add(panel, BorderLayout.CENTER);
        add(panel1, BorderLayout.SOUTH);
        setVisible(true);

        System.out.println("gui initialized ");
    }

    public static void main(String[] args) {

        new ChatClient();
    }
}
