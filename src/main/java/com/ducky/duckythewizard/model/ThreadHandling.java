package com.ducky.duckythewizard.model;

public class ThreadHandling implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("This is message #" + i);

            try {
                Thread.sleep(2000);
                continue;
            } catch (InterruptedException ex) {
                System.out.println("I'm resumed");
            }
        }
    }

    public static void startThread() {
        Thread t1 = new Thread(new ThreadHandling());
        t1.start();

        try {
            Thread.sleep(5000);
            t1.interrupt();

        } catch (InterruptedException ex) {
            // do nothing
        }

    }
}
