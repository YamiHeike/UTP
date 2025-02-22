package com.example.utp.Wave4;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public interface Translateable {
    public default String translate(String word, Locale from, Locale to, String resource) {
        if(word == null || from == null || to == null || resource == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        ResourceBundle original = ResourceBundle.getBundle(resource, from);
        ResourceBundle translation = ResourceBundle.getBundle(resource, to);
        String translatedWord = "";
        boolean isFound = false;

        Enumeration<String> keys = original.getKeys();
        while(keys.hasMoreElements()) {
            String currKey = keys.nextElement();
            String origVal = original.getString(currKey);
            if(word.trim().compareToIgnoreCase(origVal) == 0) {
                translatedWord = translation.getString(currKey);
                isFound = true;
                break;
            }
        }

        if(!isFound) {
            return word;
        }

        return translatedWord;
    }
}

