package com.example.utp.Wave3.Ex3;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class MockTask implements Callable<String> {
    private static final AtomicInteger numInstances = new AtomicInteger(0);


    public MockTask() {
        numInstances.incrementAndGet();
    }

    public static synchronized int getNumInstances() {
        return numInstances.get();
    }

    @Override
    public String call() throws Exception {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int rand = random.nextInt(50);
        int max = Math.max(numInstances.get(), rand);
        for (int i = 0; i < max; i++) {
            sb.append(i);
            Thread.sleep(1000);
        }
        return sb.reverse().toString();
    }

}
