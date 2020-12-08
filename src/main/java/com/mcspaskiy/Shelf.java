package com.mcspaskiy;

import java.util.ArrayList;
import java.util.List;

public class Shelf<T extends Product> {
    private List<T> products;

    public Shelf() {
        products = new ArrayList<>();
    }

    public void save(T product) {
        products.add(product);
    }

    public List<T> getProducts() {
        return products;
    }
}
