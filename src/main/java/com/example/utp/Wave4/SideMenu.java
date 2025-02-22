package com.example.utp.Wave4;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SideMenu extends JPanel {
    private List<JButton> buttons;
    private JLabel hdr;

    public SideMenu(String header, List<JButton> buttons) {
        this.buttons = buttons;
        setLayout(new GridLayout(0,1));

        hdr = new JLabel(header);
        hdr.setOpaque(true);
        hdr.setBackground(Color.GRAY);
        hdr.setForeground(Color.WHITE);
        hdr.setHorizontalAlignment(SwingConstants.CENTER);

        add(hdr);
        for(JButton button : buttons) {
            add(button);
        }
    }

    public List<JButton> getButtons() {
        return buttons;
    }

    public void setText(String text) {
        hdr.setText(text);
    }
}
