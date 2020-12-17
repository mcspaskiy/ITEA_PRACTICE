package com.mcspaskiy;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread {
    private Socket socket;
    private GameServer server;
    private PrintWriter writer;
    private int queue;

    public PlayerThread(int queue, Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            sendMessage(String.valueOf(queue));
            if (queue == 1) {
                server.broadcast("start", null);
                System.out.println("Server send messages \"start\" to all players");
            }
            String clientMessage;
            while (true) {
                clientMessage = reader.readLine();
                System.out.println("Server received message: " + clientMessage);
                server.broadcast(clientMessage, this);
                if ("exit".equals(clientMessage)) {
                    break;
                }
            }
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error in PlayerThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
        System.out.println("Message was send to client: " + message);
    }
}