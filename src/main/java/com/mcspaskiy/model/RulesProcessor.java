package com.mcspaskiy.model;

import java.util.List;

public class RulesProcessor {
    public void checkMovementResult(ItemType pieceType, int x, int y, ActiveItem[][] itemsOnBoard,
                                    List<ActiveItem> capturedWhitePieces, List<ActiveItem> capturedBlackPieces) {
        //TODO: king can't capture the pieces
        if (isEnemy(x - 1, y, pieceType, itemsOnBoard) && isFriend(x - 2, y, pieceType, itemsOnBoard)) {
            capture(x - 1, y, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }

        if (isEnemy(x + 1, y, pieceType, itemsOnBoard) && isFriend(x + 2, y, pieceType, itemsOnBoard)) {
            capture(x + 1, y, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }

        if (isEnemy(x, y - 1, pieceType, itemsOnBoard) && isFriend(x, y - 2, pieceType, itemsOnBoard)) {
            capture(x, y - 1, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }

        if (isEnemy(x, y + 1, pieceType, itemsOnBoard) && isFriend(x, y + 2, pieceType, itemsOnBoard)) {
            capture(x, y + 1, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }
    }

    private void capture(int x, int y, ActiveItem[][] itemsOnBoard,
                         List<ActiveItem> capturedBlackPieces, List<ActiveItem> capturedWhitePieces) {
        ActiveItem capturedPiece = itemsOnBoard[x][y];
        itemsOnBoard[x][y] = null;
        if (capturedPiece.getItemType() == ItemType.BLACK) {
            captureBlackPiece(capturedBlackPieces, capturedPiece);
        } else if (capturedPiece.getItemType() == ItemType.WHITE) {
            captureWhitePiece(capturedWhitePieces, capturedPiece);
        }
    }

    private boolean isEnemy(int x, int y, ItemType firstType, ActiveItem[][] itemsOnBoard) {
        boolean step1 = x >= 0 && x <= itemsOnBoard.length - 1 && y >= 0 && y <= itemsOnBoard.length - 1 && itemsOnBoard[x][y] != null;
        if (step1 == false) {
            return false;
        }
        ItemType secondType = itemsOnBoard[x][y].getItemType();
        boolean step2 = false;
        if (firstType == ItemType.BLACK && (secondType == ItemType.WHITE || secondType == ItemType.WHITE_KING)) {
            step2 = true;
        }
        if ((firstType == ItemType.WHITE || firstType == ItemType.WHITE_KING) && secondType == ItemType.BLACK) {
            step2 = true;
        }
        return step2;
    }

    private boolean isFriend(int x, int y, ItemType firstType, ActiveItem[][] itemsOnBoard) {
        boolean step1 = x >= 0 && x <= itemsOnBoard.length - 1 && y >= 0 && y <= itemsOnBoard.length - 1 && itemsOnBoard[x][y] != null;
        if (step1 == false) {
            return false;
        }
        ItemType thirdType = itemsOnBoard[x][y].getItemType();
        boolean step2 = false;
        if (firstType == ItemType.BLACK && thirdType == ItemType.BLACK) {
            step2 = true;
        }
        if ((firstType == ItemType.WHITE || firstType == ItemType.WHITE_KING) && (thirdType == ItemType.WHITE || thirdType == ItemType.WHITE_KING)) {
            step2 = true;
        }
        return step2;
    }

    private void captureBlackPiece(List<ActiveItem> capturedBlackPieces, ActiveItem capturedPiece) {
        capturedBlackPieces.add(capturedPiece);
        capturedPiece.spritePos(11, capturedBlackPieces.size());
        capturedPiece.setAlive(false);
    }

    private void captureWhitePiece(List<ActiveItem> capturedWhitePieces, ActiveItem capturedPiece) {
        capturedWhitePieces.add(capturedPiece);
        capturedPiece.spritePos(-3, capturedWhitePieces.size());
        capturedPiece.setAlive(false);
    }
}
