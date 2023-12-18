package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ResourceControl implements Initializable {

    @FXML
    private Pane addfilePane;

    @FXML
    private Pane addfolderPane;

    @FXML
    private Pane fileViewPane;

    @FXML
    private TextField folName;

    @FXML
    private VBox folderHolder;

    @FXML
    private TextField foldername;

    @FXML
    private ImageView picture;

    @FXML
    private Pane resourcePane;

    @FXML
    void addFileCilck(ActionEvent event) {
        if (!foldername.getText().isEmpty()){

        }
    }

    @FXML
    void addFolderClick(MouseEvent event) {
        String directoryName = foldername.getText();

        String directoryPath = "D:\\Q_StudentCommunity\\src\\main\\resources\\Resources" + directoryName;

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            boolean success = directory.mkdir();

            if (success) {
                System.out.println("Directory created successfully: " + directoryPath);
            } else {
                System.err.println("Failed to create the directory.");
            }
        } else {
            System.out.println("Directory already exists: " + directoryPath);
        }
        foldername.setPromptText("Enter folder name.");
    }

    @FXML
    void newFileClick(ActionEvent event) {
        addfilePane.setVisible(true);
        addfolderPane.setVisible(false);
        fileViewPane.setVisible(false);
    }

    @FXML
    void newFolderClcik(ActionEvent event) {
        addfilePane.setVisible(false);
        addfolderPane.setVisible(true);
        fileViewPane.setVisible(false);
    }

    @FXML
    void onBrouseButtonClicked(MouseEvent event) {

    }

    @FXML
    void onCrossButtonClick(MouseEvent event) {
        addfilePane.setVisible(false);
        addfolderPane.setVisible(false);
        fileViewPane.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addfilePane.setVisible(false);
        addfolderPane.setVisible(false);
        fileViewPane.setVisible(true);

    }
}

