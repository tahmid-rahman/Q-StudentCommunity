package com.example.q_studentcommunity;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WebViews implements Initializable {

    @FXML
    private Button aCalender;

    @FXML
    private Button backToResource;

    @FXML
    private Button library;

    @FXML
    private Line line;

    @FXML
    private Button lms;

    @FXML
    private Button noticeBoard;

    @FXML
    private ProgressBar progress;

    @FXML
    private Button sites;

    @FXML
    private Button ucam;

    @FXML
    private Button uiu;

    @FXML
    private WebView webView;

    @FXML
    private Label welcomeLebel;
    WebEngine webEngine;

    @FXML
    void backToResourceClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resourcePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }

    @FXML
    void calenderButtonClick(ActionEvent event) {
        try {
            webEngine = webView.getEngine();
            progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    progress.setVisible(false);
                } else {
                    progress.setVisible(true);
                }
            });
            webEngine.load("https://www.uiu.ac.bd/academics/calendar/");
        }catch (Exception e){}

    }

    @FXML
    void libraryButton(ActionEvent event) {
        try {
            webEngine = webView.getEngine();
            progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    progress.setVisible(false);
                } else {
                    progress.setVisible(true);
                }
            });
            webEngine.load("https://library.uiu.ac.bd/");
        }catch (Exception e){System.out.println(e);}

    }

    @FXML
    void lmsButtonClick(ActionEvent event) {
        try {
            webEngine = webView.getEngine();
            progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    progress.setVisible(false);
                } else {
                    progress.setVisible(true);
                }
            });

            webEngine.load("http://lms.uiu.ac.bd/login/index.php");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void noticeButtonClick(ActionEvent event) {
        try {
            webEngine = webView.getEngine();
            progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    progress.setVisible(false);
                } else {
                    progress.setVisible(true);
                }
            });

            webEngine.load("https://www.uiu.ac.bd/notices/");
        }catch (Exception e){System.out.println(e);}

    }

    @FXML
    void sitePage(ActionEvent event) {

    }

    @FXML
    void ucamButton(ActionEvent event) {
        try {
            webEngine = webView.getEngine();
            //progress.setProgress(0.0);
            progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    //   progress.disableProperty();
                    progress.setVisible(false);
                } else {
                    progress.setVisible(true);
                }
            });

            webEngine.load("https://ucam.uiu.ac.bd/Security/LogIn.aspx");
        }catch (Exception e){
            System.out.println(e);
            throw new IncompatibleClassChangeError("Size error");
        }

    }

    @FXML
    void uiuButton(ActionEvent event) {
        try {
            webEngine = webView.getEngine();
//            progress.setProgress(0);
            progress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    progress.setVisible(false);
                } else {
                    progress.setVisible(true);
                }
            });

            //webEngine.load("https://www.uiu.ac.bd/");
        }catch (Exception e){System.out.println(e);}


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progress.setVisible(false);
        progress.setProgress(0.0);
    }
}
