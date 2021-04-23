package com;

import model.Ship;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*;


//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/Ship") 
public class ShippingService {
	
	Ship shipObj = new Ship();
	
	@GET
	@Path("/getShipD") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
		return shipObj.readItems();
		
	}
	
	@POST
	@Path("/addShipD") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("productID") int productID,
							@FormParam("firstName") String firstName,
							@FormParam("lastName") String lastName,
							@FormParam("userName") String userName,
							@FormParam("email") String email, 
							@FormParam("country") String country,
	{ 
			String output = shipObj.insertItem(productID, firstName, lastName, userName, email, country); 
			return output; 
	}
	
	@PUT
	@Path("/updateShipD") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
			//Convert the input string to a JSON object 
			JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
	
			//Read the values from the JSON object
			String productID = itemObject.get("productID").getAsString(); 
			String firstName = itemObject.get("firstName").getAsString(); 
			String lastName = itemObject.get("lastName").getAsString();
			String userName = itemObject.get("userName").getAsString();
			String email = itemObject.get("email").getAsString(); 
			String country = itemObject.get("country").getAsString();
			
			String output = shipObj.updateItem(productID, firstName, lastName, userName, email, country); 
	
			return output; 
	}
	
	@DELETE
	@Path("/deleteShipD") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
			//Convert the input string to an XML document
			Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
	 
			//Read the value from the element <itemID>
			String productID = doc.select("productID").text(); 
	 
			String output = shipObj.deleteItem(productID); 
	
			return output; 
	}

}
