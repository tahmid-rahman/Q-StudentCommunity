package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;

import java.util.Objects;

public class PostTemplate {
    @FXML ImageView profilePic;
    @FXML ImageView postPic;
    @FXML Label username;
    @FXML Label userType;
    @FXML Text caption;
    @FXML Label qPoint;
    public void setData(Post post){
        Image dp = new Image(Objects.requireNonNull(getClass().getResourceAsStream(post.getDP())));
        Image pp = new Image(Objects.requireNonNull(getClass().getResourceAsStream(post.getPP())));
        profilePic.setImage(dp);
        postPic.setImage(pp);
        username.setText(post.getUsername());
        qPoint.setText(post.getqPoint());
        caption.setTextContent(post.getCaption());
        userType.setText(post.getUserType());

    }

}
