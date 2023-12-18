package com.example.q_studentcommunity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminPanel implements Initializable {

    @FXML
    private Pane AdminPane;

    @FXML
    private Label activeUser;

    @FXML
    private Pane dashPane;

    @FXML
    private Button dashboard;

    @FXML
    private Button dropbox;

    @FXML
    private Button feed;

    @FXML
    private Button help;

    @FXML
    private Circle profileCircle;

    @FXML
    private Button resource;

    @FXML
    private Button server;

    @FXML
    private Label totalHelpPost;

    @FXML
    private Label totolHomePost;

    @FXML
    private Label totolUser;
    @FXML
    private LineChart<?, ?> lineChat;




    private LocalDate localDate = LocalDate.now();
    @FXML
    void dashboard(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#f87c29");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
        */
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPanel.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("AdminPane");
        stage.show();
    }

    @FXML
    void feedClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#f87c29");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FeedPostControl.fxml"));
        AdminPane.getChildren().add(loader.load());
    }

    @FXML
    void helpClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#f87c29");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25")
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HelpPostControl.fxml"));
        AdminPane.getChildren().add(loader.load());
    }

    @FXML
    void dropboxClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#f87c29");
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Dropbox.fxml"));
        AdminPane.getChildren().add(loader.load());
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("authorizerLogin.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();
    }

    @FXML
    void resourceClick(ActionEvent event) throws IOException {
        /*
        server.setStyle(" -fx-background-color :#dc6e25");
        resource.setStyle(" -fx-background-color :#f87c29");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
        */
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ResourceControl.fxml"));
        AdminPane.getChildren().add(loader.load());

    }

    @FXML
    void serverClick(ActionEvent event) throws IOException {
        dashPane.setVisible(false);
/*
        server.setStyle(" -fx-background-color :#f87c29");
        resource.setStyle(" -fx-background-color :#dc6e25");
        dashboard.setStyle(" -fx-background-color :#dc6e25");
        help.setStyle(" -fx-background-color :#dc6e25");
        feed.setStyle(" -fx-background-color :#dc6e25");
        dropbox.setStyle(" -fx-background-color :#dc6e25");
*/
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ServerControl.fxml"));
        AdminPane.getChildren().add(loader.load());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashPane.setVisible(true);
//        dashboard.setStyle(" -fx-background-color :#f87c29");
        try {
            setTotalUserInDashboard();
            setTotalActiveUserInDashboard();
            setTotalHelpPostInDashboard();
            setTotalNormalPostInDashboard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        XYChart.Series chartData = new XYChart.Series();
        try {
            for (int i = 30; i >= 0; i--) {
                String y = String.valueOf(localDate.minusDays(i));
                int x = setX(localDate.minusDays(i));
               // System.out.println(y + "    " + x);
                chartData.getData().add(new XYChart.Data(y, x));
            }
            lineChat.getData().add(chartData);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //lineChat.getData().add();



    }
    public void setTotalUserInDashboard() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM logindata WHERE 1";
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(getData);
        int user=0;
        while(queryResult.next()){
            user+=1;
        }
        totolUser.setText(user+" person");
    }
    public void setTotalActiveUserInDashboard() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM chat_user_list WHERE 1";
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(getData);
        int user=0;
        while(queryResult.next()){
            user+=1;
        }
        activeUser.setText(user+" person");
    }
    public void setTotalHelpPostInDashboard() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM help_post WHERE 1";
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(getData);
        int user=0;
        while(queryResult.next()){
            user+=1;
        }
        totalHelpPost.setText(user+" person");
    }
    public void setTotalNormalPostInDashboard() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM home_page_post WHERE 1";
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(getData);
        int user=0;
        while(queryResult.next()){
            user+=1;
        }
        totolHomePost.setText(user+" person");
    }
    private int setX(LocalDate date){
        int x = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String getData = "SELECT * FROM help_post WHERE 1";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getData);
            while (queryResult.next()){
                //System.out.println(queryResult.getDate("date"));
                //System.out.println(date);
                String d1 = String.valueOf(queryResult.getDate("date"));
                String d2 = String.valueOf(date);
                if(d1.equals(d2)){
                    x+=1;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return x;
    }
}
