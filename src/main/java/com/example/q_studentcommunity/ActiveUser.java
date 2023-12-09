package com.example.q_studentcommunity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ActiveUser {

    @FXML
    private Label UserName;

    @FXML
    private AnchorPane pane;
    public void setUserName(String name){
        UserName.setText(name);
    }
    public String getUser(){
        return UserName.getText();
    }

}
