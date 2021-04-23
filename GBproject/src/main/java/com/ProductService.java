package com;


	import model.Products; 
	
	//For REST Service
	import javax.ws.rs.*; 
	import javax.ws.rs.core.MediaType; 
	
	//For JSON
	import com.google.gson.*; 
	
	//For XML
	import org.jsoup.*; 
	import org.jsoup.parser.*; 
	import org.jsoup.nodes.Document; 
	
	@Path("/Product") 
	public class ProductService 
	{ 
		Products productObj = new Products(); 
		@GET
		@Path("/") 
		@Produces(MediaType.TEXT_HTML) 
		public String readProducts() 
		{ 
			return "Hello"; 
		}
	}
