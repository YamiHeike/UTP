package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class ColumnNames_ja_JP extends ListResourceBundle {
    private final Object[][] contents = {
            {"Country", "国"},
            {"Start Date", "開始日"},
            {"End Date", "終了日"},
            {"Destination", "目的地"},
            {"Price", "価格"},
            {"Currency", "通貨"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

