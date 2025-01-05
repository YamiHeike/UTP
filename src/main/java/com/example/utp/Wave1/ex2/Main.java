package com.example.utp.Wave1.ex2;


public class Main {

    public static void main(String[] args)  {
        CustomersPurchaseSortFind cpsf = new CustomersPurchaseSortFind();

        // Exercise requirement: file customers.txt must exist in home directory
        String fname = System.getProperty("user.home") + "/customers.txt";
        cpsf.readFile(fname);
        cpsf.showSortedBy(ClientSort.NAME);
        cpsf.showSortedBy(ClientSort.PRICE);

        String[] custSearch = { "c00001", "c00002" };

        for (String id : custSearch) {
            cpsf.showPurchaseFor(id);
        }
    }

}