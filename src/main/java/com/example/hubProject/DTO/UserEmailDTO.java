package com.example.hubProject.DTO;

public class UserEmailDTO {

    String email;
    String userType;

    public UserEmailDTO(String email,String userType) {
        this.email = email;
        this.userType=userType;
    }
    public UserEmailDTO()
    {

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
