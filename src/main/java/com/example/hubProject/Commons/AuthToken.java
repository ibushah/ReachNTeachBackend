package com.example.hubProject.Commons;

public class AuthToken {

    private String token;
    private String email;
    private String userType;

    public AuthToken(String token, String email, String userType) {
        this.token = token;
        this.email = email;
        this.userType = userType;
    }

    public AuthToken(){

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }
}
