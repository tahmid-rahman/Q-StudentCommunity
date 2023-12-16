package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.example.q_studentcommunity.HelpPostTemplate.helpPostId;

public class helpPopUp {

    @FXML
    private Text buttonID;

    @FXML
    private Button topicButton;

    @FXML
    private Text topicText;


    public void setHelpPopData(HelpPopupClass popdata){
        buttonID.setText(popdata.getTopIp());
        topicText.setText(popdata.getTopText());
    }

    @FXML
    void onTopicButtonClick(ActionEvent event) throws IOException {
        helpPostId = buttonID.getText();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SeeSolution.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Solution");
        stage.show();
        System.out.println(helpPostId);
    }

}
