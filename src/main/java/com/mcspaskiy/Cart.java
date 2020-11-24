package com.mcspaskiy;

import java.util.concurrent.TimeUnit;

public class Cart {
    private static final int PASSPORT_CAPACITY = 6;
    private int currentCargo = 0;

    public boolean isFull() {
        return currentCargo == PASSPORT_CAPACITY;
    }

    public synchronized void increaseCount(int value) {
        while (currentCargo < PASSPORT_CAPACITY) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.currentCargo += value;
            System.out.println("Loader has loaded " + currentCargo);
        }
    }

    public synchronized void decreaseCount(int value) {
        this.currentCargo += value;
    }

    public  int getCurrentCargo() {
        return currentCargo;
    }
}
