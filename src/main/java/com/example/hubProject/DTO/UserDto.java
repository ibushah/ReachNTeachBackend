
package com.example.hubProject.DTO;


import java.util.Date;

public class UserDto {

	private Long id;
	private String name;
	private String email;
	private String password;
	private Boolean active;
	private String userType;
private Boolean status;
private Date expiry;

	public UserDto() {
	}

	public UserDto(Long id, String name, String email, String password, Boolean active, String userType, Boolean status, Date expiry) {
		this.id = id;
		this.name = name;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


