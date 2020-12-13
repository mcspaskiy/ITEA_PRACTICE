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
    private ActiveItem[][] itemsOnBoard;
    private StaticItem[][] staticItemsOnBoard;
    private List<ActiveItem> availPositions;
    private Stage stage;
    private ActiveItem selectedPiece;
    private List<ActiveItem> capturedWhitePieces;
    private List<ActiveItem> capturedBlackPieces;
    private RulesProcessor ruleProcessor;

    // private MovementProcessor movementProcessor;


    public Board(Stage stage) {
        ruleProcessor = new RulesProcessor();
        capturedWhitePieces = new ArrayList<>();
        capturedBlackPieces = new ArrayList<>();
        this.staticItemsOnBoard = new StaticItem[9][9];
        this.stage = stage;
        this.availPositions = new ArrayList<>();
        this.itemsOnBoard = new ActiveItem[9][9];
        //this.boardElementsOnBoard = new BoardElement[9][9];
        //   this.movementProcessor = MovementProcessor.getInstance();
        resetBoard();
    }

    public void resetBoard() {
        // stage.clear();
        for (int i = 0; i < itemsOnBoard.length; i++) {
            for (int j = 0; j < itemsOnBoard.length; j++) {
               /* if (itemsOnBoard[i][j] != null) {
                    itemsOnBoard[i][j].addAction();
                }*/
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

        staticItemsOnBoard[0][0] = new StaticItem(0, 0);
        staticItemsOnBoard[0][8] = new StaticItem(0, 8);
        staticItemsOnBoard[8][8] = new StaticItem(8, 8);
        staticItemsOnBoard[8][0] = new StaticItem(8, 0);
        staticItemsOnBoard[4][4] = new StaticItem(4, 4);
        //fillStage();
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
    private void onItemClick(ActiveItem item) {
        if (!item.isAlive()) {
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

            for (int i = x - 1; i >= 0; i--) {
                ActiveItem anotherPiece = itemsOnBoard[i][y];
                if (anotherPiece != null || staticItemsOnBoard[i][y] != null) {
                    break;
                }
                putPieceOnBoard(ItemType.AVAIL_POS, i, y);
            }
            for (int i = x + 1; i < itemsOnBoard.length; i++) {
                ActiveItem anotherPiece = itemsOnBoard[i][y];
                if (anotherPiece != null || staticItemsOnBoard[i][y] != null) {
                    break;
                }
                putPieceOnBoard(ItemType.AVAIL_POS, i, y);
            }
            for (int i = y - 1; i >= 0; i--) {
                ActiveItem anotherPiece = itemsOnBoard[x][i];
                if (anotherPiece != null || staticItemsOnBoard[x][i] != null) {
                    break;
                }
                putPieceOnBoard(ItemType.AVAIL_POS, x, i);
            }
            for (int i = y + 1; i < itemsOnBoard.length; i++) {
                ActiveItem anotherPiece = itemsOnBoard[x][i];
                if (anotherPiece != null || staticItemsOnBoard[x][i] != null) {
                    break;
                }
                putPieceOnBoard(ItemType.AVAIL_POS, x, i);
            }
        } else {
            //We here,  so we click on avail position. Movement implementation here.

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
            capturePieces(selectedPiece.getItemType(), newX, newY);
            selectedPiece = null;
        }
    }

    /**
     * It will be implemented after piece movement. If it's possible we will capture pieces
     */
    private void capturePieces(ItemType pieceType, int x, int y) {
        /**
         * Get new X,Y
         * Get current piece type
         * Get piece on x-1. If it enemy get piece at x-2. If it friend or static - capture
         * Get piece on x+1. If it enemy get piece at x-2. If it friend or static - capture
         * Get piece on y-1. If it enemy get piece at x-2. If it friend or static - capture
         * Get piece on y+1. If it enemy get piece at x-2. If it friend or static - capture
         */
        if (itemsOnBoard[x - 1][y] != null && itemsOnBoard[x - 1][y].getItemType() != pieceType) {
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

        if (itemsOnBoard[x + 1][y] != null && itemsOnBoard[x + 1][y].getItemType() != pieceType) {
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

        if (itemsOnBoard[x][y - 1] != null && itemsOnBoard[x][y - 1].getItemType() != pieceType) {
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

        if (itemsOnBoard[x][y + 1] != null && itemsOnBoard[x][y + 1].getItemType() != pieceType) {
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
