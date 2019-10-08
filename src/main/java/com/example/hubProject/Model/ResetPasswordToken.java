package com.example.hubProject.Model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String token;

    private Date created;

    private Date valid_until;

    private String email;

    public ResetPasswordToken() {
    }

    public ResetPasswordToken(Long id, String token, Date created, Date valid_until, String email) {
        this.id = id;
        this.token = token;
        this.created = created;
        this.valid_until = valid_until;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getValid_until() {
        return valid_until;
    }

    public void setValid_until(Date valid_until) {
        this.valid_until = valid_until;
    }
}
