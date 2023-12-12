package com.example.q_studentcommunity;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GEmailSender {
    public void sendEmail(String to, String from, String subject, String text) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");


        String username = "roxboy.tahmid";
        String password = "dfffsvtazwnlarkz";


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from,"QUEUE"));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"You entered a Invalid email address.", ButtonType.CLOSE);
            alert.show();
            //throw new SendFailedException;
           // e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
