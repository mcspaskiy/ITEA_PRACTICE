package com.mcspaskiy;

import com.github.javafaker.Faker;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Player implements Callable<String> {
    private int connectionProblems;
    private String name;
    private static final int MIN_TIME = 5;
    private static final int RANDOM_TIME = 16;

    public Player() {
        this.name = new Faker().name().fullName();
        this.connectionProblems = (int) (Math.random() * RANDOM_TIME + MIN_TIME);
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(connectionProblems);
        return name;
    }
}
