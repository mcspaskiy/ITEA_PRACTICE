package com.mcspaskiy;

public class WeightProduct extends Product {
    protected double weight;

    public WeightProduct(String title, double price, double weight) {
        super(title, price);
        this.weight = weight;
    }

    public String showProduct() {
        return String.format("Title - %s, price - %.2f, weight - %.2f", title, price, weight);
    }
}
