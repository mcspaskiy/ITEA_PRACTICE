package com.mcspaskiy.model;

@FunctionalInterface
public interface ResponseReceiver {
    void run(String response);
}
