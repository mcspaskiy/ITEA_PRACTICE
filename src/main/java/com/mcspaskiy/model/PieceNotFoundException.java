package com.mcspaskiy.model;

public class PieceNotFoundException extends RuntimeException {
    public PieceNotFoundException(String message) {
        super(message);
    }
}
