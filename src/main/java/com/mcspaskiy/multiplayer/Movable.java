package com.mcspaskiy.multiplayer;

@FunctionalInterface
public interface Movable {
    void run(String command, String playerName, int prevX, int prevY, int newX, int newY, boolean playerTurn);
}
