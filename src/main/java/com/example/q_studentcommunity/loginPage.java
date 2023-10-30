package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginPage implements Initializable {
    @FXML private Label comment;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private ChoiceBox <String> choice;
    private Button exit;






    @FXML protected void onLoginButtonClick(ActionEvent event){
        if((!username.getText().isBlank()) && (!password.getText().isBlank())){
//            if(username.getText().length()<=9 && username.getText().length()>=8){
//                validLogin(username.getText(),password.getText(),event);
//            }
//            else {
//                comment.setText("* User name must be your varsity ID. ");
//
//            }
            validLogin(username.getText(),password.getText(),event);

        }
        else {
            comment.setText("You must fill all the filled");

        }
        username.setText("");
        password.setText("");
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

    public void validLogin(String user,String pass,ActionEvent event){
      //  comment.setText(user + pass);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM logindata WHERE username ='" + user +"' AND password = '" + pass +"'";
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice.getItems().add("AuthorizeLogin");
        choice.getItems().add("Exit");
        choice.setOnAction(event -> {
            try {
                onChoiceBoxClick(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onChoiceBoxClick(ActionEvent event) throws IOException {
        String c = choice.getValue();
        if(c.equals("Exit")){
            javafx.application.Platform.exit();
        } else if (c.equals("AuthorizeLogin")) {
            onAuthorizeLoginButtonClick(event);
            
        }
    }
    public void onAuthorizeLoginButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("authorizerLogin.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Author Login Page");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void loginButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("StudentCommunity");
        stage.setScene(new Scene(root));
        stage.show();
    }

}