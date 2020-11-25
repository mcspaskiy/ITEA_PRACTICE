package com.mcspaskiy.simulation1;

import java.util.concurrent.TimeUnit;

import static com.mcspaskiy.simulation1.Task.*;

public class GarbageTruck {
    private static final int INITIAL_COUNT = 100;
    private static final int CART_PASSPORT_CAPACITY = 6;
    private int garbage = INITIAL_COUNT;
    private int dump = 0;
    private int cargoOnBoard = 0;
    private Task cartTask;

    public GarbageTruck() {
        cartTask = LOAD;
    }

    public synchronized void load(int value) {
        while (garbage > 0) {
            boolean flag = true;
            while (cartTask != LOAD) {
                try {
                    if (flag) {
                        System.out.println("Соковой пошел спать.");
                    }
                    wait();
                    flag = cartTask == LOAD ? true : false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Соковой проснулся и начал грузить мусор...");
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
                System.out.println("Мусоровоз загружен на " + cargoOnBoard + " мусора.");
                if (cargoOnBoard == CART_PASSPORT_CAPACITY || garbage == 0) {
                    cartTask = MOVE_TO_DUMP;
                }
            }

            System.out.println("Соковой закончил грузить.");
            notifyAll();
        }
    }

    public synchronized void move(int value) {
        while (dump < INITIAL_COUNT) {
            while (cartTask == MOVE_TO_DUMP || cartTask == MOVE_TO_STORE) {
                if (cartTask == MOVE_TO_DUMP) {
                    System.out.println("Перевозчик проснулся, взял мусоровоз и повез мусор на свалку...");
                } else {
                    System.out.println("Перевозчик проснулся, взял мусоровоз и поехал в Кульков за мусором...");
                }
                for (int i = 0; i < value; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Мусоровоз едет: " + (i + 1) + " сек.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (cartTask == MOVE_TO_DUMP) {
                    System.out.println("Перевозчик привез мусор Яблунюку на свалку.");
                    cartTask = UNLOAD;
                } else {
                    System.out.println("Перевозчик пригнал мусоровоз в Кульков.");
                    cartTask = LOAD;
                }
                notifyAll();
            }

            if (cartTask == UNLOAD && cargoOnBoard + dump == INITIAL_COUNT) {
                break;
            }

            try {
                System.out.println("Перевозчик лег спать.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized void unload(int value) {
        while (dump < INITIAL_COUNT) {
            boolean flag = false;
            while (cartTask != UNLOAD) {
                try {
                    if (flag) {
                        System.out.println("Яблунюк пошел спать.");
                    }
                    wait();
                    flag = cartTask == UNLOAD ? true : false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Яблунюк проснулся и начал разгружать мусоровоз...");
            while (cartTask == UNLOAD) {
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
                    System.out.println("В мусоровозе " + cargoOnBoard + " мусора.");
                }
                System.out.println("Яблунюк разгрузил мусоровоз.");
                System.out.println("В Кулькове: " + garbage + " мусора | На свалке " + dump + " мусора.");
                cartTask = MOVE_TO_STORE;
                notifyAll();
            }
        }

    }
}
