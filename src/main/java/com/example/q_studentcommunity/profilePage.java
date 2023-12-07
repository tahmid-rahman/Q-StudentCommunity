package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.q_studentcommunity.loginPage.CurrentUserName;

public class profilePage implements Initializable {

    @FXML
    private Label Email;

    @FXML
    private Circle circle;

    @FXML
    private VBox postContainer;

    @FXML
    private Label username;
    public ArrayList<Post> post;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetEmailAndProfilePic();
        username.setText(CurrentUserName);

        post = new ArrayList<>(getList());
//
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Post value : post) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("PostTemplate.fxml"));

                    try {
                        postContainer.getChildren().add(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    PostTemplate postControl = loader.getController();
                    postControl.setData(value);
                }
            }
        });

    }

    private void SetEmailAndProfilePic() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM logindata WHERE username ='"+CurrentUserName+"' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next())
             {
                Blob blob = queryResult.getBlob("profilePic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    circle.setFill(new ImagePattern(image));
                }else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
                    circle.setFill(new ImagePattern(image));
                }
                Email.setText(queryResult.getString("email"));

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }

    private List<Post> getList() {
        List<Post> list = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM home_page_post WHERE username = '"+CurrentUserName+"' ORDER BY id DESC";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){

                Post p = new Post();
                Blob blob = queryResult.getBlob("postPic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    p.setPostPic(image);
                }else {p.setPostPic(null);}

                Blob blob1 = queryResult.getBlob("profilePic");
                if(blob1 != null) {
                    InputStream inputStream = blob1.getBinaryStream();
                    Image image1 = new Image(inputStream);
                    p.setProfilePic(image1);
                }else {p.setProfilePic(null);}

                if(queryResult.getString("caption") != null ) {
                    p.setCaption(queryResult.getString("caption"));
                }else {p.setCaption("");}
                p.setUsername(queryResult.getString("username"));
                p.setUserType(queryResult.getString("usertype"));
                list.add(p);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }


        return list;
    }
    @FXML private void onCreateNewPostButtonClick(ActionEvent event){
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewPost.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            // stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Create new post");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }


}
