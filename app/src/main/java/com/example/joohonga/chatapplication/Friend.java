package com.example.joohonga.chatapplication;

public class Friend {
    private String email;
    private String photo;
    private String uid;

    public Friend(){ }
    public Friend(String email,String photo){

        this.email = email;
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo ) {
        this.photo = photo;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
