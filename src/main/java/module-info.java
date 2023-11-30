module com.example.q_studentcommunity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires mail;
    requires java.desktop;


    opens com.example.q_studentcommunity to javafx.fxml;
    exports com.example.q_studentcommunity;
}