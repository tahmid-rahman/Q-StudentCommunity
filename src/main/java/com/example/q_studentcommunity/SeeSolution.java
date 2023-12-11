package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

import static com.example.q_studentcommunity.HelpPostTemplate.helpPostId;

public class SeeSolution implements Initializable {

    @FXML
    private ImageView Crossbutton;

    @FXML
    private VBox ansholder;

    @FXML
    private ImageView imageView1;

    @FXML
    private Text quesText;

    @FXML
    private ImageView questionImage;

    @FXML
    private Text topicName;
    public ArrayList<Answer> answers;
    @FXML
    void onCrossbuttonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(helpPostId);
        SetQuestionAndImage();
        answers = new ArrayList<>(getAnswerList());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Answer value : answers) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("AnswerTemplate.fxml"));

                    try {
                        ansholder.getChildren().add(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    AnswerTemplate answerTemplate = loader.getController();
                    answerTemplate.setAnsData(value);
                }
            }
        });


    }

    private List<Answer> getAnswerList() {
        List<Answer> list = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM help_post_answer WHERE postId = '"+helpPostId+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){

                Answer answer = new Answer();
                Blob blob = queryResult.getBlob("answerPic");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    answer.setAnsPic(image);
                }else {answer.setAnsPic(null);}
                if(queryResult.getString("answerCap") != null ) {
                    answer.setAnsText(queryResult.getString("answerCap"));
                }else {answer.setAnsText("");}
                answer.setpId(queryResult.getString("postId"));
                list.add(answer);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }


        return list;
    }

    void SetQuestionAndImage(){
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
                    questionImage.setFitHeight(200);
                    questionImage.setFitWidth(200);
                }
                topicName.setText(queryResult.getString("topic"));
                quesText.setText(queryResult.getString("helpText"));


            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }
    }
}
