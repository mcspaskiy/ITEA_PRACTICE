package com.mcspaskiy.io;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.mcspaskiy.db.DbConnectionParams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mcspaskiy.utils.Constants.APPLICATION_PROPERTIES;

/**
 * @author Mikhail Spaskiy
 */
public class IOService {
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

    /*public void load() {
        InputStream serviceAccount;

        serviceAccount = getClass().getResourceAsStream("/serviceAccountKey.json");


        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://tablut-23048-default-rtdb.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }*/

    public DbConnectionParams readDbConnectionParams() {
        Path path = Paths.get(APPLICATION_PROPERTIES);
        String dbUrl = null;
        String dbUser = null;
        String dbPass = null;
        try {
            Stream<String> lines = Files.lines(path);
            String appProperties = lines.collect(Collectors.joining("\n"));
            lines.close();
            String[] values = appProperties.split("\n");
            dbUrl = values[0].replace("db.url=", "");
            dbUser = values[1].replace("db.user=", "");
            dbPass = values[2].replace("db.password=", "");
            System.out.println("Connection params: " + "dbUrl = " + dbUrl + " dbUser = " + dbUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DbConnectionParams dbConnectionParams = new DbConnectionParams(dbUrl, dbUser, dbPass);
        return dbConnectionParams;
    }

    public AssetHolder getOrloadAssets() {
        if (assetHolder == null) {
            assetHolder = new AssetHolder();
            /* TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
            param.minFilter = TextureFilter.Linear;
            param.genMipMaps = true;*/
            // boardImage = new Texture(Gdx.files.internal("images/board.png"));
            //whitePieceImage = new Texture(Gdx.files.internal(("images/piece_white.png")));

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
          //  assetHolder.setMenuSound(assetManager.get("MenuMusic.wav", Music.class));
        }
        return assetHolder;
    }

    public void unloadAssets() {
        assetManager.dispose();
    }

  /*  public void loadAndr() {

    }*/
}
