package com.login.service;

import java.util.HashMap;

import com.google.gson.JsonObject;

public interface LoginService {

	public JsonObject login(String email, String password);
	public int getUserLoginId(String userId);
	public String getRoleName(String roleId);
	public String resetPassword(String User_Id, String currentPassword, String newPassword);
	public String verifyPassword(String User_Id, String currentPassword);
	public JsonObject getAutization(String header);
	public JsonObject getUserDetails(String userId);
	public JsonObject updateUserDetails(String userId, String firstName, String lastName, String age, String gender, String address, String mobileNumber,  String email);
}