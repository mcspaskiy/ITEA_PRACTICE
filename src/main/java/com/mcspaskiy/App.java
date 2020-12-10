package com.mcspaskiy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * создать в ФХ интерфейс формы авторизации с подключением к базе и собрать мейвеном,
 * чтобы джарник заппускался под дабл клику
 */
public class App extends Application {

    public static void main(String[] args) {

        DbConnectionParams dbConnectionParams = IOService.getInstance().readDbConnectionParams();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


      /*  try {
            Class<?> aClass = Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
*/
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/login_layout.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
