package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class Dests_ja_JP extends ListResourceBundle {
    private final Object[][] contents = {
            {"mountains", "山"},
            {"sea", "海"},
            {"lake", "湖"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }

}

