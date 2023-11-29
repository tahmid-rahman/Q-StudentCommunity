package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class otpVerification {
    @FXML
    private TextField otpTF;
    @FXML
    private Label error;
    register regi = new register();
    @FXML public void onOtpCheckButtonClick(ActionEvent event) throws IOException {
        if(otpTF.getText().equals(regi.otp)){
            try {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                Statement statement = connectDB.createStatement();
                String data = "INSERT INTO logindata (firstname, lastname, id, email, username, password) VALUES ('" + regi.fName + "','" + regi.lName + "','" + regi.sId + "','" + regi.email + "','" + regi.uName + "','" + regi.pass + "')";
                statement.executeUpdate(data);
            } catch (Exception e) {
                e.getCause();
                e.printStackTrace();
            }
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUpSuccessfulMassage.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

        }
        else{
            error.setText("OTP doesn't match.");
        }



    }
    @FXML protected void onExitButtonClick(){
        javafx.application.Platform.exit();
    }
    @FXML protected void onRegisterButtonClick(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Register Page");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }


    }
}
