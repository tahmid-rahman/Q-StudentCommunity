package com.example.q_studentcommunity;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.q_studentcommunity.loginPage.CurrentUserName;


public class chatPage implements Initializable {
    @FXML private VBox chatHolder;
    @FXML
    private TextField msgField;
    @FXML
    private ScrollPane scBox;
    @FXML
    private VBox userList;

    @FXML
    private Label username;
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    private boolean isCommunity = true;
    private String CurrentReceiver = null;
    String Username;
    @FXML void onLogoutButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");
        stage.show();
    }
    @FXML protected void onOpinionPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onHelpPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onChatsPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chatPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onProfilePageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profilePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onSettingPageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settingPage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }
    @FXML protected void onResourcePageButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("resourcePage.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("StudentCommunity");
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            socket = new Socket("127.0.0.1",8080);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(loginPage.CurrentUserName);
            updatePhoto();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Socket Server is off.\nWait or contact admin to start server.",ButtonType.OK);
            alert.show();
            throw new RuntimeException(e);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String rcvmsg = dis.readUTF();
                        if(rcvmsg.contains("<c>") && isCommunity){
                            String []array=rcvmsg.split("<c>");
                            String msg = array[1];
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("receiverBox.fxml"));
                            Node node = loader.load();
                            ReceiverBox send = loader.getController();
                            Platform.runLater(() -> {
                                send.receiverMsg.setText(msg);
                                chatHolder.getChildren().add(node);
                                scBox.setVvalue(1D);
                            });

                        }else if(!rcvmsg.contains("<c>") && !isCommunity){
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("receiverBox.fxml"));
                            Node node = loader.load();
                            ReceiverBox send = loader.getController();
                            Platform.runLater(() -> {
                                send.receiverMsg.setText(rcvmsg);
                                chatHolder.getChildren().add(node);
                                scBox.setVvalue(1D);
                            });
                            System.out.println(rcvmsg);
                        }


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
        thread.setDaemon(true);
        Platform.runLater(thread::start);

    }
    private void updatePhoto(){

    }
    @FXML
    void onMsgSendbtn() throws IOException {
        String msg = msgField.getText();
        if (msg != null && !msg.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("senderBox.fxml"));
            Node node = loader.load();
            SenderBox send = loader.getController();
            send.senderMsg.setText(msg);
            chatHolder.getChildren().add(node);
            if(!isCommunity){
                dos.writeUTF(CurrentReceiver+"<@>"+msg);
            }else {
                dos.writeUTF(loginPage.CurrentUserName+"<c>"+msg);
            }
            msgField.setText("");
        }
    }



    @FXML
    void onActiveUserClick(ActionEvent event) throws IOException {
        userList.getChildren().clear();
        userList.setVisible(true);
        isCommunity = false;
        ArrayList<String> list = getActiveUserList();
        chatHolder.getChildren().clear();
        for(String l: list){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ActiveUser.fxml"));
            AnchorPane pan = loader.load();
            ActiveUser user = loader.getController();
            user.setUserName(l);
            pan.setOnMouseClicked(event1 -> {
                String cUser = user.getUser();
                if(cUser.equals(CurrentUserName)){
                    username.setText(cUser+" (self)");
                }else {username.setText(cUser);}

                CurrentReceiver = cUser;
                chatHolder.getChildren().clear();
            });
            userList.getChildren().add(pan);

        }
    }


    @FXML
    void onCommunityButtonClick(ActionEvent event) {
        username.setText("Community");
        userList.setVisible(false);
        isCommunity=true;
    }
    private ArrayList<String> getActiveUserList(){
        ArrayList list = new ArrayList<>();
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String data = "SELECT username FROM chat_user_list WHERE 1";
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(data);
            while (rs.next()){
                list.add(rs.getString("username"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }

}
