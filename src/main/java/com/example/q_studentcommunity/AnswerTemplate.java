package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class AnswerTemplate {

    @FXML
    private ImageView answerPic;

    @FXML
    private Text answerText;

    @FXML
    private Label postId;
    public void setAnsData(Answer answer){


        if(answer.getAnsPic() != null)
        {
            answerPic.setFitHeight(300);
            answerPic.setFitWidth(300);
            answerPic.setImage(answer.getAnsPic());
        }else {
            answerPic.setVisible(false);
        }

        if(answer.getAnsText() != null || !answer.getAnsText().isEmpty())
        {
            answerText.setText(answer.getAnsText());
        }else {
            answerPic.setVisible(false);
        }
        postId.setText(answer.getpId());


    }

}
