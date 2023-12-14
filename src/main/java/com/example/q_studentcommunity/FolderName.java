package com.example.q_studentcommunity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Objects;
import java.util.ResourceBundle;


public class FolderName implements Initializable {

    @FXML
    private Button button;
    public static String SourceName;
    public void SetButtonText(String text){
        button.setText(text);
    }
    @FXML private void buttonClick(ActionEvent event) throws IOException {
        SourceName = button.getText();
        System.out.println(SourceName);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fileBox.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
