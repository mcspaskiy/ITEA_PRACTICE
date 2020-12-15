package com.mcspaskiy.multiplayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import static com.mcspaskiy.utils.Constants.PORT;

/**
 * @author Mikhail Spaskiy
 */
public class RoomService {
    private static RoomService instance;
    private Room room;
    Socket socket; //TODO close


    private RoomService() {
    }

    public static RoomService getInstance() {
        if (instance == null) {
            instance = new RoomService();
        }
        return instance;
    }

    public String connectToServer(String name, String ip) {
        InetAddress ipAddr = null;
        String result = null;
        try {
            ipAddr = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket(ipAddr, PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(name);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String answer = (String) in.readObject();

            String[] resultArray = answer.split(" ");
            if (resultArray.length == 1) {
                //it is uuid
                result = resultArray[0];
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public UUID createServer(String name, InetAddress ip) {
     /*   Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int connections = 0;
                try {
                    ServerSocket socket = new ServerSocket(PORT, 0, ip);
                    System.out.println("Server is start");
                    gameServer = new GameServer();
                    gameServer.addPlayer(name);
                    while (true) {
                        Socket accept = socket.accept();

                        new GameServer(socket.accept(), ++connections);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return null;*/
        return null;
    }

    /*public void move(String command, String playerName, int prevX, int prevY, int newX, int newY) {
        StringBuilder sb = new StringBuilder();
        sb.append(command)
                .append(" ")
                .append(playerName)
                .append(" ")
                .append(prevX)
                .append(" ")
                .append(prevY)
                .append(" ")
                .append(newX)
                .append(" ")
                .append(newY);

        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
