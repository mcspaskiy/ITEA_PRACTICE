package com.mcspaskiy.multiplayer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private GameClient client;

    public WriteThread(Socket socket, GameClient client) {
        this.socket = socket;
        this.client = client;
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Writer try to send....");
            String message = client.getMessage();
            if (message != null) {
                writer.println(message);
                System.out.println("Writer send the message: " + message);
            }
        }
    }
}
