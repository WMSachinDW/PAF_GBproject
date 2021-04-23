package com;

import model.OrderService;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*;


//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/Orders") 
public class OrderService {
	
	Order orderObj = new Order();
	
	@GET
	@Path("/getOrder") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
		return orderObj.readItems();
		
	}
	
	@POST
	@Path("/addOrder") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("productID") int productID,
							@FormParam("productName") String productName,
							@FormParam("productPrice") String productPrice,

	{ 
			String output = shipObj.insertItem(productID, productName, productPrice); 
			return output; 
	}
	
	@PUT
	@Path("/updateOrder") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
			//Convert the input string to a JSON object 
			JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
	
			//Read the values from the JSON object
			String productID = itemObject.get("productID").getAsString(); 
			String firstName = itemObject.get("productName").getAsString(); 
			String lastName = itemObject.get("productPrice").getAsString();
			
			String output = shipObj.updateItem(productID, productName, productPrice); 
	
			return output; 
	}
	
	@DELETE
	@Path("/deleteOrder") 
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
