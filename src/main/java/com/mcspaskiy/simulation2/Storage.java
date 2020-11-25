package com.mcspaskiy.simulation2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Storage {
    private static final int CART_CAPACITY = 6;
    private static final int INITIAL_COUNT = 100;
    private int storageA = INITIAL_COUNT;
    private int storageB = 0;
    private int inCart = 0;

    private Semaphore semaphore1 = new Semaphore(0);
    private Semaphore semaphore2 = new Semaphore(0);
    private Semaphore semaphore3 = new Semaphore(0);

    public Storage() {
        semaphore1.release();
    }

    public void get(int value) {
        try {
            semaphore1.acquire();
            System.out.println("Worker 1 is loading...");
            while (inCart < CART_CAPACITY && storageA > 0) {
                TimeUnit.SECONDS.sleep(1);
                if (value > storageA) {
                    value = storageA;
                }
                inCart += value;
                storageA -= value;
                System.out.printf("In the cart %d resources. In the storage A %d resources\n", inCart, storageA);
            }
            System.out.println("Worker 1 is sleeping...");
            semaphore2.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveToB(int value) {
        try {
            semaphore2.acquire();
            System.out.println("Worker 2 is moving from point A to B");
            while (value != 0) {
                System.out.printf("Moving to B. The cart will arrive in %d sec.\n", value);
                TimeUnit.SECONDS.sleep(1);
                value--;
            }
            System.out.println("Worker 2 is sleeping...");
            semaphore3.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveToA(int value) {
        try {
            semaphore2.acquire();
            System.out.println("Worker 2 is moving from point B to A");
            while (value != 0) {
                System.out.printf("Moving to A. The cart will arrive in %d sec.\n", value);
                TimeUnit.SECONDS.sleep(1);
                value--;
            }
            System.out.println("Worker 2 is sleeping...");
            semaphore1.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(int value) {
        try {
            semaphore3.acquire();
            System.out.println("Worker 3 is unloading...");
            while (inCart > 0) {
                TimeUnit.SECONDS.sleep(1);
                if (value > inCart) {
                    value = inCart;
                }
                inCart -= value;
                storageB += value;
                System.out.printf("In the cart %d resources. In the Storage B %d resources\n", inCart, storageB);
            }
            System.out.println("Worker 3 is sleeping...");
            semaphore2.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getStorageA() {
        return storageA;
    }

    public boolean isStorageBFilled() {
        return storageB == INITIAL_COUNT;
    }
}
