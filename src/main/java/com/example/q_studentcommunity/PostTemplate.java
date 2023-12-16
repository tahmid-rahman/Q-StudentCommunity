package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.q_studentcommunity.homePage.usernameForProfileView;
import static com.example.q_studentcommunity.loginPage.CurrentUserName;
import static com.example.q_studentcommunity.loginPage.PageName;
import static com.example.q_studentcommunity.profilePage.flag;

public class PostTemplate implements Initializable {
    @FXML Circle dp;
    @FXML Hyperlink username;
    @FXML Label userType;
    @FXML ImageView pp;
    @FXML ImageView option;
    @FXML Text caption;
    @FXML private Pane popPane;
    @FXML private Label hPostId;
    public static String selectedId;

    @FXML
    void optionButtonClicked(MouseEvent event) {
        if(flag){
            popPane.setVisible(true);
            flag= false;
        }else {
            flag= true;
            popPane.setVisible(false);
        }



    }
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws SQLException, IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "DELETE FROM home_page_post WHERE id ='"+hPostId.getText()+"'";
        Statement statement = connectDB.createStatement();
        statement.execute(getData);
        System.out.println("Delete successful");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }

    @FXML
    void onEditButtonClick(ActionEvent event) throws IOException {
        selectedId = hPostId.getText();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UpdatePostTemplate.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }
    @FXML
    void onUsernameClick(ActionEvent event) throws IOException {
        usernameForProfileView = username.getText();
        if(usernameForProfileView.equals(CurrentUserName)){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("StudentCommunity");
            stage.show();
        }else {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ProfileView.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("StudentCommunity");
            stage.show();

        }

       // System.out.println(username.getText()+" clicked");
    }
    public void setData(Post post){

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
      hPostId.setText(post.getHomePostId());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popPane.setVisible(false);
        if(PageName.equals("profilePage") && !PageName.equals("ProfileView")){
            option.setVisible(true);
        }else {
            option.setVisible(false);
        }
    }
}