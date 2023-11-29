package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class ResetPassword {
    @FXML TextField otpTF;
    @FXML TextField pasTF;
    @FXML TextField rePasTF;
    @FXML Label comment;

    //forgotPassword fg = new forgotPassword();
    @FXML void onBackToLoginPageClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();

    }
    @FXML protected void onExitButtonClick(){
        javafx.application.Platform.exit();
    }

    @FXML public void onResetButtonClick(ActionEvent event){
        comment.setText("");
        String pass = pasTF.getText();
        String rePass = rePasTF.getText();
        if((!pass.isBlank()) && (!rePass.isBlank())){
          //  if(pass.length()>7){
            if(true){
                boolean b = updatePassword(pass);
                if (b){
                    try {

                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ResetPasswordSuccessful.fxml")));
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Successful");
                        stage.show();

                    } catch (Exception e) {
                        e.getCause();
                        e.printStackTrace();
                    }
                }
            }
            else {
                comment.setText("*Password must be 8 or more character.");

            }
         //   validLogin(Email.getText(),password.getText(),event);

        }
        else {
            comment.setText("You must fill all the filled");

        }


    }
    private Boolean updatePassword( String pass){
        if(otpTF.getText().equals(forgotPassword.otp)){
            try {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                Statement statement = connectDB.createStatement();
                String data = "UPDATE logindata SET password = " + pass + " WHERE email = '" + forgotPassword.email + "'";
                statement.executeUpdate(data);


            } catch (Exception e) {
                e.getCause();
                e.printStackTrace();
            }
            return true;


        }
        else{
            comment.setText("OTP doesn't match.");
            return false;
        }



    }
}
