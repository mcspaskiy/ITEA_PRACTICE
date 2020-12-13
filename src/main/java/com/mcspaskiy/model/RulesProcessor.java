package com.mcspaskiy.model;

import java.util.List;

public class RulesProcessor {
    public void checkMovementResult(ItemType pieceType, int x, int y, ActiveItem[][] itemsOnBoard,
                                    List<ActiveItem> capturedWhitePieces, List<ActiveItem> capturedBlackPieces) {
        //TODO: king can't capture the pieces

        if (x >= 2 && itemsOnBoard[x - 1][y] != null && itemsOnBoard[x - 1][y].getItemType() != pieceType) {
            //The piece is present and it's an enemy
            if (itemsOnBoard[x - 2][y] != null && itemsOnBoard[x - 2][y].getItemType() == pieceType) {
                //The piece is present and it's a friend
                ActiveItem capturedPiece = itemsOnBoard[x - 1][y];
                itemsOnBoard[x - 1][y] = null;
                if (capturedPiece.getItemType() == ItemType.BLACK) {
                    capturedBlackPieces.add(capturedPiece);
                    capturedPiece.spritePos(11, capturedBlackPieces.size());
                    capturedPiece.setAlive(false);
                } else if (capturedPiece.getItemType() == ItemType.WHITE) {
                    capturedWhitePieces.add(capturedPiece);
                    capturedPiece.spritePos(-3, capturedWhitePieces.size());
                    capturedPiece.setAlive(false);
                }
            }
        }

        if (x < itemsOnBoard.length - 2 && itemsOnBoard[x + 1][y] != null && itemsOnBoard[x + 1][y].getItemType() != pieceType) {
            //The piece is present and it's an enemy
            if (itemsOnBoard[x + 2][y] != null && itemsOnBoard[x + 2][y].getItemType() == pieceType) {
                //The piece is present and it's a friend
                ActiveItem capturedPiece = itemsOnBoard[x + 1][y];
                itemsOnBoard[x + 1][y] = null;
                if (capturedPiece.getItemType() == ItemType.BLACK) {
                    capturedBlackPieces.add(capturedPiece);
                    capturedPiece.spritePos(11, capturedBlackPieces.size());
                    capturedPiece.setAlive(false);
                } else if (capturedPiece.getItemType() == ItemType.WHITE) {
                    capturedWhitePieces.add(capturedPiece);
                    capturedPiece.spritePos(-3, capturedWhitePieces.size());
                    capturedPiece.setAlive(false);
                }
            }
        }

        if (y >= 2 && itemsOnBoard[x][y - 1] != null && itemsOnBoard[x][y - 1].getItemType() != pieceType) {
            //The piece is present and it's an enemy
            if (itemsOnBoard[x][y - 2] != null && itemsOnBoard[x][y - 2].getItemType() == pieceType) {
                //The piece is present and it's a friend
                ActiveItem capturedPiece = itemsOnBoard[x][y - 1];
                itemsOnBoard[x][y - 1] = null;
                if (capturedPiece.getItemType() == ItemType.BLACK) {
                    capturedBlackPieces.add(capturedPiece);
                    capturedPiece.spritePos(11, capturedBlackPieces.size());
                    capturedPiece.setAlive(false);
                } else if (capturedPiece.getItemType() == ItemType.WHITE) {
                    capturedWhitePieces.add(capturedPiece);
                    capturedPiece.spritePos(-3, capturedWhitePieces.size());
                    capturedPiece.setAlive(false);
                }
            }
        }

        if (y < itemsOnBoard.length -2 && itemsOnBoard[x][y + 1] != null && itemsOnBoard[x][y + 1].getItemType() != pieceType) {
            //The piece is present and it's an enemy
            if (itemsOnBoard[x][y + 2] != null && itemsOnBoard[x][y + 2].getItemType() == pieceType) {
                //The piece is present and it's a friend
                ActiveItem capturedPiece = itemsOnBoard[x][y + 1];
                itemsOnBoard[x][y + 1] = null;
                if (capturedPiece.getItemType() == ItemType.BLACK) {
                    capturedBlackPieces.add(capturedPiece);
                    capturedPiece.spritePos(11, capturedBlackPieces.size());
                    capturedPiece.setAlive(false);
                } else if (capturedPiece.getItemType() == ItemType.WHITE) {
                    capturedWhitePieces.add(capturedPiece);
                    capturedPiece.spritePos(-3, capturedWhitePieces.size());
                    capturedPiece.setAlive(false);
                }
            }
        }
    }
}
