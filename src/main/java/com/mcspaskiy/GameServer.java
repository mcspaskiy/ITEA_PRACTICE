package com.mcspaskiy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class GameServer {
    private static final int PORT = 3129;
    private static final int MAX_PLAYERS = 2;
    private Set<PlayerThread> playerThreads = new HashSet<>();

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.execute();
    }

    public void execute() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT, 0, inetAddress)) {
            System.out.println("Game Server is running on address " + inetAddress.getHostAddress() + ":" + PORT);
            while (playerThreads.size() < MAX_PLAYERS) {
                Socket socket = serverSocket.accept();
                System.out.println("Player connected");
                PlayerThread newPlayer = new PlayerThread(playerThreads.size(), socket, this);
                playerThreads.add(newPlayer);
                newPlayer.start();
            }
            System.out.println("Room is full");
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    synchronized void broadcast(String message, PlayerThread excluded) {
        for (PlayerThread player : playerThreads) {
            if (player != excluded) {
                player.sendMessage(message);
            }
        }
    }
}