package com.mcspaskiy;

/**
 * Есть три класса(базовые поля имя и цена)-Product,WeightProduct, WeightColorProduct.
 * Реализовать полку-дженерик,на которой могут сохранятся любой из товаров с методами showProduct,
 * которые могли бы в зависимости от обьекта,выводить значения всех полей товара.
 */
public class App {
    public static void main(String[] args) {
        Shelf<Product> shelf = new Shelf<>();
        shelf.save(new Product("Simple book", 100));
        shelf.save(new WeightProduct("Heavy book", 200, 0.5f));
        shelf.save(new WeightColorProduct("Colored heavy book", 300, 0.55f, "BLUE"));

        for (Product product : shelf.getProducts()) {
            System.out.println(product.showProduct());
        }
    }
}
