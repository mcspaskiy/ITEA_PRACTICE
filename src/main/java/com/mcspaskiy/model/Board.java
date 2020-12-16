package com.mcspaskiy.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mcspaskiy.io.AssetHolder;
import com.mcspaskiy.io.IOService;
import com.mcspaskiy.multiplayer.Movable;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private ActiveItem[][] itemsOnBoard;

    private List<ActiveItem> availPositions;
    private Stage stage;
    private ActiveItem selectedPiece;
    private List<ActiveItem> capturedWhitePieces;
    private List<ActiveItem> capturedBlackPieces;
    private RulesProcessor ruleProcessor;
    private String playerName;
    private Movable movable;
    private int queue;
    private boolean playerTurn;

    public Board(Stage stage, String playerName, int queue, Movable movable) {
        if (queue == 0) {
            playerTurn = true;
        }
        this.playerName = playerName;
        this.ruleProcessor = new RulesProcessor();
        this.capturedWhitePieces = new ArrayList<>();
        this.capturedBlackPieces = new ArrayList<>();
        this.movable = movable;
        this.stage = stage;
        this.availPositions = new ArrayList<>();
        this.itemsOnBoard = new ActiveItem[9][9];
        this.queue = queue;
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < itemsOnBoard.length; i++) {
            for (int j = 0; j < itemsOnBoard.length; j++) {
                itemsOnBoard[i][j] = null;
            }
        }
        capturedWhitePieces.clear();
        capturedBlackPieces.clear();

        putPieceOnBoard(ItemType.BLACK, 3, 0);
        putPieceOnBoard(ItemType.BLACK, 4, 0);
        putPieceOnBoard(ItemType.BLACK, 5, 0);
        putPieceOnBoard(ItemType.BLACK, 4, 1);

        putPieceOnBoard(ItemType.BLACK, 3, 8);
        putPieceOnBoard(ItemType.BLACK, 4, 8);
        putPieceOnBoard(ItemType.BLACK, 5, 8);
        putPieceOnBoard(ItemType.BLACK, 4, 7);

        putPieceOnBoard(ItemType.BLACK, 0, 3);
        putPieceOnBoard(ItemType.BLACK, 0, 4);
        putPieceOnBoard(ItemType.BLACK, 0, 5);
        putPieceOnBoard(ItemType.BLACK, 1, 4);

        putPieceOnBoard(ItemType.BLACK, 8, 3);
        putPieceOnBoard(ItemType.BLACK, 8, 4);
        putPieceOnBoard(ItemType.BLACK, 8, 5);
        putPieceOnBoard(ItemType.BLACK, 7, 4);

        putPieceOnBoard(ItemType.WHITE, 3, 4);
        putPieceOnBoard(ItemType.WHITE, 5, 4);
        putPieceOnBoard(ItemType.WHITE, 4, 3);
        putPieceOnBoard(ItemType.WHITE, 4, 5);

        putPieceOnBoard(ItemType.WHITE_KING, 4, 4);
    }

    /**
     * Create and put piece on board by piece type and relative position
     */
    private void putPieceOnBoard(ItemType pieceType, int x, int y) {
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
        ActiveItem piece = new ActiveItem(texture, pieceType, x, y, this::onItemClick);
        itemsOnBoard[x][y] = piece;
        stage.addActor(piece);

        if (pieceType == ItemType.AVAIL_POS) {
            availPositions.add(piece);
        }
    }

    /**
     * Use to get X and Y relative position on board
     */
    private int[] getPiecePosition(ActiveItem piece) {
        for (int i = 0; i < itemsOnBoard.length; i++) {
            for (int j = 0; j < itemsOnBoard.length; j++) {
                if (piece.equals(itemsOnBoard[i][j])) {
                    return new int[]{i, j};
                }
            }
        }
        throw new PieceNotFoundException("Incorrect piece on Board");
    }

    /**
     * Describes selection and movement behaviour
     */
    private void onItemClick(ActiveItem item) {
        if (!item.isAlive()) {
            return;
        }
        if (queue == 0 && (item.getItemType() == ItemType.WHITE_KING || item.getItemType() == ItemType.WHITE)) {
            return;
        }
        if (queue == 1 && item.getItemType() == ItemType.BLACK) {
            return;
        }
        if (!playerTurn) {
            return;
        }
        Gdx.app.log("Touch down asset with name ", item.getItemType().toString());
        if (item.getItemType() != ItemType.AVAIL_POS) {
            selectedPiece = item;
            //We need to remove actors from the board view and clear list of items
            clearAvailPositions();
            //We select available position on the board
            int[] piecePos = getPiecePosition(item);
            int x = piecePos[0];
            int y = piecePos[1];
            System.out.println(x + ", " + y);
            int[][] availMovements = ruleProcessor.getAvailMovements(x, y, itemsOnBoard);
            for (int i = 0; i < availMovements.length; i++) {
                for (int j = 0; j < availMovements.length; j++) {
                    if (availMovements[i][j] == 1) {
                        putPieceOnBoard(ItemType.AVAIL_POS, i, j);
                    }
                }
            }
        } else {
            //MOVEMENT
            //We here, so we click on avail position. Movement implementation here.
            //Receiving from position
            int[] piecePosPrev = getPiecePosition(selectedPiece);
            int prevX = piecePosPrev[0];
            int prevY = piecePosPrev[1];

            //Receiving new position by avail
            int[] piecePosNew = getPiecePosition(item);
            int newX = piecePosNew[0];
            int newY = piecePosNew[1];

            itemsOnBoard[prevX][prevY] = null;
            itemsOnBoard[newX][newY] = selectedPiece;
            selectedPiece.spritePos(newX, newY);
            clearAvailPositions();
            processMovement(selectedPiece.getItemType(), newX, newY);
            selectedPiece = null;

            playerTurn = !playerTurn;
            movable.run("movement", playerName, prevX, prevY, newX, newY, playerTurn);
        }
    }

    public void moveItemByCoords(int prevX, int prevY, int newX, int newY) {
        selectedPiece = itemsOnBoard[prevX][prevY];
        itemsOnBoard[prevX][prevY] = null;
        itemsOnBoard[newX][newY] = selectedPiece;
        selectedPiece.spritePos(newX, newY);
        processMovement(selectedPiece.getItemType(), newX, newY);
        selectedPiece = null;
    }

    /**
     * It will be implemented after piece movement. If it's possible we will capture pieces
     */
    private void processMovement(ItemType pieceType, int x, int y) {
        GameOverType gameOverType = ruleProcessor.processMovement(pieceType, x, y, itemsOnBoard, capturedWhitePieces, capturedBlackPieces);
        if (gameOverType != null) {
            Gdx.app.log("GAME OVER: ", gameOverType.toString());
        }
    }

    private void clearAvailPositions() {
        for (ActiveItem availPosition : availPositions) {
            availPosition.addAction(Actions.removeActor());
        }
        for (int i = 0; i < itemsOnBoard.length; i++) {
            for (int j = 0; j < itemsOnBoard.length; j++) {
                if (itemsOnBoard[i][j] != null && itemsOnBoard[i][j].getItemType() == ItemType.AVAIL_POS) {
                    itemsOnBoard[i][j] = null;
                }
            }
        }
        availPositions.clear();
    }
}
