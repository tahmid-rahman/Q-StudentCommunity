package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

public class Help implements Initializable {

    @FXML
    ImageView ProfilePic;
    @FXML
    Label user;
    @FXML private VBox PostHolder;

    @FXML
    Button feed;
    public ArrayList<HelpPost> helpost;
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
       // post = new ArrayList<>(getList());
//        for (Post value : post) {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("PostTemplate.fxml"));
//
//            try {
//                PostHolder.getChildren().add(loader.load());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            PostTemplate postControl = loader.getController();
//            postControl.setData(value);
//
//        }\

        helpost = new ArrayList<>(getHelpList());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (HelpPost value : helpost) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("HelpPostTemplate.fxml"));

                    try {
                        PostHolder.getChildren().add(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    HelpPostTemplate postCollector = loader.getController();
                    postCollector.setHelpData(value);
                }
            }
        });

        // System.out.println(UserName);

    }
    private List<HelpPost> getHelpList() {
        List<HelpPost> list = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM help_post WHERE 1 ORDER BY postId DESC";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){
                System.out.println(queryResult.getString("topic"));
//                System.out.println(queryResult.getString("usertype"));
//                System.out.println(queryResult.getString("caption"));
//                System.out.println(queryResult.getString("qpoint"));

                HelpPost hp = new HelpPost();
                Blob blob = queryResult.getBlob("helpPic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    hp.setHelpPic(image);
                }else {hp.setHelpPic(null);}


                if(queryResult.getString("helpText") != null ) {
                    hp.setHelpCap(queryResult.getString("helpText"));
                }else {hp.setHelpCap("");}
                //  p.setProfilePic("file/feed.png");
                hp.setTopicName(queryResult.getString("topic"));
                hp.setDateTime(queryResult.getString("date"));
                list.add(hp);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }


        return list;
    }

    @FXML
    void onAddHelpPostButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateHelpPost.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create help Post");
        stage.show();
    }



}
