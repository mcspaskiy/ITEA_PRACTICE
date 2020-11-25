package com.mcspaskiy.simulation2;

public class Worker3 implements Runnable {
    private Storage storage;
    private static final int PRODUCTIVITY = 2; //per sec

    public Worker3(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (!storage.isStorageBFilled()) {
            storage.put(PRODUCTIVITY);
        }
        System.out.println("Worker 3 has finished job.");
        System.out.println("THE END");
    }
}
