package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FileName {

    @FXML
    private Button FileName;
    public static String SelectedFileToShow;
    public void SetFileNameText(String text){
        FileName.setText(text);
    }

    @FXML
    void FileButtonClick(ActionEvent event) throws IOException {
        SelectedFileToShow = FileName.getText();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FileViewTemplate.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();


    }

}
