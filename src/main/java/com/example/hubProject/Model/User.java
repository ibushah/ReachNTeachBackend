package com.example.hubProject.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class User {



	@Id
	private String email;

	@Column
	private String password;

	@Column
	private Boolean active;

	@Column
	private String userType;


	@Column
	private Boolean status;

	@Column
	private Date expiry;


	public User() {
	}

	public User(String email, String password, Boolean active, String userType, Boolean status, Date expiry) {
		this.email = email;
		this.password = password;
		this.active = active;
		this.userType = userType;
		this.status = status;
		this.expiry = expiry;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}




}