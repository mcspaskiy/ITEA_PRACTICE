package com.mcspaskiy;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class App extends Application {
    public static String INNER_IMAGE_NAME = "gosling_v1.jpg";
    public static String INNER_MESSAGES_NAME = "messages.txt";

    public static String OUTER_IMAGE_NAME = "addons/gosling_v2.jpg";
    public static String OUTER_MESSAGES_NAME = "addons/messages.txt";

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
        labelInner.setPrefWidth(500);
        Label labelOuter = new Label(outer);
        labelInner.setPrefWidth(500);
        Image imgInner = new Image(INNER_IMAGE_NAME);
        Image imgOuter = new Image(new File(OUTER_IMAGE_NAME).toURI().toString());
        ImageView ivInner = new ImageView(imgInner);
        ImageView ivOuter = new ImageView(imgOuter);

        HBox imgHorizontalLayout = new HBox(ivInner, ivOuter);
        imgHorizontalLayout.setAlignment(Pos.CENTER);
        HBox lblHorizontalLayout = new HBox(labelInner, labelOuter);
        lblHorizontalLayout.setAlignment(Pos.CENTER);
        VBox verticalLayout = new VBox(imgHorizontalLayout, lblHorizontalLayout);
        verticalLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(verticalLayout, 1000, 524);
        primaryStage.setScene(scene);
    }

    private String loadFromInnerSource() {
        URL url = this.getClass().getClassLoader().getResource(INNER_MESSAGES_NAME);
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
        File file = new File(OUTER_MESSAGES_NAME);
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