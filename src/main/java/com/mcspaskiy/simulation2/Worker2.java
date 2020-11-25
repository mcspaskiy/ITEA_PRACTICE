package com.mcspaskiy.simulation2;

public class Worker2 implements Runnable {
    private Storage storage;
    private boolean moveToB = true;
    private static final int TIME_IN_ROAD = 5;

    public Worker2(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            if (moveToB) {
                storage.moveToB(TIME_IN_ROAD);
                if (storage.getStorageA() == 0) {
                    break;
                }
                moveToB = false;
            } else {
                storage.moveToA(TIME_IN_ROAD);
                moveToB = true;
            }
        }
        System.out.println("Worker 2 has finished job.");
    }
}
