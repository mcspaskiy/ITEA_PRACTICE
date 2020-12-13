package com.mcspaskiy.model;

/**
 * @author Mikhail Spaskiy
 */
public class MovementProcessor {
    private static MovementProcessor instance;
    private Board board;

   /* private MovementProcessor(Board board) {
        this.board = board;
    }*/

    public static MovementProcessor getInstance() {
        if (instance == null) {
            instance = new MovementProcessor();
        }
        return instance;
    }

    public void showMovementForPiece(int x, int y, ItemType pieceType) {

    }

}
