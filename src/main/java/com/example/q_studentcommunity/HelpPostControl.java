package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelpPostControl implements Initializable {
    @FXML
    private TableView<Account> HelpPostTable;

    @FXML
    private TableColumn<Account, String> caption;

    @FXML
    private TableColumn<?, ?> control;

    @FXML
    private TableColumn<Account, String> postId;

    @FXML
    private Pane resourcePane;

    @FXML
    private TableColumn<HelpPost, String> userName;
    public ObservableList<Account> accounts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    public void loadData() {


        userName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        postId.setCellValueFactory(new PropertyValueFactory<>("Post id"));
        caption.setCellValueFactory(new PropertyValueFactory<>("Caption"));
        HelpPostTable.setItems(accounts);


    }
}
 /*   private ObservableList<Account> getHelpControlList() {
        List<Account> list = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM help_post WHERE 1 ORDER BY postId DESC";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);

            while (queryResult.next()){
                Account account = new Account();
                if(queryResult.getString("helpText") != null ) {
                    account.setCaption(queryResult.getString("helpText"));
                }else {account.setCaption("");}
                account.setUsername(queryResult.getString("postername"));
                account.setId(queryResult.getString("postId"));
                list.add(account);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();

        }


        return list;


*/