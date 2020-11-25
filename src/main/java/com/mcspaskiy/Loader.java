package com.mcspaskiy;

public class Loader implements Runnable {
    private static final int PRODUCTIVITY = 3;
    private Thread thread;
    private GarbageTruck garbageTruck;

    public Loader(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        garbageTruck.load(PRODUCTIVITY);
    }

    public void startLoad() {
        this.thread.start();
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
