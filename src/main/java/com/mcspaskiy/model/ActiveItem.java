package com.mcspaskiy.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import static com.mcspaskiy.utils.Constants.SCREEN_HEIGHT;
import static com.mcspaskiy.utils.Constants.SCREEN_WITH;

public class ActiveItem extends Actor {
    private ItemType pieceType;
    private Sprite sprite;
    private boolean alive;
    private static final int PIECE_SIZE = 55 / 2;
    private static final int CELL_SIZE = SCREEN_HEIGHT / 11;


    public ActiveItem(Texture texture, ItemType pieceType, int x, int y, OnPieceClickHandler eventHandler) {
        this.sprite = new Sprite(texture);
        sprite.setSize(PIECE_SIZE, PIECE_SIZE);
        this.pieceType = pieceType;
        alive = true;
        spritePos(x, y);
        setTouchable(Touchable.enabled);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                eventHandler.run(ActiveItem.this);
                return true;
            }
        });
    }

    public void spritePos(int x, int y) {
        float posX = (float) (SCREEN_WITH / 2 - PIECE_SIZE / 2 - 4 * CELL_SIZE + x * CELL_SIZE);
        float posY = (float) ((y + 1) * CELL_SIZE + (CELL_SIZE - PIECE_SIZE) / 2);
        sprite.setPosition(posX, posY);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public ItemType getItemType() {
        return pieceType;
    }

    public void setPieceType(ItemType pieceType) {
        this.pieceType = pieceType;
    }

    public void setAlive(boolean value) {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

}