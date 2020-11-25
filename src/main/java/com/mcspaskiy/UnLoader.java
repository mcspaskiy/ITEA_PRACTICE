package com.mcspaskiy;

public class UnLoader implements Runnable {
    private static final int PRODUCTIVITY = 2;
    private Thread thread;
    private GarbageTruck garbageTruck;

    public UnLoader(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        garbageTruck.unload(PRODUCTIVITY);
    }

    public void startUnload() {
        this.thread.start();
    }

    public void join() {
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
