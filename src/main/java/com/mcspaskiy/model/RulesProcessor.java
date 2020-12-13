package com.mcspaskiy.model;

import java.util.List;

public class RulesProcessor {
    private StaticItem[][] staticItemsOnBoard;

    public RulesProcessor() {
        staticItemsOnBoard = new StaticItem[9][9];
        staticItemsOnBoard[0][0] = new StaticItem(0, 0);
        staticItemsOnBoard[0][8] = new StaticItem(0, 8);
        staticItemsOnBoard[8][8] = new StaticItem(8, 8);
        staticItemsOnBoard[8][0] = new StaticItem(8, 0);
        staticItemsOnBoard[4][4] = new StaticItem(4, 4);
    }

    public void checkMovementResult(ItemType pieceType, int x, int y, ActiveItem[][] itemsOnBoard,
                                    List<ActiveItem> capturedWhitePieces, List<ActiveItem> capturedBlackPieces) {
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

    public int[][] getAvailMovements(int x, int y, ActiveItem[][] itemsOnBoard) {
        int[][] availMovements = new int[9][9];

        for (int i = x - 1; i >= 0; i--) {
            ActiveItem anotherPiece = itemsOnBoard[i][y];
            if (anotherPiece != null || staticItemsOnBoard[i][y] != null) {
                break;
            }
            availMovements[i][y] = 1;
            //putPieceOnBoard(ItemType.AVAIL_POS, i, y);
        }
        for (int i = x + 1; i < itemsOnBoard.length; i++) {
            ActiveItem anotherPiece = itemsOnBoard[i][y];
            if (anotherPiece != null || staticItemsOnBoard[i][y] != null) {
                break;
            }
            availMovements[i][y] = 1;
            //putPieceOnBoard(ItemType.AVAIL_POS, i, y);
        }
        for (int i = y - 1; i >= 0; i--) {
            ActiveItem anotherPiece = itemsOnBoard[x][i];
            if (anotherPiece != null || staticItemsOnBoard[x][i] != null) {
                break;
            }
            availMovements[x][i] = 1;
            //putPieceOnBoard(ItemType.AVAIL_POS, x, i);
        }
        for (int i = y + 1; i < itemsOnBoard.length; i++) {
            ActiveItem anotherPiece = itemsOnBoard[x][i];
            if (anotherPiece != null || staticItemsOnBoard[x][i] != null) {
                break;
            }
            availMovements[x][i] = 1;
            //putPieceOnBoard(ItemType.AVAIL_POS, x, i);
        }
        return availMovements;
    }
}
