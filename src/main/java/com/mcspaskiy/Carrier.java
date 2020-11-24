package com.mcspaskiy;

public class Carrier implements Runnable {
    private static final int TRANSPORTATION_TIME = 5;
    private Thread thread;
    private Cart cart;

    public Carrier(Cart cart) {
        this.cart = cart;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        cart.move(TRANSPORTATION_TIME);
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
