package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class AiPage implements Initializable {

    @FXML
    private WebView webView;
    WebEngine chatGpt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatGpt = webView.getEngine();
        chatGpt.load("https://chat.openai.com/auth/login");
//        String script = "document.getElementById('username').value = 'hocosi2939@mcenb.com' ;" +
//                "document.getElementById('password').value = 'tahmid_howAreYou';" +
//                "document.getElementById('login-button').click();";
//        chatGpt.executeScript(script);
    }
}