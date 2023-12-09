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

import static com.example.q_studentcommunity.loginPage.CurrentUserName;

public class CreateHelpPost {
    @FXML
    ImageView imageView;
    @FXML
    TextField helpCap;
    @FXML TextField topicField;
    @FXML Stage stage;
    private String imagePath;
    File selectedHelpFile = null;
    private PreparedStatement store;
    private String storeStatement = "INSERT INTO help_post (topic,helpText,helpPic) VALUE (?,?,?)";

    @FXML
    void onBrowseButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file.");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG image","*.png"),
                new FileChooser.ExtensionFilter("All image","*.jpg","*.png","*.pdf"));
        selectedHelpFile = fileChooser.showOpenDialog(stage);
        if(selectedHelpFile != null ){
            Image image = new Image(selectedHelpFile.getPath());

            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imagePath = selectedHelpFile.getPath();
            System.out.println(selectedHelpFile.getName());
        }
        else {System.out.println("File not selected");}

        System.out.println(imagePath);
    }

    @FXML
    void onCrossbuttonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/Screenshot 2023-12-06 082057.png")));
        imageView.setImage(image);
//        imageView.setFitWidth(0);
//        imageView.setFitHeight(0);
        selectedHelpFile = null;
    }

    @FXML
    void onUploadPostButtonClick(ActionEvent event) {
        try {
            boolean b= false;
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            store = connectDB.prepareStatement(storeStatement);
            if(selectedHelpFile != null){
                FileInputStream fileInputStream = new FileInputStream(selectedHelpFile);
                store.setBinaryStream(3,fileInputStream,fileInputStream.available());
            }else {store.setBinaryStream(3,null);}
            if(!helpCap.getText().isEmpty()){
                store.setString(2,helpCap.getText());
            }else {store.setString(2,null);}
            //   store.setString(1,loginPage.CurrentUserName);
            if(!topicField.getText().isEmpty()){
                store.setString(1,topicField.getText());
                b=true;
            }else {
                b= false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You have to insert a topic name");
                alert.show();
            }

            if(((selectedHelpFile != null) || !helpCap.getText().isEmpty()) && b){

                store.execute();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("StudentCommunity");
                stage.show();
                System.out.println("Upload successful");
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Nothing have posted.\nYou help post must have text or image file.");
                alert.show();
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
