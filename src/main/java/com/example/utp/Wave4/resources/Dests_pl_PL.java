package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class Dests_pl_PL extends ListResourceBundle {
    private final Object[][] contents = {
            {"mountains", "góry"},
            {"sea", "morze"},
            {"lake", "jezioro"},
    };


    @Override
    protected Object[][] getContents() {
        return contents;
    }

}
