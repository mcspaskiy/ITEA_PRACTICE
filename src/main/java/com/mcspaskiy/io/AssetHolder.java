package com.mcspaskiy.io;

import com.badlogic.gdx.graphics.Texture;

public class AssetHolder {
    private Texture boardImage;
    private Texture whitePieceImage;
    private Texture whiteKingPieceImage;
    private Texture blackPieceImage;
    private Texture possiblePlace;

    public Texture getBoardImage() {
        return boardImage;
    }

    public void setBoardImage(Texture boardImage) {
        this.boardImage = boardImage;
    }

    public Texture getWhitePieceImage() {
        return whitePieceImage;
    }

    public void setWhitePieceImage(Texture whitePieceImage) {
        this.whitePieceImage = whitePieceImage;
    }

    public Texture getWhiteKingPieceImage() {
        return whiteKingPieceImage;
    }

    public void setWhiteKingPieceImage(Texture whiteKingPieceImage) {
        this.whiteKingPieceImage = whiteKingPieceImage;
    }

    public Texture getBlackPieceImage() {
        return blackPieceImage;
    }

    public void setBlackPieceImage(Texture blackPieceImage) {
        this.blackPieceImage = blackPieceImage;
    }

    public Texture getAvailPosImage() {
        return possiblePlace;
    }

    public void setPossiblePlace(Texture possiblePlace) {
        this.possiblePlace = possiblePlace;
    }
}
