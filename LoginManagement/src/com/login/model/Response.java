package com.login.model;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Response {

	public static JsonObject getResponse(String getUrl, String header) {

		Client client = new Client().create();
		WebResource webResource = client.resource(getUrl);
		ClientResponse response = webResource.accept("application/json").header("authString", header)
				.get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}
		String result = response.getEntity(String.class);
		JsonObject userObject = new JsonParser().parse(result).getAsJsonObject();
		return userObject;
	}

	public static javax.ws.rs.core.Response sendErrorResponse() {
		return javax.ws.rs.core.Response.serverError().entity("You're Not Authorized").build();
	}
	
}
