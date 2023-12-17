package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminPanel implements Initializable {

    @FXML
    private Pane AdminPane;

    @FXML
    private Label activeUser;

    @FXML
    private Pane dashPane;

    @FXML
    private Button dashboard;

    @FXML
    private Button dropbox;

    @FXML
    private Button feed;

    @FXML
    private Button help;

    @FXML
    private Circle profileCircle;

    @FXML
    private Button resource;

    @FXML
    private Button server;

    @FXML
    private Label totalHelpPost;

    @FXML
    private Label totolHomePost;

    @FXML
    private Label totolUser;

    @FXML
    void dashboard(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#f87c29");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
        */
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPanel.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("AdminPane");
        stage.show();
    }

    @FXML
    void feedClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#f87c29");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FeedPostControl.fxml"));
        AdminPane.getChildren().add(loader.load());
    }

    @FXML
    void helpClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#f87c29");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25")
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HelpPostControl.fxml"));
        AdminPane.getChildren().add(loader.load());
    }

    @FXML
    void dropboxClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#f87c29");
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Dropbox.fxml"));
        AdminPane.getChildren().add(loader.load());
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("authorizerLogin.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void resourceClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#f87c29");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ResourceControl.fxml"));
        AdminPane.getChildren().add(loader.load());

    }

    @FXML
    void serverClick(ActionEvent event) throws IOException {
        dashPane.setVisible(false);
/*
        server.setStyle(" -fx-background-color :#f87c29");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
*/
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ServerControl.fxml"));
        AdminPane.getChildren().add(loader.load());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashPane.setVisible(true);
//        dashboard.setStyle(" -fx-background-color :#f87c29");


    }
}
