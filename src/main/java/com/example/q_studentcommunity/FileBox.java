package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.q_studentcommunity.FolderName.SourceName;

public class FileBox implements Initializable {

    @FXML
    private Label sourceName;

    @FXML
    private VBox fileHolder;

    @FXML
    private Pane webPan;
    @FXML
    private Label SelectedFile;
    @FXML
    private ImageView imageViewer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sourceName.setText(SourceName);
        String fileLocation = "D:\\Q_StudentCommunity\\src\\main\\resources\\Resources\\"+SourceName;
        File file = new File(fileLocation);
        String[] arr = file.list();
        System.out.println(arr.length);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(String i: arr) {
                    File file1 = new File(fileLocation+"\\"+i);
                    if(!file1.isDirectory()){
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("FileName.fxml"));
                        try {
                            fileHolder.getChildren().add(loader.load());
                            FileName filerName = loader.getController();
                            filerName.SetFileNameText(i);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            }
        });
    }
    @FXML
    void onCrossButtonClick(MouseEvent event) throws IOException {


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resourcePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();
    }

}
