package com.example.utp.Wave1.ex2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomersPurchaseSortFind {
    List<Purchase> purchaseData = new ArrayList<>();

    public void readFile(String fname) {
        try(
                BufferedReader br = new BufferedReader(new FileReader(fname));
        ) {
            String line;
            while((line = br.readLine()) != null) {
                //c00004;Nowak Anna;banany;4.0;50.0 --fixed format
                String[] data = line.split(";");

                //Ewentualnie mogę to zrefactorować na builder
                Purchase purchase = new Purchase(data[0], data[1], data[2], Double.parseDouble(data[3]),Double.parseDouble(data[4]));
                purchaseData.add(purchase);
            }
        } catch(FileNotFoundException exc) {
            System.out.println(fname + ": File not found");
        } catch(IOException ioe) {
            System.out.println("Could not read from the file.\n" + ioe.getMessage());
        }

    }

    public void showSortedBy(ClientSort field) {
        List<Purchase> toSort = new ArrayList<>(purchaseData);
        switch(field) {
            case NAME:
                System.out.println(field);
                toSort.sort(Comparator.comparing(Purchase::getClientname)
                        .thenComparing(Purchase::getClientId));
                toSort.forEach(System.out::println);
                System.out.println();
                break;
            case PRICE:
                System.out.println(field);
                toSort.sort(Comparator.comparingDouble(Purchase::getTotalCost)
                        .reversed()
                        .thenComparing(Purchase::getClientId));
                toSort.forEach(Purchase::printIncludeCost);
                System.out.println();
                break;
            default:
                throw new IllegalArgumentException("Lack of implementation for this field");
        }
    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        List<Purchase> purchases = purchaseData.stream().filter(itm -> itm.getClientId().equals(id)).toList();
        purchases.forEach(System.out::println);
        System.out.println();
    }
}