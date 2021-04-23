package com.login.servlet;


import java.util.HashMap;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;
import com.login.service.LoginService;
import com.login.service.LoginServiceImp;

@Path("/login")
public class LoginServlet {

	LoginService loginServiceObj = new LoginServiceImp();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("email") String email, @FormParam("password") String password) {	
		JsonObject obj = loginServiceObj.login(email, password);
		return Response.ok(obj.toString()).build();
	}
	
	@GET
	@Path("/getUserLoginId/{UserId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserLoginId(@PathParam("userId") String User_Id, @HeaderParam("authString") String authString) {
		int loginId = loginServiceObj.getUserLoginId(User_Id);
		return Response.ok(String.valueOf(loginId)).build();
	}
	
	@POST
	@Path("/resetPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetPassword(@FormParam("userId") String User_Id, @FormParam("currentPassword") String currentPassword, @FormParam("newPassword") String newPassword, @HeaderParam("authString") String authString) {
		String status = loginServiceObj.resetPassword(User_Id, currentPassword, newPassword);
		return Response.ok(status).build();
	}
	
	@POST
	@Path("/verifyPassword")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyPassword(@FormParam("userId") String User_Id, @FormParam("currentPassword") String currentPassword, @HeaderParam("authString") String authString) {
		String status = loginServiceObj.verifyPassword(User_Id, currentPassword);
		return Response.ok(status).build();
	}
	
	@GET
	@Path("/getRoleName/{roleId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoleName(@PathParam("roleId") String roleId, @HeaderParam("authString") String authString) {
		String roleName = loginServiceObj.getRoleName(roleId);
		return Response.ok(roleName).build();
	}
	
	@GET
	@Path("/authorizeUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authorizeUser(@HeaderParam("authString") String authString) {
		JsonObject obj = loginServiceObj.getAutization(authString);
		return Response.ok(obj.toString()).build();
	}
	
	@GET
	@Path("/getUserDetails/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetails(@PathParam("userId") String userId, @HeaderParam("authString") String authString) {
		return Response.ok(loginServiceObj.getUserDetails(userId).toString()).build();
	}
	
	@PUT
	@Path("/updateUserDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserDetails(@FormParam("userId") String userId, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("age") String age, @FormParam("gender") String gender, @FormParam("address") String address, @FormParam("mobileNumber") String mobileNumber, @FormParam("email") String email) {
		return Response.ok(loginServiceObj.updateUserDetails(userId, firstName, lastName, age, gender, address, mobileNumber, email).toString()).build();
	}
}
