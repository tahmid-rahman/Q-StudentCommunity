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
import javafx.scene.control.TextArea;
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

public class NewPost implements Initializable {
    @FXML ImageView imageView;
    @FXML ImageView profilePic;
    @FXML TextField caption;
    @FXML Label username;
    @FXML Label type;
    Stage stage;
    private String imagePath;
    File selectedFile = null;
    @FXML public void onBrowseButtonClick(){

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
            imagePath = selectedFile.getPath();
            System.out.println(selectedFile.getName());
        }
        else {System.out.println("File not selected");}

        System.out.println(imagePath);



    }
    private PreparedStatement store, retrieve;
    private String storeStatement = "INSERT INTO home_page_post (usertype,caption,qpoint,postPic,username) VALUE (?,?,?,?,?)";
    //private String retrieveStatement = "SELECT image FROM home_page_post WHERE username = ?";
    @FXML private void onUploadPostButtonClick(ActionEvent event){


        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            store = connectDB.prepareStatement(storeStatement);
            if(selectedFile != null){
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                store.setBinaryStream(4,fileInputStream,fileInputStream.available());
            }else {store.setBinaryStream(4,null);}
            if(!caption.getText().isEmpty()){
                store.setString(2,caption.getText());
            }else {store.setString(2,null);}
         //   store.setString(1,loginPage.CurrentUserName);
            store.setString(1,"admin");
            store.setString(3,"3 Point");
            store.setString(5,CurrentUserName);
            if(selectedFile != null || !caption.getText().isEmpty()){

                store.execute();
                UpdateProfilePicIntoPost();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("StudentCommunity");
                stage.show();
                System.out.println("Upload successful");
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Nothing have posted");
                alert.show();
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }

    private void UpdateProfilePicIntoPost() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        String data = "UPDATE home_page_post SET profilePic = ( SELECT profilePic FROM logindata WHERE home_page_post.username = logindata.username);";
        statement.executeUpdate(data);
    }

    @FXML
    private void onCrossbuttonClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }

    @FXML private void onCancelButtonClick()  {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/Screenshot 2023-12-06 082057.png")));
        imageView.setImage(image);
        selectedFile = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(CurrentUserName);
        SetProfilePicture();
    }

    private void SetProfilePicture() {
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
                    profilePic.setImage(image);
                }else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
                    profilePic.setImage(image);
                }

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }
}
