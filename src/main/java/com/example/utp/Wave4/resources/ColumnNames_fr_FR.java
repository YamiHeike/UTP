package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class ColumnNames_fr_FR extends ListResourceBundle {
    private final Object[][] contents = {
            {"Country", "Pays"},
            {"Start Date", "Date de début"},
            {"End Date", "Date de fin"},
            {"Destination", "Destination"},
            {"Price", "Prix"},
            {"Currency", "Devise"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}