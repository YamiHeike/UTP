package com.example.utp.Wave3.Ex4;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Author implements Runnable {
    private BlockingQueue<String> list = new LinkedBlockingQueue<>();
    private String[] texts;

    public Author(String[] strings) {
        this.texts = strings;
    }

    public String getTextToWrite() throws InterruptedException {
        return list.take();
    }

    @Override
    public void run() {
        for(int i = 0; i <= texts.length; i++) {
            try {
                if(i < texts.length) {
                    Thread.sleep((int) (Math.random() * 1000));
                    list.put(texts[i]);
                } else {
                    list.put("END");
                }

            } catch(InterruptedException ie) {
                ie.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

