package com.mcspaskiy.utils;

/**
 * @author Mikhail Spaskiy
 */
public class Constants {
    private static final float SCALE = 1.0f;
    public static final String APPLICATION_PROPERTIES = "props/application.properties";
    public static final String SQL_CHECK_CREDENTIALS = "SELECT * FROM USERS WHERE USER_NAME = ? AND USER_PASS = ?";
    public static final int SCREEN_WITH = (int) (1440 * SCALE);
    public static final int SCREEN_HEIGHT = (int) (720* SCALE);
    public static final int PIECE_SIZE = (int) (55 * SCALE);
    public static final int CELL_SIZE = (int) (SCREEN_HEIGHT / 11);
    public static final String APP_TITLE = "Tablut";
    public static final int PORT = 3129;
}