package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.q_studentcommunity.loginPage.CurrentUserName;
public class settingPage implements Initializable {
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField user;
    @FXML TextField password;
    @FXML Label comment;
    @FXML void onLogoutButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();
    }
    @FXML protected void onOpinionPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onHelpPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onChatsPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chatPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onProfilePageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onSettingPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settingPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onResourcePageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resourcePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML
    private Label username;
    @FXML private ImageView imageView;
    Stage stage;
    File selectedFile = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // username.setText(CurrentUserName);
        SetUsernameAndProfilepic();



    }
    @FXML public void changeProfilePic(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file.");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG image","*.png"),
                new FileChooser.ExtensionFilter("All image","*.jpg","*.png","*.pdf"));
        selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null ){
            Image image = new Image(selectedFile.getPath());
            imageView.setImage(image);
            //imagePath = selectedFile.getPath();
            updateProfilePic();

        }
        else {
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
//            imageView.setImage(image);
            System.out.println("File not selected");
        }
    }
    private void updateProfilePic(){
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement store = connectDB.prepareStatement("UPDATE logindata SET profilePic = ? WHERE username = '"+CurrentUserName+"'");
            if(selectedFile != null){
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                store.setBinaryStream(1,fileInputStream,fileInputStream.available());
                store.execute();
                UpdateProfilePicIntoPost();
            }
            else {
                System.out.println("failed to update profile pci");
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setContentText("Nothing have posted");
//                alert.show();
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }
    public void SetUsernameAndProfilepic(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM logindata WHERE username ='"+CurrentUserName+"' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){
                Blob blob = queryResult.getBlob("profilePic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                }else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
                    imageView.setImage(image);
                }
                if(queryResult.getString("username") != null ) {
                    username.setText(queryResult.getString("username"));
                }else {
                    System.out.println("error loading username");
                }
                if(queryResult.getString("firstname") != null ) {
                    firstName.setPromptText(queryResult.getString("firstname"));
                }else {
                    System.out.println("error loading firstname");
                }
                if(queryResult.getString("lastname") != null ) {
                    lastName.setPromptText(queryResult.getString("lastname"));
                }else {
                    System.out.println("error loading lastname");
                }
                if(queryResult.getString("username") != null ) {
                    user.setPromptText(queryResult.getString("username"));
                }else {
                    System.out.println("error loading email");
                }



            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }
    private void UpdateProfilePicIntoPost() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String data = "UPDATE home_page_post SET profilePic = ( SELECT profilePic FROM logindata WHERE home_page_post.username = logindata.username);";
        statement.executeUpdate(data);
    }


    @FXML private void onUpdateButtonClick() throws SQLException {
        Boolean b= false;

        if (!user.getText().isEmpty()||!user.getText().isBlank()) {
            updateUsername(user.getText());
            b= true;
        }
        if (!firstName.getText().isEmpty()||!firstName.getText().isBlank()) {
            updateFirstname(firstName.getText());
            b=true;
        }
        if (!lastName.getText().isEmpty()||!lastName.getText().isBlank()) {
            updateLastname(lastName.getText());
            b= true;
        }
        if (!password.getText().isEmpty()||!password.getText().isBlank()) {
            updatePassword(password.getText());
            b= true;
        }
        if(b){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Updated successfully");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is something went wrong");
            alert.show();
        }

    }

    private void updateFirstname(String text) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement store = connectDB.prepareStatement("UPDATE logindata SET firstname = ? WHERE username = '" + CurrentUserName + "'");

            store.setString(1, text);
            store.execute();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void updateLastname(String text) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement store = connectDB.prepareStatement("UPDATE logindata SET lastname = ? WHERE username = '" + CurrentUserName + "'");

            store.setString(1, text);
            store.execute();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void updateUsername(String text) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String query = "SELECT * FROM logindata WHERE username = '" + text + "'";
        ResultSet resultSet = statement.executeQuery(query);
        boolean b= resultSet.next();
        //System.out.println(resultSet.next());
        if (b) {
            comment.setText("* Username is already taken.");
        }
        else {

            PreparedStatement store = connectDB.prepareStatement("UPDATE logindata SET username = ? WHERE username = '" + CurrentUserName + "'");
            store.setString(1, text);
            store.execute();
            String query1 = "UPDATE home_page_post SET username = '"+text+"' WHERE username = '"+CurrentUserName+"'";
            statement.executeUpdate(query1);
            CurrentUserName = text;


        }

    }
    private void updatePassword(String text) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement store = connectDB.prepareStatement("UPDATE logindata SET password = ? WHERE username = '" + CurrentUserName + "'");
            store.setString(1, text);
            store.execute();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
