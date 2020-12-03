package com.mcspaskiy.catsandbox.cats;

public abstract class DefaultStreetCat implements Cuttable {
    protected double weight;

    public DefaultStreetCat() {
        this.weight = Math.random() * 10 + 0.1;
    }

    @Override
    public double checkMeatGrinder() {
        return weight;
    }
}
