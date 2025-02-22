package com.example.utp.Wave4;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements Translateable, Formattable {
    private List<Travel> listData;
    private final String[] columnNames = {"Country", "Start Date", "End Date", "Destination", "Price", "Currency"};
    private final Locale defaultLoc = new Locale("en", "GB");
    private Locale currentLoc;
    private Locale settingsLoc;
    private static String[] langs = { "pl_PL", "en_GB", "fr_FR", "ja_JP"};
    private static Map<String, Locale> supportedLocales = new HashMap<>();

    static {
        for(String lang : langs) {
            String[] codes = lang.split("_");
            supportedLocales.put(lang, new Locale(codes[0], codes[1]));
        }
    }

    public TableModel(List<Travel> listData, Locale currentLoc) {
        this.listData = listData;
        this.currentLoc = currentLoc;
        settingsLoc = currentLoc;
    }

    public void setLocale(Locale loc) {
        boolean isFound = false;
        for(Locale l : supportedLocales.values()) {
            if(l.getCountry().equals(loc.getCountry()) && l.getLanguage().equals(loc.getLanguage())) {
                currentLoc = loc;
                fireTableStructureChanged();
                isFound = true;
                break;
            }
        }

        if(!isFound) {
            JOptionPane.showMessageDialog(null,"We do not have language support for " + loc.getLanguage()  + ".\nPlease, choose another option");
        }
    }

    public void setRegionalSettings(Locale loc) {
        boolean isFound = false;
        for(Locale l: supportedLocales.values()) {
            if(l.getCountry().equals(loc.getCountry()) && l.getLanguage().equals(loc.getLanguage())) {
                settingsLoc = loc;
                fireTableStructureChanged();
                isFound = true;
                break;
            }
        }
        if(!isFound) {
            JOptionPane.showMessageDialog(null,"Regional settings for " + loc.toLanguageTag() + " unavailable.\nPlease, choose another option");
        }
    }

    public static Map<String, Locale> getSupportedLocales() {
        return supportedLocales;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return listData.size();
    }

    @Override
    public String getColumnName(int col) {
        return (defaultLoc.equals(currentLoc)) ? columnNames[col] : translate(columnNames[col], defaultLoc, currentLoc, ResourceName.COLUMNS);
    }

    @Override
    public Object getValueAt(int row, int col) {
        Travel travel = listData.get(row);

        boolean locTest = currentLoc.getLanguage().equals("en");

        switch(col) {
            case 0:
                String ctr = travel.getCountry();
                return (locTest) ? ctr : translate(ctr, defaultLoc, currentLoc, ResourceName.COUNTRIES);
            case 1:
                return travel.getStartDate();
            case 2:
                return travel.getEndDate();
            case 3:
                String dest = travel.getDest();
                return (locTest) ? dest : translate(dest, defaultLoc, currentLoc, ResourceName.DESTS);
            case 4:
                double price = travel.getPrice();
                String textNum = String.valueOf(price);
                return (settingsLoc.getLanguage().equals("en")) ? price : convertAmount(textNum, defaultLoc, settingsLoc);
            case 5:
                return travel.getCurrency();
            default:
                return null;

        }
    }

}
