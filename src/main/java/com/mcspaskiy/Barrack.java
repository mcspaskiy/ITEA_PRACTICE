package com.mcspaskiy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Barrack implements Runnable {
    private int productivity = 10;
    private Thread thread;
    private GoldMine goldMine;
    private int index;
    private boolean alive = true;

    private List<Worker> workers = new ArrayList<>();

    //Only list of names
    private List<String> names = Arrays.asList(
            "Petya", "Kolya", "Vasya", "Kuso", "Nikolay Sergeevich", "Bruce", "Lotus",
            "Fisher", "Denis", "Maximus", "Sidorov", "Vova", "Charlie", "X-144.mod.v2",
            "Anabel", "Ping", "Stuart", "Abdul");

    public Barrack(GoldMine goldMine) {
        this.thread = new Thread(this);
        this.thread.setName(this.getClass().getSimpleName());
        this.goldMine = goldMine;
    }

    @Override
    public void run() {
        try {
            while (alive) {
                TimeUnit.SECONDS.sleep(productivity);
                createWorker();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Barrack closed");
    }

    public void start() {
        thread.start();
    }

    public void createWorker() {
        Worker worker = new Worker(goldMine, names.get(index));
        index++;
        workers.add(worker);
        worker.start();
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void stop() {
        alive = false;
    }
}
