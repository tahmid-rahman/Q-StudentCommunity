package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.q_studentcommunity.HelpPostTemplate.helpPostId;
import static com.example.q_studentcommunity.loginPage.CurrentUserName;


public class HelpAnswer implements Initializable {

    @FXML
    private TextField AnswerTF;

    @FXML
    private ImageView Crossbutton;

    @FXML
    private ImageView ansImage;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView questionImage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text topicName;
    @FXML
    private Text question;

    Stage stage;
    File selectedHelpFile = null;
    private String imagePath;
    private String storeStatement = "INSERT INTO help_post_answer (postId,answerPic,answerCap) VALUE (?,?,?)";
    private PreparedStatement store;

    @FXML
    void onAttachImageButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file.");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG image","*.png"),
                new FileChooser.ExtensionFilter("All image","*.jpg","*.png","*.pdf"));
        selectedHelpFile = fileChooser.showOpenDialog(stage);
        if(selectedHelpFile != null ){
            Image image = new Image(selectedHelpFile.getPath());
            ansImage.setVisible(true);
            ansImage.setImage(image);
            ansImage.setFitWidth(200);
            ansImage.setFitHeight(200);
            imagePath = selectedHelpFile.getPath();
            System.out.println(selectedHelpFile.getName());
        }
        else {System.out.println("File not selected");}

        System.out.println(imagePath);

    }

    @FXML
    void onCrossbuttonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Help Page");
        stage.show();
    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) {
        selectedHelpFile = null;
        ansImage.setVisible(false);
    }

    @FXML
    void onSubmitAnswerButtonClick(ActionEvent event) {
        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            store = connectDB.prepareStatement(storeStatement);
            if(selectedHelpFile != null){
                FileInputStream fileInputStream = new FileInputStream(selectedHelpFile);
                store.setBinaryStream(2,fileInputStream,fileInputStream.available());
            }else {store.setBinaryStream(2,null);}
            if(!AnswerTF.getText().isEmpty()){
                store.setString(3,AnswerTF.getText());
            }else {store.setString(3,null);}
            store.setString(1,helpPostId);

            if((selectedHelpFile != null) || !AnswerTF.getText().isEmpty()){

                store.execute();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("StudentCommunity");
                stage.show();
                System.out.println("Upload successful");
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Nothing have posted.\nYou help post must have text or image file.");
                alert.show();
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(helpPostId);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM help_post WHERE postId ='"+helpPostId+"' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){
                Blob blob = queryResult.getBlob("helpPic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    questionImage.setImage(image);
                }
                topicName.setText(queryResult.getString("topic"));
                question.setText(queryResult.getString("helpText"));


            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }


    }
}
