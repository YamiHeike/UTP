package com.example.utp.Wave4;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File dataDir = new File("data");
        TravelData travelData = new TravelData(dataDir);
        String dateFormat = "yyyy-MM-dd";
        for (String locale : Arrays.asList("pl_PL", "en_GB")) {
            List<String> odlist = travelData.getOffersDescriptionsList(locale, dateFormat);
            for (String od : odlist) System.out.println(od);
        }
        // --- część bazodanowa
        String url = "jdbc:postgresql://localhost:5433/travel_agency";
        Database db = new Database(url, travelData);
        db.showGui();
    }
}
