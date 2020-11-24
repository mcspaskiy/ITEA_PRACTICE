package com.mcspaskiy;

/**
 * Hello world!
 */
public class App {
    /**
     * Есть 3 рабочих,
     * У каждого своя специал-я: грузчик, транспортёр, разгрузчик
     * В куче 100 чего-то
     * Тележка вмещает 6 кг песка
     * Грузчик набирает в тележку 3кг/с
     * Когда тележка полн, транспортёр везёт 5 сек
     * Разгрузчик разгружает 2 кг/с
     * И транспортёр везёт (тоже 5 сек) телегу обратно грузчику.
     * <p>
     * Нужны подробные логи, кто в данный момент работает, и кто кому что делегирует
     * <p>
     * Потоки должны быть приостановлены, только 1 в единицу времени.
     */

    public static void main(String[] args) {
        //store: 100 kg
        //cart: 6 kg,
        //loader: 3 per sec
        //carrier: 5 sec for transport
        //unload: 2 per sec
        System.out.println("Это выдуманная история и все персонажи вымышленные. Все совпадения не имеют отношения к рельной жизни.");

        //Store store = new Store();
        Cart cart = new Cart();
        //System.out.println(Thread.activeCount());
        Loader loader = new Loader(cart);
        loader.startLoad();

        Carrier carrier = new Carrier(cart);
        carrier.startTransportation();

        UnLoader unLoader = new UnLoader(cart);
        unLoader.startUnload();


        loader.join();
        carrier.join();
        unLoader.join();

        System.out.println("История успешно рассказана.");
    }
}
