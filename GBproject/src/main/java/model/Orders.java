package model;
import java.sql.*;
public class Orders
	{ //A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		}
		catch (Exception e)
		{e.printStackTrace();}
		return con;
	}
	public String insertOrders(String productID, String productName, String productPrice)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for inserting."; }
			// create a prepared statement
			String query = " insert into orders
					(`productID`,`productName`,`productPrice`)"
					+ " values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, productID);
			preparedStmt.setString(3, productName);
			preparedStmt.setDouble(4, Double.parseDouble(price));
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
				public String readorders()
			{
					String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{return "Error while connecting to the database for reading."; }
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Product ID</th><th>Orders</th>" +
						"<th>Product Price</th>" +
						"<th>Product Name</th>" +
						"<th>Update</th><th>Remove</th></tr>";
				String query = "select * from orders";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next())
				{
					String productID = Integer.toString(rs.getInt("productID"));
					String productName = rs.getString("productName");
					String productPrice = rs.getString("productPrice");
					// Add into the html table
					output += "<tr><td>" + productID + "</td>";
					output += "<td>" + productName + "</td>";
					output += "<td>" + productPrice + "</td>";
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update'
				class='btn btn-secondary'></td>"
				+ "<td><form method='post' action='orders.jsp'>"
				+ "<input name='btnRemove' type='submit' value='Remove'
				class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + productID
						+ "'>" + "</form></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the orders.";
				System.err.println(e.getMessage());
			}
			return output;
			}
				public String updateProduct(String productID, String productName, String productPrice)
				{
					String output = "";
						try
						{
							Connection con = connect();
							if (con == null)
							{return "Error while connecting to the database for updating."; }
							// create a prepared statement
							String query = "UPDATE orders SET productName=?,productPrice=?
									WHERE productID=?";
											PreparedStatement preparedStmt = con.prepareStatement(query);
							// binding values
							preparedStmt.setString(1, productID);
							preparedStmt.setString(2, productName);
							preparedStmt.setDouble(3, Double.parseDouble(productPrice));
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
						String query = "delete from orders where productID=?";
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