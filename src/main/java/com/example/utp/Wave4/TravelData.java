package com.example.utp.Wave4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TravelData implements Translateable, Formattable {
    private File dataDir;
    private Map<String, String> localeCodes;


    public TravelData(File dataDir) {
        this.dataDir = dataDir;
    }

    List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        if(!dataDir.isDirectory()) {
            System.out.println("The file you passed to the constructor is NOT a directory.");
            return null; // GUI will show an empty interface
        }
        List<String> offers = new ArrayList<>();
        Map<String, String> localeCodes = new HashMap<>();

        File[] files = dataDir.listFiles((dataDir, name) -> name.endsWith(".txt"));
        String[] codes = loc.split("_");
        Locale newLoc = new Locale(codes[0], codes[1]);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, newLoc);

        for(File file : files) {
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while((line = br.readLine()) != null) {
                    String[] data = line.split("\t");
                    StringBuilder sb = new StringBuilder();

                    String[] recordCodes = data[0].split("_");

                    Locale recordLoc;

                    if(recordCodes.length == 1) {
                        recordLoc = new Locale(recordCodes[0]);
                    } else if(recordCodes.length <= 0) {
                        continue;
                    } else {
                        recordLoc = new Locale(recordCodes[0], recordCodes[1]);
                    }

                    SimpleDateFormat recFormat = new SimpleDateFormat(dateFormat, recordLoc);

                    String country = translate(data[1], recordLoc, newLoc, ResourceName.COUNTRIES);
                    String startDate = convertDate(data[2], recFormat, sdf);
                    String endDate = convertDate(data[3], recFormat, sdf);

                    String dest = translate(data[4], recordLoc, newLoc, ResourceName.DESTS);
                    String price = convertAmount(data[5], recordLoc, newLoc);
                    String currency = data[6];

                    sb.append(country)
                            .append(" ")
                            .append(startDate)
                            .append(" ")
                            .append(endDate)
                            .append(" ")
                            .append(dest)
                            .append(" ")
                            .append(price)
                            .append(" ")
                            .append(currency);

                    offers.add(sb.toString());
                    localeCodes.put(data[0], sb.toString());
                }
            } catch(FileNotFoundException fnfe) {
                System.out.println("File Not Found");
                fnfe.printStackTrace();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return offers;
    }


    public Map<String,String> getLocaleCodes() {
        return localeCodes;
    }
}
