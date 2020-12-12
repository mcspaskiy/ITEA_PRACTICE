package com.mcspaskiy.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mcspaskiy.io.AssetHolder;
import com.mcspaskiy.io.IOService;

public class Board {
    private Piece[][] piecesOnBoard;
    private BoardElement[][] boardElementsOnBoard;


    public Board() {
        this.piecesOnBoard = new Piece[9][9];
        this.boardElementsOnBoard = new BoardElement[9][9];
        resetBoard();
    }

    public void resetBoard() {
        AssetHolder assets = IOService.getInstance().getOrloadAssets();

        piecesOnBoard[3][0] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 3, 0);
        piecesOnBoard[4][0] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 4, 0);
        piecesOnBoard[5][0] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 5, 0);
        piecesOnBoard[4][1] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 4, 1);

        piecesOnBoard[3][8] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 3, 8);
        piecesOnBoard[4][8] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 4, 8);
        piecesOnBoard[5][8] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 5, 8);
        piecesOnBoard[4][7] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 4, 7);

        piecesOnBoard[0][3] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 0, 3);
        piecesOnBoard[0][4] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 0, 4);
        piecesOnBoard[0][5] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 0, 5);
        piecesOnBoard[1][4] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 1, 4);

        piecesOnBoard[8][3] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 8, 3);
        piecesOnBoard[8][4] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 8, 4);
        piecesOnBoard[8][5] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 8, 5);
        piecesOnBoard[7][4] = new Piece(assets.getBlackPieceImage(), PieceType.BLACK, 7, 4);

        piecesOnBoard[3][4] = new Piece(assets.getWhitePieceImage(), PieceType.WHITE, 3, 4);
        piecesOnBoard[5][4] = new Piece(assets.getWhitePieceImage(), PieceType.WHITE, 5, 4);
        piecesOnBoard[4][3] = new Piece(assets.getWhitePieceImage(), PieceType.WHITE, 4, 3);
        piecesOnBoard[4][5] = new Piece(assets.getWhitePieceImage(), PieceType.WHITE, 4, 5);

        piecesOnBoard[4][4] = new Piece(assets.getWhiteKingPieceImage(), PieceType.WHITE_KING, 4, 4);
    }

    public void fillStage(Stage stage) {
        for (int i = 0; i < piecesOnBoard.length; i++) {
            for (int j = 0; j < piecesOnBoard.length; j++) {
                if (piecesOnBoard[i][j] != null) {
                    stage.addActor(piecesOnBoard[i][j]);
                }
            }
        }
    }
}
