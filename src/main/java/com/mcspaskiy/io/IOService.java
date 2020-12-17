package com.mcspaskiy.io;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mcspaskiy.db.DbConnectionParams;

import java.io.InputStream;
import java.net.URL;

/**
 * @author Mikhail Spaskiy
 */
public class IOService {
    public static final String APPLICATION_PROPERTIES = "application.properties";

    private static IOService instance;
    private AssetManager assetManager;
    private AssetHolder assetHolder;

    private IOService() {
        this.assetManager = new AssetManager();
    }

    public static IOService getInstance() {
        if (instance == null) {
            instance = new IOService();
        }
        return instance;
    }

    public DbConnectionParams readDbConnectionParams() {
        URL resource = getClass().getClassLoader().getResource(APPLICATION_PROPERTIES);
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = resource.openStream()) {
            int c;
            while ((c = inputStream.read()) != -1) {
                sb.append((char) c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] values = sb.toString().split("\r\n");
        String dbUrl = values[0].replace("db.url=", "");
        String dbUser = values[1].replace("db.user=", "");
        String dbPass = values[2].replace("db.password=", "");
        System.out.println("Connection params: " + "dbUrl = " + dbUrl + " dbUser = " + dbUser);

        DbConnectionParams dbConnectionParams = new DbConnectionParams(dbUrl, dbUser, dbPass);
        return dbConnectionParams;
    }

    public AssetHolder getOrloadAssets() {
        if (assetHolder == null) {
            assetHolder = new AssetHolder();

            assetManager.load("images/board.png", Texture.class);
            assetManager.load("images/piece_white.png", Texture.class);
            assetManager.load("images/piece_black.png", Texture.class);
            assetManager.load("images/piece_white_king.png", Texture.class);
            assetManager.load("images/possible_place.png", Texture.class);

            //lock and make sure all assets are loaded
            assetManager.finishLoading();
            assetHolder.setBoardImage(assetManager.get("images/board.png", Texture.class));
            assetHolder.setWhitePieceImage(assetManager.get("images/piece_white.png", Texture.class));
            assetHolder.setBlackPieceImage(assetManager.get("images/piece_black.png", Texture.class));
            assetHolder.setWhiteKingPieceImage(assetManager.get("images/piece_white_king.png", Texture.class));
            assetHolder.setPossiblePlace(assetManager.get("images/possible_place.png", Texture.class));
        }
        return assetHolder;
    }

    public void unloadAssets() {
        assetManager.dispose();
    }
}
