package com;

import model.Payment;

//REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//JSON
import com.google.gson.*;


//XML
import org.jsoup.*; 
import org.jsoup.nodes.Document;

@Path("/Pay") 
public class PaymentService {
	
	Payment payObj = new Payment();
	
	@GET
	@Path("/getPayment") 
	@Produces(MediaType.TEXT_HTML) 
	public String readPayment() 
	{ 
		return payObj.readPayment();
		
	}
	
	@POST
	@Path("/addPayemt") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertPayment(@FormParam("accountID") int accId, 
							@FormParam("cardType") String cardType, 
							@FormParam("name") String name, 
							@FormParam("cardNo") String cardNo,
							@FormParam("expireDate") String expireDate,
				
	{ 
			String output = payObj.insertPayment(accId, cardType, name, cardNo, expireDate); 
			return output; 
	}
	
	@PUT
	@Path("/updatePayment") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatePayment(String paymentData) 
	{ 
			 
			 
	
		
			String accountID = paymentObject.get("accountID").getAsString(); 
			String cardType = paymentObject.get("cardType").getAsString(); 
			String nameOnCard = paymentObject.get("name").getAsString(); 
			String cardNo = paymentObject.get("cardNo").getAsString();
			String expireDate = paymentObject.get("expireDate").getAsString();
	
			
			String output = payObj.updatePayment(accountID, cardType, name, cardNo, expireDate); 
	
			return output; 
	}
	
	@DELETE
	@Path("/deletePayment") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deletePayment(String paymentData) 
	{  
	 
			String accountID = doc.select("accountID").text(); 
	 
			String output = payObj.deletePayment(accountID); 
	
			return output; 
	}

}