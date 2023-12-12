package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Objects;

public class PostTemplate {
    @FXML Circle dp;
    @FXML Label username;
    @FXML Label userType;
    @FXML ImageView pp;
    @FXML Text caption;
    public void setData(Post post){

      //
      if(post.getProfilePic() != null)
      {
          dp.setFill(new ImagePattern(post.getProfilePic()));
      }else {
          Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
          dp.setFill(new ImagePattern(image));
      }

      if(post.getPostPic() != null)
      {
          pp.setFitHeight(300);
          pp.setFitWidth(300);
          pp.setImage(post.getPostPic());
      }else {
          pp.setVisible(false);
      }
      if(post.getCaption() != null || !post.getCaption().isEmpty())
      {
          caption.setText(post.getCaption());
      }else {
          caption.setVisible(false);
      }
      username.setText(post.getUsername());
      userType.setText(post.getUserType());

    }


}