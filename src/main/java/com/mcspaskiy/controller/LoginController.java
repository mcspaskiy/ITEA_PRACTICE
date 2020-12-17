package com.mcspaskiy.controller;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mcspaskiy.TablutGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.mcspaskiy.utils.Constants.*;

public class LoginController {
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfIp;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblIp;

    public void handleButtonAction() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.title = APP_TITLE;
        config.width = SCREEN_WITH;
        config.height = SCREEN_HEIGHT;
        new LwjglApplication(new TablutGame(tfName.getText(), tfIp.getText()), config);
        Platform.exit();
    }
}
