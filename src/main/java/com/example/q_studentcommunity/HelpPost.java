package com.example.q_studentcommunity;


import javafx.scene.image.Image;
public class HelpPost {
    private String topicName;
    private String helpCap;
    private String dateTime;
    private Image helpPic;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getHelpCap() {
        return helpCap;
    }

    public void setHelpCap(String helpCap) {
        this.helpCap = helpCap;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Image getHelpPic() {
        return helpPic;
    }

    public void setHelpPic(Image helpPic) {
        this.helpPic = helpPic;
    }




}
