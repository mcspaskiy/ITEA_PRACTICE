package com.mcspaskiy;

public class Loader implements Runnable {
    private int productivity = 3;
    private Thread thread;
    private Store store;
    private Cart cart;

    public Loader(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        synchronized (store) {
            System.out.println("Loader is loading...");
            cart.increaseCount(productivity);
            store.decreaseCount(cart.getCurrentCargo());
            System.out.println("Loader has finished load. Store: " + store.getCount() + " Cart: " + cart.getCurrentCargo());
            System.out.println("Waiting for carrier...");
        }
    }

    public void startLoad() {
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
