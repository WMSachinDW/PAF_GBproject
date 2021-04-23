package com.login.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection connection;
	private static String DBDriver = "com.mysql.cj.jdbc.Driver";
	private static String DBUrl = "jdbc:mysql://localhost:3306/healthcare?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String DBUser = "root";
	private static String DBPassword = "";

	private DBConnection() {

	}

	public static Connection connect() throws SQLException, ClassNotFoundException {
		Class.forName(DBDriver);
		Connection con = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
		return con;
	}

}