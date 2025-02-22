package com.example.utp.Wave4.resources;

import java.util.ListResourceBundle;

public class GUIWords_ja_JP extends ListResourceBundle {
    private final Object[][] contents = {
            {"Regional Settings", "地域設定"},
            {"Language Settings", "言語設定"},
            {"Our Offers", "旅行プランのご案内"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
