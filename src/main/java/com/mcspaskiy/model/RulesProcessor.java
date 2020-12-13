package com.mcspaskiy.model;

import java.util.List;

public class RulesProcessor {
    private int[][] staticItemsOnBoard;

    public RulesProcessor() {
        staticItemsOnBoard = new int[9][9];
        staticItemsOnBoard[0][0] = 1;
        staticItemsOnBoard[0][8] = 1;
        staticItemsOnBoard[8][8] = 1;
        staticItemsOnBoard[8][0] = 1;
        staticItemsOnBoard[4][4] = 1;
    }

    public GameOverType processMovement(ItemType pieceType, int x, int y, ActiveItem[][] itemsOnBoard,
                                        List<ActiveItem> capturedWhitePieces, List<ActiveItem> capturedBlackPieces) {
        //First step. Check King victory
        if (pieceType == ItemType.WHITE_KING) {
            for (int i = 0; i < staticItemsOnBoard.length; i++) {
                for (int j = 0; j < staticItemsOnBoard.length; j++) {
                    if (staticItemsOnBoard[i][j] == 1 && x == i && y == j) {
                        return GameOverType.WIN_WHITE_KING_FINISHED;
                    }
                }
            }
        }

        GameOverType gameOverType = null;
        if (isEnemy(x - 1, y, pieceType, itemsOnBoard) && isFriend(x - 2, y, pieceType, itemsOnBoard)) {
            gameOverType = capture(x - 1, y, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }

        if (isEnemy(x + 1, y, pieceType, itemsOnBoard) && isFriend(x + 2, y, pieceType, itemsOnBoard)) {
            gameOverType = capture(x + 1, y, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }

        if (isEnemy(x, y - 1, pieceType, itemsOnBoard) && isFriend(x, y - 2, pieceType, itemsOnBoard)) {
            gameOverType = capture(x, y - 1, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }

        if (isEnemy(x, y + 1, pieceType, itemsOnBoard) && isFriend(x, y + 2, pieceType, itemsOnBoard)) {
            gameOverType = capture(x, y + 1, itemsOnBoard, capturedBlackPieces, capturedWhitePieces);
        }
        return gameOverType;
    }

    private GameOverType capture(int x, int y, ActiveItem[][] itemsOnBoard,
                                 List<ActiveItem> capturedBlackPieces, List<ActiveItem> capturedWhitePieces) {
        ActiveItem capturedPiece = itemsOnBoard[x][y];
        itemsOnBoard[x][y] = null;
        if (capturedPiece.getItemType() == ItemType.BLACK) {
            captureBlackPiece(capturedBlackPieces, capturedPiece);
        } else if (capturedPiece.getItemType() == ItemType.WHITE) {
            captureWhitePiece(capturedWhitePieces, capturedPiece);
        }
        if (capturedPiece.getItemType() == ItemType.WHITE_KING) {
            return GameOverType.WIN_BLACK_KING_CAPTURED;
        }
        return null;
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
        ActiveItem piece = itemsOnBoard[x][y];
        ItemType pieceType = piece.getItemType();
        int[][] availMovements = new int[9][9];

        for (int i = x - 1; i >= 0; i--) {
            ActiveItem anotherPiece = itemsOnBoard[i][y];
            if (anotherPiece != null || staticItemsOnBoard[i][y] == 1) {
                if (i == 4 && y == 4) {
                    continue;
                }
                if (pieceType == ItemType.WHITE_KING && staticItemsOnBoard[i][y] == 1) {
                    availMovements[i][y] = 1;
                }
                break;
            }
            availMovements[i][y] = 1;
        }

        for (int i = x + 1; i < itemsOnBoard.length; i++) {
            ActiveItem anotherPiece = itemsOnBoard[i][y];
            if (anotherPiece != null || staticItemsOnBoard[i][y] == 1) {
                if (i == 4 && y == 4) {
                    continue;
                }
                if (pieceType == ItemType.WHITE_KING && staticItemsOnBoard[i][y] == 1) {
                    availMovements[i][y] = 1;
                }
                break;
            }
            availMovements[i][y] = 1;
        }

        for (int i = y - 1; i >= 0; i--) {
            ActiveItem anotherPiece = itemsOnBoard[x][i];
            if (anotherPiece != null || staticItemsOnBoard[x][i] == 1) {
                if (x == 4 && i == 4) {
                    continue;
                }
                if (pieceType == ItemType.WHITE_KING && staticItemsOnBoard[x][i] == 1) {
                    availMovements[x][i] = 1;
                }
                break;
            }
            availMovements[x][i] = 1;
        }

        for (int i = y + 1; i < itemsOnBoard.length; i++) {
            ActiveItem anotherPiece = itemsOnBoard[x][i];
            if (anotherPiece != null || staticItemsOnBoard[x][i] == 1) {
                if (x == 4 && i == 4) {
                    continue;
                }
                if (pieceType == ItemType.WHITE_KING && staticItemsOnBoard[x][i] == 1) {
                    availMovements[x][i] = 1;
                }
                break;
            }
            availMovements[x][i] = 1;
        }
        return availMovements;
    }
}
