package com.example.q_studentcommunity;


import javafx.scene.image.Image;

public class Post {
    private String username;
    private String caption;
    private String userType;
    private Image profilePic;
    private Image postPic;
    private String qPoint;
    private String homePostId;

    public String getHomePostId() {
        return homePostId;
    }

    public void setHomePostId(String homePostId) {
        this.homePostId = homePostId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }


    public Image getPostPic() {
        return postPic;
    }

    public void setPostPic(Image postPic) {
        this.postPic = postPic;
    }

    public String getqPoint() {
        return qPoint;
    }

    public void setqPoint(String qPoint) {
        this.qPoint = qPoint;
    }
}