package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.q_studentcommunity.loginPage.PageName;

import static com.example.q_studentcommunity.loginPage.CurrentUserName;

public class homePage implements Initializable {


    @FXML private Label user;
    @FXML private VBox PostHolder;
    @FXML private Circle circle;
    @FXML private Button feed;
    @FXML private Pane popUpPane;
    @FXML private Circle popUpCircle;
    @FXML private Button popUpUsername;
    @FXML private Pane FeedPane;
    @FXML public  VBox feedVox;
    @FXML private HBox feedhbox;
    public static String usernameForProfileView;



    public ArrayList<Post> post;

    @FXML
    void onPopUpCrossButton(MouseEvent event) {
        popUpPane.setVisible(false);
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException, SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String data = "DELETE FROM chat_user_list WHERE username = '"+CurrentUserName+"'";
        Statement statement = connectDB.createStatement();
        statement.execute(data);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML protected void onOpinionPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML protected void onHelpPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML protected void onChatsPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chatPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML protected void onProfilePageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML protected void onSettingPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settingPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML protected void onResourcePageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resourcePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //feedVox.setVisible(false);
        PageName = "homePage";

        SetUsernameAndProfilepic();
        popUpPane.setVisible(false);
        user.setText("Hello "+ CurrentUserName +", Welcome !");
        popUpUsername.setText(CurrentUserName);

        post = new ArrayList<>(getList());
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                for (Post value : post) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("PostTemplate.fxml"));

                    try {
                        PostHolder.getChildren().add(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    PostTemplate postControl = loader.getController();
                    postControl.setData(value);
                }
            }
        });

       // System.out.println(UserName);


    }

    private List<Post> getList() {
        List<Post> list = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM home_page_post WHERE 1 ORDER BY id DESC";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){
/*                System.out.println(queryResult.getString("username"));
                System.out.println(queryResult.getString("usertype"));
                System.out.println(queryResult.getString("caption"));
                System.out.println(queryResult.getString("qpoint"));
*/
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
              //  p.setProfilePic("file/feed.png");
                p.setUsername(queryResult.getString("username"));
                p.setUserType(queryResult.getString("usertype"));
                p.setHomePostId(queryResult.getString("id"));
                //System.out.println(queryResult.getString("id"));
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
                    circle.setFill(new ImagePattern(image));
                    popUpCircle.setFill(new ImagePattern(image));
                }else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
                    //ProfilePic.setImage(image);
                    circle.setFill(new ImagePattern(image));
                    popUpCircle.setFill(new ImagePattern(image));
                }

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }
    @FXML
    void onProfileCircleButtonClick(MouseEvent event) {
        popUpPane.setVisible(true);
        System.out.println("clicked");
    }

    private void filterData(String filter) {

    }


}
