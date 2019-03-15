package ru.iopoz.courseTwo.homework4;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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

        setLayout(new GridLayout(2, 1));
        JPanel p1 = new JPanel();

        p1.setBackground(Color.GREEN);
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        JTextArea jTextOutputArea = new JTextArea();
        p1.add(new JScrollPane(jTextOutputArea));


        JPanel p2 = new JPanel();
        p2.setBackground(Color.LIGHT_GRAY);
        p2.setLayout(new GridLayout(1, 2));
        JTextField jTextInputField = new JTextField();
        JButton jBtnSend = new JButton("Send");
        jBtnSend.setBounds(50, 50, 100, 100);


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
//
//        jbf.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                System.out.println("you pressed me!");
//            }
//        });

        // add(jbf);


        setVisible(true);

    }

    public static void main(String[] args) {
        new ChatWindow();
    }


}
