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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Random;

public class forgotPassword {
    @FXML
    TextField emailTF;
    @FXML
    Label comment;
    static String email;
    public static String otp = Integer.toString(new Random().nextInt((999999-10000+1)+10000));
    @FXML
    public void onSendButtonClick(ActionEvent event) throws IOException {
        email = emailTF.getText();
        if (email.isBlank()) {
            comment.setText("* must fill all the field");

        } else {
            try {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                Statement statement = connectDB.createStatement();
                String query = "SELECT * FROM logindata WHERE email = '" + email + "'";
                ResultSet resultSet = statement.executeQuery(query);

                if(resultSet.next()){
                   // System.out.println("value found");

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ResetPassword.fxml")));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Reset Password");
                    stage.show();

                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            Platform.runLater(() ->{
                                GEmailSender gEmailSender = new GEmailSender();
                                String to = email;
                                String from = "roxboy.tahmid@gmail.com";
                                String subject = "Welcome to QUEUE.";
                                String text = "Your \"Reset Password\" OTP is : " + otp;
                                gEmailSender.sendEmail(to, from, subject, text);
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }).start();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("User was not found.\nCreate an account first.");
                    alert.show();
                }


            } catch (Exception e) {
                e.getCause();
                e.printStackTrace();
            }

        }

    }
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
}
