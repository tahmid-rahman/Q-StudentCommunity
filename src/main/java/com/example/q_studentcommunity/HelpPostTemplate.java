
package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.util.Objects;


public class HelpPostTemplate {
    @FXML private Text cap;
    @FXML private ImageView helpPic;
    @FXML private Label time;
    @FXML private Label topicName;

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

    }
    @FXML
    void onSeeAnswerButtonClick(ActionEvent event) {

    }
    @FXML void onSolveProblemButtonClick(ActionEvent event) {}
}


