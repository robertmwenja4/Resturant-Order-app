package com.example.myapplication;

public class RegisterUser {

    public  String username, email, password, notificationToken;
    public long  phone;

    public RegisterUser() {
    }

    public RegisterUser(String username, String email, String password, String notificationToken, long phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.notificationToken = notificationToken;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
