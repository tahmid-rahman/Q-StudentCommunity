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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class resourcePage implements Initializable {
    @FXML
    Line line;
    @FXML
    private Pane pane;
    @FXML
    private VBox folderHolder;
    @FXML
    private Button ShowFolder;
    @FXML
    private ScrollPane scrollbox;

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
        ShowFolder.setVisible(false);


        String fileLocation = "D:\\Q_StudentCommunity\\src\\main\\resources\\com\\example\\q_studentcommunity\\Resources";
        File file = new File(fileLocation);
        String[] arr = file.list();
        System.out.println(arr.length);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(String i: arr) {
                    File file1 = new File(fileLocation+"\\"+i);
                    if(file1.isDirectory()){
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("FolderName.fxml"));
                        try {
                            folderHolder.getChildren().add(loader.load());
                            FolderName folderName = loader.getController();
                            folderName.SetButtonText(i);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            }
        });
    }

    @FXML
    void onImportantLinkButtonClick(ActionEvent event) {
        scrollbox.setVisible(false);
        line.setVisible(false);
        ShowFolder.setVisible(true);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WebViews.fxml"));
        try {
            pane.getChildren().add(loader.load());
            //FolderName folderName = loader.getController();
            //folderName.SetButtonText(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void onShowFolderButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resourcePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }
}
