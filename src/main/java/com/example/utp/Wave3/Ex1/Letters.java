package com.example.utp.Wave3.Ex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Letters {
    private List<Thread> threads;
    private String text;
    private volatile boolean running = true;

    public Letters(String text) {
        this.text = text;
        threads = new ArrayList<>();

        for(int i = 0; i < text.length(); i++) {

            int index = i;
            threads.add(new Thread(() -> {
                while(running) {
                    try {
                        Thread.sleep(1000);
                        System.out.print(text.charAt(index));
                    } catch(InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }, "Thread " + text.charAt(i)));
        }
    }

    public String getText() {
        return text;
    }

    public List<Thread> getThreads() {
        return Collections.unmodifiableList(threads);
    }

    public synchronized void startThreads() {
        if(!threads.isEmpty()) {
            running = true;
            threads.forEach(Thread::start);
        }
    }

    public synchronized void stopThreads() {
        running = false;
        threads.forEach(Thread::interrupt);
    }

}
