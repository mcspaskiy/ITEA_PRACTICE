package com.mcspaskiy.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mcspaskiy.io.AssetHolder;
import com.mcspaskiy.io.IOService;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] piecesOnBoard;
    private BoardElement[][] boardElementsOnBoard;
    private List<Piece> availPositions;
    private Stage stage;
    private Piece selectedPiece;
    // private MovementProcessor movementProcessor;


    public Board(Stage stage) {
        this.stage = stage;
        this.availPositions = new ArrayList<>();
        this.piecesOnBoard = new Piece[9][9];
        this.boardElementsOnBoard = new BoardElement[9][9];
        //   this.movementProcessor = MovementProcessor.getInstance();
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < piecesOnBoard.length; i++) {
            for (int j = 0; j < piecesOnBoard.length; j++) {
                piecesOnBoard[i][j] = null;
            }
        }
        putPieceOnBoard(PieceType.BLACK, 3, 0);
        putPieceOnBoard(PieceType.BLACK, 4, 0);
        putPieceOnBoard(PieceType.BLACK, 5, 0);
        putPieceOnBoard(PieceType.BLACK, 4, 1);

        putPieceOnBoard(PieceType.BLACK, 3, 8);
        putPieceOnBoard(PieceType.BLACK, 4, 8);
        putPieceOnBoard(PieceType.BLACK, 5, 8);
        putPieceOnBoard(PieceType.BLACK, 4, 7);

        putPieceOnBoard(PieceType.BLACK, 0, 3);
        putPieceOnBoard(PieceType.BLACK, 0, 4);
        putPieceOnBoard(PieceType.BLACK, 0, 5);
        putPieceOnBoard(PieceType.BLACK, 1, 4);

        putPieceOnBoard(PieceType.BLACK, 8, 3);
        putPieceOnBoard(PieceType.BLACK, 8, 4);
        putPieceOnBoard(PieceType.BLACK, 8, 5);
        putPieceOnBoard(PieceType.BLACK, 7, 4);

        putPieceOnBoard(PieceType.WHITE, 3, 4);
        putPieceOnBoard(PieceType.WHITE, 5, 4);
        putPieceOnBoard(PieceType.WHITE, 4, 3);
        putPieceOnBoard(PieceType.WHITE, 4, 5);

        putPieceOnBoard(PieceType.WHITE_KING, 4, 4);
        //fillStage();
    }

    /**
     * Create and put piece on board by piece type and relative position
     */
    private void putPieceOnBoard(PieceType pieceType, int x, int y) {
        AssetHolder assets = IOService.getInstance().getOrloadAssets();
        Texture texture = null;
        switch (pieceType) {
            case WHITE:
                texture = assets.getWhitePieceImage();
                break;
            case WHITE_KING:
                texture = assets.getWhiteKingPieceImage();
                break;
            case BLACK:
                texture = assets.getBlackPieceImage();
                break;
            case AVAIL_POS:
                texture = assets.getAvailPosImage();
                break;
        }
        Piece piece = new Piece(texture, pieceType, x, y, this::onPieceClick);
        piecesOnBoard[x][y] = piece;
        stage.addActor(piece);

        if (pieceType == PieceType.AVAIL_POS) {
            availPositions.add(piece);
        }
    }

    /**
     * Use to get X and Y relative position on board
     */
    private int[] getPiecePosition(Piece piece) {
        for (int i = 0; i < piecesOnBoard.length; i++) {
            for (int j = 0; j < piecesOnBoard.length; j++) {
                if (piece.equals(piecesOnBoard[i][j])) {
                    return new int[]{i, j};
                }
            }
        }
        throw new PieceNotFoundException("Incorrect piece on Board");
    }

    /**
     * Callback from click event on piece
     */


   /* private void putAvailPositionOnBoard(int x, int y) {
        putPieceOnBoard(PieceType.AVAIL_POS, 4, 4);

        //AssetHolder assets = IOService.getInstance().getOrloadAssets();
        //Texture texture = assets.getPossiblePlace();
       // AvailPosition availPosition = new AvailPosition(texture, PieceType.AVAIL_POS, x, y, this::onPieceClick);
        availPositions.add(availPosition);
        stage.addActor(availPosition);
        //piecesOnBoard[x][y] =
    }*/

   /* private Piece getPieceByPosition(int x, int y) {
        for (int i = 0; i < piecesOnBoard.length; i++) {
            for (int j = 0; j < piecesOnBoard.length; j++) {
                return piecesOnBoard[][]
            }
        }
    }*/

    /**
     * Add all pieces to stage as actors. Once on start game
     */
/*    public void fillStage() {
        for (int i = 0; i < piecesOnBoard.length; i++) {
            for (int j = 0; j < piecesOnBoard.length; j++) {
                if (piecesOnBoard[i][j] != null) {
                    stage.addActor(piecesOnBoard[i][j]);
                }
            }
        }
    }*/

    /**
     * Describes selection and movement behaviour
     */
    private void onPieceClick(Piece piece) {
        Gdx.app.log("Touch down asset with name ", piece.getPieceType().toString());
        if (piece.getPieceType() != PieceType.AVAIL_POS) {
            selectedPiece = piece;

            //We need to remove actors from the board view and clear list of items
            clearAvailPositions();

            //We select available position on the board
            int[] piecePos = getPiecePosition(piece);
            int x = piecePos[0];
            int y = piecePos[1];
            System.out.println(x + ", " + y);

            for (int i = x - 1; i >= 0; i--) {
                Piece anotherPiece = piecesOnBoard[i][y];
                if (anotherPiece != null) {
                    break;
                }
                putPieceOnBoard(PieceType.AVAIL_POS, i, y);
            }
            for (int i = x + 1; i < piecesOnBoard.length; i++) {
                Piece anotherPiece = piecesOnBoard[i][y];
                if (anotherPiece != null) {
                    break;
                }
                putPieceOnBoard(PieceType.AVAIL_POS, i, y);
            }
            for (int i = y - 1; i >= 0; i--) {
                Piece anotherPiece = piecesOnBoard[x][i];
                if (anotherPiece != null) {
                    break;
                }
                putPieceOnBoard(PieceType.AVAIL_POS, x, i);
            }
            for (int i = y + 1; i < piecesOnBoard.length; i++) {
                Piece anotherPiece = piecesOnBoard[x][i];
                if (anotherPiece != null) {
                    break;
                }
                putPieceOnBoard(PieceType.AVAIL_POS, x, i);
            }
        } else {
            //We here,  so we click on avail position. Movement implementation here.

            //Receiving from position
            int[] piecePosPrev = getPiecePosition(selectedPiece);
            int prevX = piecePosPrev[0];
            int prevY = piecePosPrev[1];

            //Receiving new position by avail
            int[] piecePosNew = getPiecePosition(piece);
            int newX = piecePosNew[0];
            int newY = piecePosNew[1];

            piecesOnBoard[prevX][prevY] = null;
            piecesOnBoard[newX][newY] = selectedPiece;
            selectedPiece.spritePos(newX, newY);
            selectedPiece = null;
            clearAvailPositions();
        }
    }

    private void clearAvailPositions() {
        for (Piece availPosition : availPositions) {
            availPosition.addAction(Actions.removeActor());
        }
        for (int i = 0; i < piecesOnBoard.length; i++) {
            for (int j = 0; j < piecesOnBoard.length; j++) {
                if (piecesOnBoard[i][j] != null && piecesOnBoard[i][j].getPieceType() == PieceType.AVAIL_POS) {
                    piecesOnBoard[i][j] = null;
                }
            }
        }
        availPositions.clear();
    }
}
