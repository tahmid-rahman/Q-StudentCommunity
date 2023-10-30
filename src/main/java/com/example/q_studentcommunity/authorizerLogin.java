package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class authorizerLogin {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label comment;
    @FXML protected void onLoginButtonClick(ActionEvent event){
        if((!username.getText().isBlank()) && (!password.getText().isBlank())){

            validLogin(username.getText(),password.getText(),event);

        }
        else {
            comment.setText("You must fill all the filled");

        }
        username.setText("");
        password.setText("");

    }
    public void validLogin(String user,String pass,ActionEvent event){
        //  comment.setText(user + pass);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM adminlogindata WHERE username ='" + user +"' AND password = '" + pass +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                    //  comment.setText("login Successful");
                    loginButton(event);

                }
                else {
                    comment.setText("login failed");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }

    }

    @FXML protected void onExitButtonClick(){
        javafx.application.Platform.exit();
    }
    @FXML public void onBackToLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();
    }
    public void loginButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPanel.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
