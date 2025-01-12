package com.example.utp.Wave3.Ex3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ThreadPanel extends JFrame {
    public ThreadPanel() {
        setLayout(new BorderLayout());
        setTitle("Task Management Service");
        ThreadList list = new ThreadList(5);
        add(list, BorderLayout.CENTER);
        JPanel bGroup = new JPanel();
        bGroup.setLayout(new FlowLayout());
        JButton button = new JButton("Add");
        button.addActionListener(e -> list.addTask(new MockTask()));
        bGroup.add(button);
        button = new JButton("Cancel");
        button.addActionListener(e -> list.cancelTask());
        bGroup.add(button);
        button = new JButton("State");
        button.addActionListener(e -> list.getSelectedTaskState());
        bGroup.add(button);
        button = new JButton("Result");
        button.addActionListener(e -> list.getResult());
        bGroup.add(button);
        add(bGroup, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}