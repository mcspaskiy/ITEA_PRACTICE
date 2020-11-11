package com.mcspaskiy;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Main extends Application {
    public static final String PATH_INNER = "resources/readme.txt";
    public static final String PATH_OUTER = "misc/readme.txt";

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        String inner = loadFromInnerSource();
        String outer = loadFromOuterSource();
        initViews(primaryStage, inner, outer);
        primaryStage.show();
    }

    private void initViews(Stage primaryStage, String inner, String outer) {
        Label labelInner = new Label(inner);
        Label labelOuter = new Label(outer);
        VBox verticalLayout = new VBox(labelInner, labelOuter);
        verticalLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(verticalLayout, 300, 100);
        primaryStage.setScene(scene);
    }

    private String loadFromInnerSource() {
        URL url = this.getClass().getClassLoader().getResource(PATH_INNER);
        StringBuilder sbInner = new StringBuilder();
        try (InputStream inputStream = url.openStream()) {
            int i;
            while ((i = inputStream.read()) != -1) {
                sbInner.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbInner.toString();
    }

    private String loadFromOuterSource() {
        File file = new File(PATH_OUTER);
        StringBuilder sbOuter = new StringBuilder();
        try (InputStream io = new FileInputStream(file)) {
            int i;
            while ((i = io.read()) != -1) {
                sbOuter.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbOuter.toString();
    }
}