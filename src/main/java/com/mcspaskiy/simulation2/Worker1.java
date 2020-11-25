package com.mcspaskiy.simulation2;

public class Worker1 implements Runnable {
    private Storage storage;
    private static final int PRODUCTIVITY = 3; //per sec

    public Worker1(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        System.out.println("BEGIN");
        while (storage.getStorageA() > 0) {
            storage.get(PRODUCTIVITY);
        }
        System.out.println("Worker 1 has finished job.");
    }
}
