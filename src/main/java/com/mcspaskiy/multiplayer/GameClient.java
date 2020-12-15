package com.mcspaskiy.multiplayer;

import com.mcspaskiy.model.ResponseReceiver;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class GameClient {
    private String hostname;
    private int port;
    private String userName;
    private String message;
    private String response;
    private ResponseReceiver receiver;

    public GameClient(String userName, String hostname, int port) {
        this.userName = userName;
        this.hostname = hostname;
        this.port = port;
    }

    public void execute(ResponseReceiver receiver) {
        this.receiver = receiver;
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the game server");
            ReadThread readThread = new ReadThread(socket, this);
            readThread.start();
            WriteThread writeThread = new WriteThread(socket, this);
            writeThread.start();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    String getUserName() {
        return this.userName;
    }

    public synchronized void processPlayerMovement(String message) {
        this.message = message;

        notify();
        //notify();
    }

    public synchronized String getMessage() {
        if (message == null) {
            try {
                System.out.println("Writer go sleep");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = message;
        message = null;
       // notify();
        return result;
    }

    /*public synchronized void clearMessage() {
        message = null;
      //  try {
        //Мы отправили сообщение, пусть работает читатель
            notify();
        //System.out.println("Thread " + Thread.currentThread().getName() + " go sleep");
        // wait();
      *//* } catch (InterruptedException e) {
            e.printStackTrace();
        }*//*
    }*/

    public synchronized void setResponse(String response) {
        // try {
        System.out.println("Client read " + response);
        receiver.run(response);
        /*try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Мы получили, поэтому этот поток идет спать, нужно разбудить того, кто шлет
        //  System.out.println("Thread " + Thread.currentThread().getName() + " go sleep");
        //   this.response = response;
        // wait();
      /*  } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
 
 
   /* public static void main(String[] args) {
        GameClient client = new GameClient("192.168.36.1", PORT);
        client.execute();
    }*/
}