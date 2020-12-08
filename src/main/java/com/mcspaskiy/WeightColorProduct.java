package com.mcspaskiy;

public class WeightColorProduct extends WeightProduct {
    private String color;

    public WeightColorProduct(String title, double price, double weight, String color) {
        super(title, price, weight);
        this.color = color;
    }

    public String showProduct() {
        return String.format("Title - %s, price - %.2f, weight - %.2f, color - %s", title, price, weight, color);
    }
}
