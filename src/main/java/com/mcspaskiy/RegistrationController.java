package com.mcspaskiy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Connection;

public class RegistrationController {
    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfPassword;

    @FXML
    private Label lblResult;

    public void handleButtonAction(ActionEvent actionEvent) {
        //Read connection params
        DbConnectionParams dbConnectionParams = IOService.getInstance().readDbConnectionParams();

        //Create DB connection with params
        Connection conn = DbService.getInstance().getConnection(dbConnectionParams);

        //Check credentials
        boolean result = DbService.getInstance().checkCredentials(conn, tfLogin.getText(), tfPassword.getText());
        lblResult.setVisible(true);
        if (result) {
            lblResult.setTextFill(Paint.valueOf("#319900"));
            lblResult.setText("Login successful");
        } else {
            lblResult.setTextFill(Paint.valueOf("#ff6433"));
            lblResult.setText("Incorrect login or password");
        }
    }
}
