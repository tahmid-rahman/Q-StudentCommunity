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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;


public class loginPage  {
    @FXML private Label comment;
    @FXML private TextField Email;
    @FXML private PasswordField password;

    public static String CurrentUserName;
    public static String UserType;
    public static String E_mail;
    public static String Password;
    public static String PageName = "loginPage";


    @FXML protected void onExitButtonClick(){
        javafx.application.Platform.exit();
    }
    @FXML
    void onForgotPasswordButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("forgotPassword.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Password Recovery");
        stage.show();

    }


    @FXML protected void onLoginButtonClick(ActionEvent event){
        comment.setText("");
        if((!Email.getText().isBlank()) && (!password.getText().isBlank())){
//            if(Email.getText().length()<=9 && username.getText().length()>=8){
//                validLogin(Email.getText(),password.getText(),event);
//            }
//            else {
//                comment.setText("* User name must be your varsity ID. ");
//
//            }
            validLogin(Email.getText(),password.getText(),event);

        }
        else {
            comment.setText("You must fill all the filled");

        }
        Email.setText("");
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

    public void validLogin(String email,String pass,ActionEvent event){
      //  comment.setText(user + pass);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM logindata WHERE email ='" + email +"' AND password = '" + pass +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                  //  comment.setText("login Successful");

                    setUsername(email,pass);
                    loginButton(event);
                    break;



                }
                else {
                   // comment.setText("login failed");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Email or password is not correct.\nPlease try again.");
                    alert.show();
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Database Server is off.\nPlease try again latter.",ButtonType.CLOSE);
            alert.show();

        }


    }

    public void setUsername(String email,String pass){

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            String query = "SELECT username,email FROM logindata WHERE email='"+email+"' AND password ='"+pass+"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){CurrentUserName = resultSet.getString("username");}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("From login username is : "+CurrentUserName);
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