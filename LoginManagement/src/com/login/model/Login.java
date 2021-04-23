package com.login.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="login")
public class Login {

	private int loginId;
	private int loginRole;
	private String email;
	private String password;
	
	public Login() {
		
	}
	
	public Login(int loginId, int loginRole, String email, String password) {
		super();
		this.loginId = loginId;
		this.loginRole = loginRole;
		this.email = email;
		this.password = password;
	}
	
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public int getLoginRole() {
		return loginRole;
	}
	public void setLoginRole(int loginRole) {
		this.loginRole = loginRole;
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
	
}
