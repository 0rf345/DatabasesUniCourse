/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Constantine Kalivas
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
