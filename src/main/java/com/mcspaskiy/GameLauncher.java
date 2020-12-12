package com.mcspaskiy;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import static com.mcspaskiy.utils.Constants.*;

/**
 * @author Mikhail Spaskiy
 */
public class GameLauncher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.title = APP_TITLE;
        config.width = SCREEN_WITH;
        config.height = SCREEN_HEIGHT;

        new LwjglApplication(new TablutGame(), config);
    }
}
