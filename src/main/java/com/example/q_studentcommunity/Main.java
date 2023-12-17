package com.example.q_studentcommunity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

import static com.example.q_studentcommunity.loginPage.CurrentUserName;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login Page");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("file/Pink and Purple Minimal Q Letter Logo (2).png"))));
        stage.show();
    }
    @Override
    public void stop() throws Exception {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String data = "DELETE FROM chat_user_list WHERE username = '"+CurrentUserName+"'";
        Statement statement = connectDB.createStatement();
        statement.execute(data);
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}