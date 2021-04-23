package model;

import java.sql.*;

public class Ship {
	
			//A common method to connect to the DB
			private Connection connect() 
				{ 
			
						Connection con = null; 
			
						try
						{ 
							//Class.forName("com.mysql.jdbc.Driver"); 
							Class.forName("com.mysql.cj.jdbc.Driver");
							
							//Provide the correct details: DBServer/DBName, username, password 
							con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", ""); 
							
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						} 
			
						return con; 
				}
	
			public String insertItem(int prodID, String fname, String lname, String uname, String email) 
			{ 
		
				String output = ""; 
		
				try
				{ 
		
					Connection con = connect(); 
		
					if (con == null) 
		
					{return "Error while connecting to the database for inserting."; } 
		
					// create a prepared statement
					String query = " insert into shippingdetails (`productID`,`firstName`,`lastName`,`userName`,`email`)"
								+ " values (?, ?, ?, ?, ?);
		
					PreparedStatement preparedStmt = con.prepareStatement(query); 
					
					// binding values
					 preparedStmt.setInt(1, prodID); 
					 preparedStmt.setString(2, fname); 
					 preparedStmt.setString(3, lname); 
					 preparedStmt.setString(4, uname); 
					 preparedStmt.setString(5, email);
		
					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 output = "Inserted successfully"; 
				} 
				catch (Exception e) 
				{ 
					 
					output = "Error while inserting the item."; 
					System.err.println(e.getMessage()); 
					 
				} 
				return output; 
			}
			
			public String readItems() 
			 { 
			 
					String output = ""; 
			 
					try
					{ 
			 
						Connection con = connect(); 
			 
						if (con == null) 
						{return "Error while connecting to the database for reading."; } 
			 
						// Prepare the html table to be displayed
						output = "<table border='1'><tr><th>productID</th><th>First Name</th>" +
								"<th>Last Name</th>" + 
								"<th>User Name</th>" +
								"<th>Email</th>";
 
			 
						String query = "select * from shippingdetails"; 
						Statement stmt = con.createStatement(); 
						ResultSet rs = stmt.executeQuery(query); 
			 
						// iterate through the rows in the result set
						while (rs.next()) 
						{ 
								String productID = Integer.toString(rs.getInt("productID")); 
								String firstName = rs.getString("firstName"); 
								String lastName = rs.getString("lastName");
								String userName = rs.getString("userName");
								String email = rs.getString("email"); 
			 
								// Add into the html table
								output += "<tr><td>" + productID + "</td>"; 
								output += "<td>" + firstName + "</td>"; 
								output += "<td>" + lastName + "</td>";
								output += "<td>" + userName + "</td>";
								output += "<td>" + email + "</td>";
						} 
			 
						con.close(); 
			 
						// Complete the html table
						output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 	output = "Error while reading the items."; 
				 	System.err.println(e.getMessage()); 
			 } 
			 
					return output; 
		}
		
			public String updateItem(String ID, String fName, String lName, String uname,  String emailadd)
			{ 
				 String output = ""; 
				 
				 try
				 { 
				 
					 	Connection con = connect(); 
				 
					 	if (con == null) 
					 	{return "Error while connecting to the database for updating."; } 
				 
					 	// create a prepared statement
					 	String query = "UPDATE shippingdetails SET firstName=?,lastName=?,userName=?,email=? WHERE productID=?"; 
				 
					 	PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
					 	// binding values
					 	preparedStmt.setString(1, fName); 
					 	preparedStmt.setString(2, lName);
					 	preparedStmt.setString(3, uname);
					 	preparedStmt.setString(4, emailadd);
					 	preparedStmt.setInt(10, Integer.parseInt(ID)); 
				 
					 	// execute the statement
					 	preparedStmt.execute(); 
					 	con.close(); 
				 
					 	output = "Updated successfully"; 
				 } 
				 catch (Exception e) 
				 { 
					 	output = "Error while updating the item."; 
					 	System.err.println(e.getMessage()); 
				 } 
				 return output; 
		}
			
			public String deleteItem(String productID)
			{ 
			 	String output = ""; 
			 
			 	try
			 	{ 
			 
			 			Connection con = connect(); 
			 
			 			if (con == null) 
			 			{return "Error while connecting to the database for deleting."; } 
			 
			 			// create a prepared statement
			 			String query = "delete from shippingdetails where productID=?"; 
			 			
			 			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 			// binding values
			 			preparedStmt.setInt(1, Integer.parseInt(productID)); 
			 
			 			// execute the statement
			 			preparedStmt.execute(); 
			 			con.close(); 
			 
			 			output = "Deleted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 		output = "Error while deleting the item."; 
				 		System.err.println(e.getMessage()); 
			 } 
			 return output; 
		}

}
