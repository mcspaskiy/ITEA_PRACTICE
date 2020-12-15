package com.mcspaskiy.multiplayer;

import java.util.UUID;

public class Room {
    private UUID uuid;
    private String player1;
    private String player2;

    public Room() {
        //Generate random UUID
        uuid = UUID.randomUUID();
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}
