/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ppg.cs360proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author looselyrigorous
 */
public class DBCommon {
	private static final String URL = "jdbc:mysql://SERVER_URL_HERE";
	private static final String DATABASE = "CS360DB";
	private static final int PORT = 3306;
	private static final String UNAME = "CS360_CCC";
	private static final String PASSWD = "CS360_CCC";

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(URL + ":" + PORT + "/" + DATABASE
			+ "?zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", UNAME, PASSWD);
    }
}
