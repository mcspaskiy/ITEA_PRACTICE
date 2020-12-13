package com.mcspaskiy.model;

@FunctionalInterface
public interface OnPieceClickHandler {
    void run(ActiveItem piece);
}
