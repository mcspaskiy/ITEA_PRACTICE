package com.mcspaskiy;

import java.util.concurrent.TimeUnit;

import static com.mcspaskiy.Task.*;

public class Cart {
    private static final int INITIAL_COUNT = 20;
    private static final int CART_PASSPORT_CAPACITY = 6;
    private int garbage = INITIAL_COUNT;
    private int dump = 0;
    private int cargoOnBoard = 0;
    private Task cartTask;

    public Cart() {
        cartTask = LOAD;
    }

    /**
     * Must work while we have a garbage in Lviv
     */
    public synchronized void load(int value) {
        while (garbage > 0) {
            while (cartTask != LOAD) {
                try {
                    // System.out.println("Садовой пошел спать.");
                    wait();
                    // System.out.println("Садовой проснулся.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Садовой грузит мусор...");
            while (cartTask == LOAD) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (value > garbage) {
                    value = garbage;
                }
                cargoOnBoard += value;
                garbage -= value;
                System.out.println("Тележка загружена на " + cargoOnBoard + " груза.");
                if (cargoOnBoard == CART_PASSPORT_CAPACITY || garbage == 0) {
                    cartTask = MOVE_TO_DUMP;
                }
            }

            System.out.println("Садовой закончил грузить.");
            notifyAll();
        }
        System.out.println("Садовой уволился. Мусора во Львове больше нет.");
    }

    public synchronized void move(int value) {
        while (dump < INITIAL_COUNT) { //TODO: Fix
            if (cartTask == MOVE_TO_DUMP || cartTask == MOVE_TO_STORE) {
                if (cartTask == MOVE_TO_DUMP) {
                    System.out.println("Перевезчик взял тележку и повез на свалку мусор...");
                } else {
                    System.out.println("Перевезчик взял тележку и поехал во Львов за мусором...");
                }
                for (int i = 0; i < value; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Пройдено: " + (i + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (cartTask == MOVE_TO_DUMP) {
                    System.out.println("Перевезчик привез мусор Яценюку на свалку.");
                    cartTask = UNLOAD;
                } else {
                    System.out.println("Перевозчик пригнал тележку во Львов.");
                    cartTask = LOAD;
                }
                notifyAll();
            } else {
                try {
                    // System.out.println("Перевозчик лег спать.");
                    wait();
                    //    System.out.println("Перевозчик проснулся.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Перевозчик прекратил свою деятельность. Мусора нет.");
    }

    /**
     * If we fill the dump then we will stop
     */
    public synchronized void unload(int value) {
        while (dump < INITIAL_COUNT) {
            if (cartTask != UNLOAD) {
                try {
                    //      System.out.println("Яценюк пошел спать.");
                    wait();
                    //      System.out.println("Яценюк проснулся.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (cartTask == UNLOAD) {
                System.out.println("Яценюк начал разгружать тележку...");
                while (cargoOnBoard > 0) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (value > cargoOnBoard) {
                        value = cargoOnBoard;
                    }
                    cargoOnBoard -= value;
                    dump += value;
                    System.out.println("В тележке " + cargoOnBoard);
                }
                System.out.println("Яценюк разгрузил тележку.");
                System.out.println("Во Львове: " + garbage + " мусора | На свалке " + dump + " мусора.");
                cartTask = MOVE_TO_STORE;
                notifyAll();
            }
        }
        System.out.println("Яценюк уволился. Свалка заполнена.");
    }
}
