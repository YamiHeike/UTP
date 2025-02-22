package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class ColumnNames_pl_PL extends ListResourceBundle {
    private final Object[][] contents = {
            {"Country", "Kraj"},
            {"Start Date", "Data rozpoczęcia"},
            {"End Date", "Data zakończenia"},
            {"Destination", "Cel podróży"},
            {"Price", "Cena"},
            {"Currency", "Waluta"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

