package com.mcspaskiy;

import com.mcspaskiy.simulation1.Carrier;
import com.mcspaskiy.simulation1.GarbageTruck;
import com.mcspaskiy.simulation1.Loader;
import com.mcspaskiy.simulation1.UnLoader;
import com.mcspaskiy.simulation2.Storage;
import com.mcspaskiy.simulation2.Worker1;
import com.mcspaskiy.simulation2.Worker2;
import com.mcspaskiy.simulation2.Worker3;

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
        System.out.println("SIMULATION 1");
        System.out.println("----------------------------------------");

        Storage storage = new Storage();

        Thread thread1 = new Thread(new Worker1(storage));
        thread1.start();

        Thread thread2 = new Thread(new Worker2(storage));
        thread2.start();

        Thread thread3 = new Thread(new Worker3(storage));
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("SIMULATION 2");
        System.out.println("----------------------------------------");

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
