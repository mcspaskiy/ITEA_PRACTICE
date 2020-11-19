package com.mcspaskiy;

import java.util.concurrent.TimeUnit;

public class Worker implements Runnable {
    private int productivity = 3; //per second
    private int goldMined;
    private Thread thread;
    private GoldMine goldMine;

    public Worker(GoldMine goldMine, String name) {
        this.goldMine = goldMine;
        this.thread = new Thread(this, name);
    }

    public Worker() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(3);
                int value = goldMine.getGold(productivity);
                goldMined += value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        thread.start();
    }

    public String getName() {
        return thread.getName();
    }

    public int getGold() {
        return goldMined;
    }
}
