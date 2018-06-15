package com.example.joohonga.chatapplication;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String photo;
    private String uid;
    List<User> friends;



    public User(){ }
    public User(String email, String photo){
        this.friends = new ArrayList<>();
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

    @Override
    public String toString() {
        return this.getEmail()+this.getUid()+this.getPhoto();
    }
}
