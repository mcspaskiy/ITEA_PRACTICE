package com.mcspaskiy;

public class GoldMine {
    private int store = 1000;

    public synchronized int getGold(int count) {
        int mined;
        if (store < count) {
            mined = store;
            store = 0;
        } else {
            mined = count;
            store -= mined;
        }
        return mined;
    }

    public int getGoldInStore() {
        return store;
    }
}
