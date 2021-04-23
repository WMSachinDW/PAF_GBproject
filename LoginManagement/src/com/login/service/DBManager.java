package com.login.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.login.model.Response;
import com.login.util.DBConnection;

public class DBManager {

	public static JsonObject login(String email, String password) {
		JsonObject l = new JsonObject();

		try {
			Connection con = DBConnection.connect();

			String verifyLogin = "SELECT * FROM login WHERE Login_Email = ? and Login_Password = ?";
			PreparedStatement ps_verifyLogin = con.prepareStatement(verifyLogin);
			ps_verifyLogin.setString(1, email);
			ps_verifyLogin.setString(2, password);

			ResultSet rs_verifyLogin = ps_verifyLogin.executeQuery();
			if (rs_verifyLogin.first() != false) {
					String emailColonPassword = "authKey" + email + ":" + password;
					String authString = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());

					String getUrl = "http://localhost:8080/HealthCarePatientManagement/webapi/patient/loginId/"
							+ String.valueOf(rs_verifyLogin.getInt(1));
					JsonObject object = Response.getResponse(getUrl, authString);

					l.addProperty("status", "success");
					l.addProperty("userId", object.get("userId").getAsString());
					l.addProperty("authString", authString);
			} else {
				l.addProperty("status", "error");
				l.addProperty("userId", 0);
				l.addProperty("authString", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return l;
	}

	public static int getLoginId(String userId) {
		int loginId = 0;
		try {

			Connection con = DBConnection.connect();

			String getLoginId = "SELECT loginId FROM user WHERE userId = " + userId;
			PreparedStatement ps_getLoginId = con.prepareStatement(getLoginId);
			ResultSet rs_getLoginId = ps_getLoginId.executeQuery();

			while (rs_getLoginId.next()) {
				loginId = rs_getLoginId.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginId;
	}

	public static String getRoleName(String roleId) {

		String roleName = "";

		try {

			Connection con = DBConnection.connect();

			String getroleName = "SELECT roleName FROM role WHERE role_id = " + roleId;
			PreparedStatement ps_getroleName = con.prepareStatement(getroleName);
			ResultSet rs_getroleName = ps_getroleName.executeQuery();

			while (rs_getroleName.next()) {
				roleName = rs_getroleName.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return roleName;
	}

	public static String verifyPassword(String UserId, String currentPassword) {
		String status = "";

		try {

			Connection con = DBConnection.connect();

			int loginId = getLoginId(UserId);
			String getLoginId = "SELECT * FROM login WHERE Login_Id = ? AND Login_Password = ?";

			PreparedStatement ps_verifyPassword = con.prepareStatement(getLoginId);
			ps_verifyPassword.setInt(1, loginId);
			ps_verifyPassword.setString(2, currentPassword);

			ResultSet rs_verifyPassword = ps_verifyPassword.executeQuery();

			if (rs_verifyPassword.first()) {
				status = "success";
			} else {
				status = "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public static String resetPassword(String UserId, String currentPassword, String newPassword) {

		String status = "";

		try {
			Connection con = DBConnection.connect();
			int loginId = getLoginId(UserId);
			String passwordVerification = verifyPassword(UserId, currentPassword);

			if (passwordVerification.equalsIgnoreCase("success")) {
				String changePassword = "UPDATE login SET Login_Password = " + newPassword;

				PreparedStatement ps_changePassword = con.prepareStatement(changePassword);
				if (ps_changePassword.executeUpdate() > 0) {
					status = "success";
				} else {
					status = "fail";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public static JsonObject getAutization(String header) {
		JsonObject obj = new JsonObject();
		try {
			if (header != null) {
				Connection con = DBConnection.connect();
				byte[] decodedBytes = Base64.getDecoder().decode(header);
				String decodedString = new String(decodedBytes);
				if (decodedString.contains("authKey")) {

					String value = decodedString.replace("authKey", "");
					String[] values = value.split(":");

					String verifyLogin = "SELECT * FROM login WHERE Login_Email = ? and Login_Password = ?";
					String getRoleName = "SELECT * FROM role WHERE role_Id = ?";
					PreparedStatement ps_verifyLogin = con.prepareStatement(verifyLogin);
					PreparedStatement ps_getRoleName = con.prepareStatement(getRoleName);
					ps_verifyLogin.setString(1, values[0]);
					ps_verifyLogin.setString(2, values[1]);

					ResultSet rs_verifyLogin = ps_verifyLogin.executeQuery();

					while (rs_verifyLogin.next()) {
						ps_getRoleName.setInt(1, rs_verifyLogin.getInt(2));
						ResultSet rs_getRoleName = ps_getRoleName.executeQuery();
						while (rs_getRoleName.next()) {

							obj.addProperty("status", "authorized");
							obj.addProperty("role", rs_getRoleName.getString(2));

						}

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public static JsonObject getUserDetails(String userId) {
		JsonObject obj = new JsonObject();
		Connection con;
		try {
			con = DBConnection.connect();
			String query = "SELECT u.loginId, u.firstName, u.lastName, u.age, u.gender, u.address, u.mobileNumber, l.Login_Email, l.Login_Email FROM user u, login l WHERE u.userId=" + userId + " AND u.loginId = l.Login_Id";
			PreparedStatement ps_getDetails = con.prepareStatement(query);
			
			ResultSet rs_getDetails = ps_getDetails.executeQuery();
			
			while(rs_getDetails.next()) {
				obj.addProperty("loginId", rs_getDetails.getInt(1));
				obj.addProperty("firstName", rs_getDetails.getString(2));
				obj.addProperty("lastName", rs_getDetails.getString(3));
				obj.addProperty("age", rs_getDetails.getInt(4));
				obj.addProperty("gender", rs_getDetails.getString(5));
				obj.addProperty("address", rs_getDetails.getString(6));
				obj.addProperty("mobileNumber", rs_getDetails.getString(7));
				obj.addProperty("email", rs_getDetails.getString(8));
				obj.addProperty("password", rs_getDetails.getString(9));
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public static JsonObject updateUserDetails(String userId, String firstName, String lastName, String age, String gender, String address, String mobileNumber,  String email) {
		JsonObject obj = new JsonObject();
		Connection con;
		
		String updateUser = "update user set firstName=?, lastName=?, age=?, gender=?, address=?, mobileNumber=? WHERE userId=" + userId;
		String updateLogin = "update login set Login_Email=? WHERE Login_Id=(SELECT loginId FROM user WHERE userId=?)";
		
		try {
			con = DBConnection.connect();
			
			PreparedStatement ps_updateUser = con.prepareStatement(updateUser);
			ps_updateUser.setString(1, firstName);
			ps_updateUser.setString(2, lastName);
			ps_updateUser.setInt(3, Integer.parseInt(age));
			ps_updateUser.setString(4, gender);
			ps_updateUser.setString(5, address);
			ps_updateUser.setString(6, mobileNumber);
			
			PreparedStatement ps_updateLogin = con.prepareStatement(updateLogin);
			ps_updateLogin.setString(1, email);
			ps_updateLogin.setString(2, userId);
			
			if (ps_updateUser.executeUpdate() > 0) {

				if (ps_updateLogin.executeUpdate() > 0) {
					obj.addProperty("status", "success");
				}else {
					obj.addProperty("status", "fail");
				}

			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}
