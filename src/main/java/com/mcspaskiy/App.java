package com.mcspaskiy;

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
     * Нужны подробные логи, кто в данный момент работает, и кто кому что делегирует
     * Потоки должны быть приостановлены, только 1 в единицу времени.
     */

    public static void main(String[] args) {
        System.out.println("Это выдуманная история и все персонажи вымышленные. Все совпадения не имеют отношения к рельной жизни.");
        System.out.println("----------------------------------------");
        GarbageTruck garbageTruck = new GarbageTruck();

        Loader loader = new Loader(garbageTruck);
        loader.startLoad();

        Carrier carrier = new Carrier(garbageTruck);
        carrier.startTransportation();

        UnLoader unLoader = new UnLoader(garbageTruck);
        unLoader.startUnload();

        loader.join();
        System.out.println("Соковой уволился. Мусора в Кулькове больше нет.");
        carrier.join();
        System.out.println("Перевозчик прекратил свою деятельность. Привез последнюю партию.");
        unLoader.join();
        System.out.println("Яценюк уволился. Свалка заполнена.");

        System.out.println("----------------------------------------");
        System.out.println("Конец.");
    }
}
