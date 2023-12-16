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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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

import static com.example.q_studentcommunity.PostTemplate.selectedId;
import static com.example.q_studentcommunity.loginPage.CurrentUserName;


public class UpdatePostTemplate implements Initializable {

    @FXML
    private Circle dp;

    @FXML
    private ImageView picture;

    @FXML
    private TextArea text;

    @FXML
    private Label type;

    @FXML
    private Label username;
    private File selectedFile = null;
    Stage stage;
    @FXML
    void onCrossButtonClick(MouseEvent event) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/Screenshot 2023-12-16 023954.png")));
        picture.setImage(image);
        selectedFile = null;
    }

    @FXML
    void onUpdateButtonClick(ActionEvent event) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            PreparedStatement store;
            String storeStatement = "UPDATE home_page_post SET caption = ? ,postPic = ? WHERE id='"+selectedId+"'";
            store = connectDB.prepareStatement(storeStatement);
            if(selectedFile != null){
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                store.setBinaryStream(2,fileInputStream,fileInputStream.available());
            }else {store.setBinaryStream(2,null);}
            if(!text.getText().isEmpty()){
                store.setString(1,text.getText());
            }else {store.setString(1,null);}
            if((selectedFile != null) || !text.getText().isEmpty()){

                store.execute();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("StudentCommunity");
                stage.show();
                System.out.println("Upload successful");
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Nothing has posted");
                alert.show();
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onCancelButtonClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }
    @FXML
    void onBrouseButtonClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file.");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG image","*.png"),
                new FileChooser.ExtensionFilter("All image","*.jpg","*.png","*.pdf"));
        selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null ){
            Image image = new Image(selectedFile.getPath());

            picture.setImage(image);
           // picture.setFitWidth(280);

        }
        else {System.out.println("File not selected");}
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPreviousData();
    }

    void setPreviousData(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM home_page_post WHERE id ='"+selectedId+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){
                Blob blob = queryResult.getBlob("profilePic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    dp.setFill(new ImagePattern(image));

                }
                Blob blob1 = queryResult.getBlob("postPic");
                if(blob1 != null) {
                    InputStream inputStream = blob1.getBinaryStream();
                    Image image1 = new Image(inputStream);
                    picture.setImage(image1);
                    //picture.setFitWidth(280);

                }
                text.setText(queryResult.getString("caption"));
                username.setText(queryResult.getString("username"));


            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }
}