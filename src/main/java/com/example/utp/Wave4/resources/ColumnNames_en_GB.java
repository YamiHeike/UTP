package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class ColumnNames_en_GB extends ListResourceBundle {
    private final Object[][] contents = {
            {"Country", "Country"},
            {"Start Date", "Start Date"},
            {"End Date", "End Date"},
            {"Destination", "Destination"},
            {"Price", "Price"},
            {"Currency", "Currency"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
