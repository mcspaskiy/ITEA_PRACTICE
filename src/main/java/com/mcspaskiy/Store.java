package com.mcspaskiy;

public class Store {
    private int count = 100;

    public void decreaseCount(int value) {
        this.count -= value;
    }

    public int getCount() {
        return count;
    }
}
