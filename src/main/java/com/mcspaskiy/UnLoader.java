package com.mcspaskiy;

public class UnLoader implements Runnable {
    private static final int PRODUCTIVITY = 2;
    private Thread thread;
    private Cart cart;

    public UnLoader(Cart cart) {
        this.cart = cart;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        cart.unload(PRODUCTIVITY);
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
