package com.example.utp.Wave4;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainHdr extends JLabel {
    public MainHdr(String headerText) {
        setText(headerText);
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 25));
        setBorder(new EmptyBorder(10,20,10,20));
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
