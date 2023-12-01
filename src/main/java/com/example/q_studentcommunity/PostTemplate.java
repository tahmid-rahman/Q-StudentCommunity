package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;

public class PostTemplate {
    @FXML ImageView profilePic;
    @FXML ImageView postPic;
    @FXML Label username;
    @FXML Label userType;
    @FXML Text caption;
    @FXML Label qPoint;
    public void setData(Post value){
        try{
            Image dp = new Image(Objects.requireNonNull(getClass().getResourceAsStream(value.getDP())));
            Image pp = new Image(Objects.requireNonNull(getClass().getResourceAsStream(value.getPP())));
            profilePic.setImage(dp);
            postPic.setImage(pp);
            username.setText(value.getUsername());
            qPoint.setText(value.getqPoint());
            caption.setText(value.getCaption());
            userType.setText(value.getUserType());
        }catch (Exception e){
            e.getCause();
        }


    }

}
