package com.example.joohonga.chatapplication;

public class Chat {
    private String email;
    private String message;

    Chat(){

    }
    Chat(String message){
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
