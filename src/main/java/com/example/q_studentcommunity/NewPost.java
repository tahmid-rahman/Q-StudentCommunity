package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class NewPost {
    @FXML
    ImageView imageView;
    @FXML
    ImageView profilePic;
    @FXML
    TextArea caption;
    @FXML
    Label username;
    @FXML
    Label type;
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
    private String storeStatement = "INSERT INTO home_page_post (username,usertype,caption,qpoint,postPic) VALUE (?,?,?,?,?)";
    //private String retrieveStatement = "SELECT image FROM home_page_post WHERE username = ?";
    @FXML private void onUploadPostButtonClick(ActionEvent event){


        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            store = connectDB.prepareStatement(storeStatement);
            if(selectedFile != null){
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                store.setBinaryStream(5,fileInputStream,fileInputStream.available());
            }else {store.setBinaryStream(5,null);}
            if(!caption.getText().isEmpty()){
                store.setString(3,caption.getText());
            }else {store.setString(3,null);}
            store.setString(1,loginPage.UserName);
            store.setString(2,"admin");
            store.setString(4,"3 Point");

            store.execute();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("StudentCommunity");
            stage.show();
            System.out.println("Upload successful");
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


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

}
