package com.example.utp.Wave3.Ex4;

public class Writer implements Runnable {
    private Author author;

    public Writer(Author author) {
        this.author = author;
    }

    @Override
    public void run() {
        try {
            String txt = author.getTextToWrite();
            while(!txt.equals("END")) {
                System.out.println("-> " + txt);
                txt = author.getTextToWrite();
            }
        } catch(InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
