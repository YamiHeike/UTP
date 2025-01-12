package com.example.utp.Wave3.Ex3;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThreadPanel::new);
    }
}