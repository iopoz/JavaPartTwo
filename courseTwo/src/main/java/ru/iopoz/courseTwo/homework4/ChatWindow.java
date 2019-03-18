package ru.iopoz.courseTwo.homework4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ChatWindow extends JFrame {

    public ChatWindow() throws HeadlessException {
        setBounds(500, 200, 400, 400);
        setTitle("Chat Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());
        JPanel p1 = new JPanel();

        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        JTextArea jTextOutputArea = new JTextArea();
        jTextOutputArea.setPreferredSize(new Dimension(350, 300));
        jTextOutputArea.setEditable(false);
        p1.add(new JScrollPane(jTextOutputArea));

        JPanel p2 = new JPanel();
        p2.setBackground(Color.LIGHT_GRAY);
        p2.setLayout(new FlowLayout());
        JTextField jTextInputField = new JTextField();
        jTextInputField.setPreferredSize(new Dimension(200, 24));
        JButton jBtnSend = new JButton("Send");
        jBtnSend.setBounds(50, 50, 50, 50);


        jTextInputField.addActionListener(e -> {
            jTextOutputArea.append(jTextInputField.getText() + "\n");
            jTextInputField.setText("");
        });

        p2.add(jTextInputField);
        p2.add(jBtnSend);


        add(p1);
        add(p2);


        jBtnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextOutputArea.append(jTextInputField.getText() + "\n");
                jTextInputField.setText("");
            }
        });

        setVisible(true);

    }

    public static void main(String[] args) {
        new ChatWindow();
    }


}
