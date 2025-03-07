package com.example.utp.Wave3.Ex1;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Letters letters = new Letters("ABCD");
        for (Thread t : letters.getThreads()) System.out.println(t.getName());

        letters.startThreads();

        Thread.sleep(5000);

        letters.stopThreads();

        System.out.println("\nProgram skończył działanie");
    }

}

