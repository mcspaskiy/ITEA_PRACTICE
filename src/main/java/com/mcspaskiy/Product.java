package com.mcspaskiy;

public class Product {
    protected String title;
    protected double price;

    public Product(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String showProduct() {
        return String.format("Title - %s, price - %.2f", title, price);
    }
}
