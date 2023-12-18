package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Random;

public class register extends Thread{

    @FXML ImageView logo1;
    @FXML ImageView logo2;

    @FXML TextField firstname;
    @FXML TextField lastname;
    @FXML TextField studentID;
    @FXML TextField Email;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML PasswordField rePassword;
    @FXML Label comment;

    public static String fName,lName,uName,sId, email, pass, rePass;
    public static String otp = Integer.toString(new Random().nextInt((999999-10000+1)+10000));
    GEmailSender gEmailSender = new GEmailSender();

    @FXML protected void onExitButtonClick(){
        javafx.application.Platform.exit();
    }

    @FXML void onBackToLoginPageClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();

    }

    @FXML void onSignUpButtonClick(ActionEvent event) throws IOException, SQLException {
        fName = firstname.getText();
        lName = lastname.getText();
        uName = username.getText();
        sId = studentID.getText();
        email = Email.getText();
        pass = password.getText();
        rePass = rePassword.getText();
        if (fName.isBlank() || lName.isBlank() || uName.isBlank() || sId.isBlank() || email.isBlank() || rePass.isBlank() || pass.isBlank()) {
            comment.setText("must fill all the field");
        }
        else {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            String query = "SELECT * FROM logindata WHERE username = '" + uName + "'";
            ResultSet resultSet = statement.executeQuery(query);
            boolean b= resultSet.next();
            System.out.println(resultSet.next());

            if (!pass.equals(rePass)) {
                comment.setText("*password don't match.");
            } else if (pass.length() < 8) {
                comment.setText("*password must be at least 8 character.");
            } else if (!email.matches("^[a-z]+[0-9]{6,7}+@[a-z]+\\.uiu\\.ac\\.bd$")) {
                comment.setText("must use your university mail.");
            } else if (!sId.matches("^[0-9]{9,10}$")) {
                comment.setText("must use your university student id.");
            }else if (b) {
                comment.setText("* Username is already taken.");


            } else if(!b){

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("otpVerification.fxml")));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("OTP Panel");
                stage.show();

                new Thread(() -> {
                    try {

                        Thread.sleep(100);
                        Platform.runLater(() -> {

                            try {
                                String to = email;
                                String from = "roxboy.tahmid@gmail.com";
                                String subject = "Verification Code.";
                                String text = "Your signup OTP is : " + otp;
                                gEmailSender.sendEmail(to, from, subject, text);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();







            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Unknown Error.\nPlease try again.");
                alert.show();
                comment.setText("");
            }
      //  comment.setText("");
        }
    }
    public void run(){

    }



}
