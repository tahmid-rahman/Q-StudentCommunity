
package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class HelpPostTemplate {
    @FXML private Text cap;
    @FXML private ImageView helpPic;
    @FXML private Label time;
    @FXML private Label topicName;
    @FXML private Label postId;
    public static String helpPostId;
    public void setHelpData(HelpPost helpPost){


        if(helpPost.getHelpPic() != null)
        {
            helpPic.setFitHeight(300);
            helpPic.setFitWidth(300);
            helpPic.setImage(helpPost.getHelpPic());
        }else {
            helpPic.setVisible(false);
        }

        if(helpPost.getHelpCap() != null || !helpPost.getHelpCap().isEmpty())
        {
            cap.setText(helpPost.getHelpCap());
        }else {
            cap.setVisible(false);
        }
        topicName.setText(helpPost.getTopicName());
        time.setText(helpPost.getDateTime());
        postId.setText(helpPost.getPostId());


    }
    @FXML
    void onSeeAnswerButtonClick(ActionEvent event) throws IOException {
        helpPostId = postId.getText();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SeeSolution.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Submit answer Page");
        stage.show();
    }
    @FXML void onSolveProblemButtonClick(ActionEvent event) throws IOException {
        helpPostId = postId.getText();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelpAnswer.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Submit answer Page");
        stage.show();
    }
}


