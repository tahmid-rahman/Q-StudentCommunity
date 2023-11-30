package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class homePage implements Initializable {
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

    @FXML private VBox vBox;
    public ArrayList<Post> post;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        post = new ArrayList<>(getList());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PostTemplate.fxml"));
        for (Post value : post) {

            try {
                vBox.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PostTemplate postControl = fxmlLoader.getController();
            if(postControl==null)
            {
                System.out.println("fuck you");
            }
            postControl.setData(value);

        }

    }

    private List<Post> getList() {
        List<Post> list = new ArrayList<>();
        Post post1 = new Post();
        post1.setDP("file/dp.jpg");
        post1.setUsername("tahmid_rahman");
        post1.setUserType("admin");
        post1.setCaption("hello every one this is the first post of my javafx project. hope you all like it.");
        post1.setPP("file/1684763460041.jpg");
        post1.setqPoint("3 Q_point");
        list.add(post1);

        return list;
    }

}
