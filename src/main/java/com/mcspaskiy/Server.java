package com.mcspaskiy;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server implements Runnable {
    private List<Player> players;
    private Thread thread;
    private static final int THREADS_COUNT = 10;
    private static final int TIME_LIMIT = 15;

    public Server() {
        this.thread = new Thread(this);
    }

    public void startServer() {
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Waiting for players...");
        List<String> results = new ArrayList<>();
        synchronized (players) {
            ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);
            List<Future<String>> connectedPlayers = new ArrayList<>();
            for (int i = 0; i < THREADS_COUNT; i++) {
                Future<String> result = executorService.submit(players.get(i));
                connectedPlayers.add(result);
            }

            Date initDate = new java.util.Date();
            for (Future<String> player : connectedPlayers) {
                try {
                    String playerName = player.get();
                    String connectionString = String.format("Player %s connected\n", playerName);
                    int usedSeconds = (int) ((new Date().getTime() - initDate.getTime()) / 1000);
                    if (usedSeconds > TIME_LIMIT) {

                        System.out.printf("Player %s has broken our room.\n", playerName);
                        System.out.println("Connection lost. This game is safe to leave.");
                        break;
                    } else {
                        results.add(connectionString);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();
        }
        if (results.size() == THREADS_COUNT) {
            for (String result : results) {
                System.out.println(result);
            }
            System.out.println("All players connected. Ready to start.");
        }
    }

    public void addPlayers(List<Player> players) {
        this.players = players;
    }
}
