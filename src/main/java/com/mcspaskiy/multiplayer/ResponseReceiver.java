package com.mcspaskiy.multiplayer;

@FunctionalInterface
public interface ResponseReceiver {
    void run(String response);
}
