package com.example.utp.Wave4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class TravelView extends JFrame {
    private Locale currentLocale = new Locale("en", "GB");
    private ResourceBundle guiBundle = ResourceBundle.getBundle(ResourceName.GUI, currentLocale);
    private final TableModel model;
    private final JLabel mainHdr;
    private final SideMenu rSettings;
    private final SideMenu lSettings;
    private final List<JButton> settingsButtons;
    private final List<JButton> languageButtons;
    Map<String, Locale> locs;

    public TravelView(List<Travel> data) {
        setTitle("InterDb Travel Agency Offers");

        this.model = new TableModel(data, currentLocale);

        JTable table = new JTable(model);
        JScrollPane jsPane = new JScrollPane(table);
        mainHdr = new MainHdr(guiBundle.getString("Our Offers"));

        add(mainHdr, BorderLayout.NORTH);
        add(jsPane, BorderLayout.CENTER);


        locs = TableModel.getSupportedLocales();
        settingsButtons = new ArrayList<>();
        languageButtons = new ArrayList<>();

        locs.forEach((key, val) -> {
            JButton sButton = new JButton(val.getDisplayCountry(currentLocale));
            JButton lButton = new JButton(val.getDisplayLanguage(currentLocale));

            sButton.addActionListener((ActionEvent e) -> {
                SwingUtilities.invokeLater(() -> {
                    model.setRegionalSettings(val);
                });
            });

            lButton.addActionListener((ActionEvent e) -> {
                currentLocale = val;
                guiBundle = ResourceBundle.getBundle(ResourceName.GUI, currentLocale);
                model.setLocale(val);
                SwingUtilities.invokeLater(() -> {
                    updateGui();

                });
            });

            settingsButtons.add(sButton);
            languageButtons.add(lButton);

        });

        JPanel smPane = new JPanel(new GridLayout(0,1));
        rSettings = new SideMenu(guiBundle.getString("Regional Settings"), settingsButtons);
        lSettings = new SideMenu(guiBundle.getString("Language Settings"), languageButtons);

        smPane.add(rSettings);
        smPane.add(lSettings);
        add(smPane, BorderLayout.WEST);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void updateGui() {
        mainHdr.setText(guiBundle.getString("Our Offers"));
        rSettings.setText(guiBundle.getString("Regional Settings"));
        lSettings.setText(guiBundle.getString("Language Settings"));
        locs.forEach((key, val) -> {
            int idx = new ArrayList<>(locs.keySet()).indexOf(key);
            if(idx >= 0 && idx < settingsButtons.size()) {
                settingsButtons.get(idx).setText(val.getDisplayCountry(currentLocale));
            }
            if(idx >= 0 && idx < languageButtons.size()) {
                languageButtons.get(idx).setText(val.getDisplayLanguage(currentLocale));
            }
        });
    }
}

