package com.mcspaskiy.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private GameClient client;

    public ReadThread(Socket socket, GameClient client) {
        this.socket = socket;
        this.client = client;
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
        setDaemon(true);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Try to receive....");
                String response = reader.readLine();
                if (response != null) {
                    client.setResponse(response);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }
}