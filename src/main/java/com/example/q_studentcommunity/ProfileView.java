package com.example.q_studentcommunity;



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.q_studentcommunity.homePage.usernameForProfileView;
import static com.example.q_studentcommunity.loginPage.CurrentUserName;
import static com.example.q_studentcommunity.loginPage.PageName;

public class ProfileView implements Initializable {
    @FXML
    private Label Email;

    @FXML
    private Circle circle;

    @FXML
    private VBox postContainer;

    @FXML
    private Pane profilePane;

    @FXML
    private Label username;
    public ArrayList<Post> post;

    @FXML
    void onCrossButtonClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PageName = "ProfileView";
        post = new ArrayList<>(getList());
        username.setText(usernameForProfileView);
        SetEmailAndProfilePic();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Post value : post) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("PostTemplate.fxml"));

                    try {
                        postContainer.getChildren().add(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    PostTemplate postControl = loader.getController();
                    postControl.setData(value);
                }
            }
        });

    }
    private List<Post> getList() {
        List<Post> list = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM home_page_post WHERE username = '"+usernameForProfileView+"' ORDER BY id DESC";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){

                Post p = new Post();
                Blob blob = queryResult.getBlob("postPic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    p.setPostPic(image);
                }else {p.setPostPic(null);}

                Blob blob1 = queryResult.getBlob("profilePic");
                if(blob1 != null) {
                    InputStream inputStream = blob1.getBinaryStream();
                    Image image1 = new Image(inputStream);
                    p.setProfilePic(image1);
                }else {p.setProfilePic(null);}

                if(queryResult.getString("caption") != null ) {
                    p.setCaption(queryResult.getString("caption"));
                }else {p.setCaption("");}
                p.setUsername(queryResult.getString("username"));
                p.setUserType(queryResult.getString("usertype"));
                p.setHomePostId(queryResult.getString("id"));

                list.add(p);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }


        return list;
    }
    private void SetEmailAndProfilePic() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM logindata WHERE username ='"+usernameForProfileView+"' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next())
            {
                Blob blob = queryResult.getBlob("profilePic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    circle.setFill(new ImagePattern(image));
                }else {
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/profile.png")));
                    circle.setFill(new ImagePattern(image));
                }
                Email.setText(queryResult.getString("email"));

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }
}
