package com.mcspaskiy;

public class Loader implements Runnable {
    private static final int PRODUCTIVITY = 3;
    private Thread thread;
    private Cart cart;

    public Loader(Cart cart) {
        this.cart = cart;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        cart.load(PRODUCTIVITY);
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
