package com.mcspaskiy;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = this.getClass().getClassLoader().getResource("resources/readme.txt");
        File file = new File("misc/readme.txt");

        Label labelInner = new Label();
        Label labelOuter = new Label();

        VBox verticalLayout = new VBox(labelInner, labelOuter);
        verticalLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(verticalLayout, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        StringBuilder sb1 = new StringBuilder();
        try (InputStream inputStream = url.openStream()) {
            int i;
            while ((i = inputStream.read()) != -1) {
                sb1.append((char) i);
            }
        }


        StringBuilder sb3 = new StringBuilder();
        try (InputStream io = new FileInputStream(file.getAbsolutePath())) {
            int i;
            while ((i = io.read()) != -1) {
                sb3.append((char) i);
            }
        }


        labelInner.setText(sb1.toString());
        labelOuter.setText(sb3.toString());
    }
}