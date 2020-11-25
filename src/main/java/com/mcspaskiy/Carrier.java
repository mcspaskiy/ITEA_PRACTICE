package com.mcspaskiy;

public class Carrier implements Runnable {
    private static final int TRANSPORTATION_TIME = 5;
    private Thread thread;
    private GarbageTruck garbageTruck;

    public Carrier(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        garbageTruck.move(TRANSPORTATION_TIME);
    }

    public void startTransportation() {
        thread.start();
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
