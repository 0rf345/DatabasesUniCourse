/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

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

	public static boolean validateDB() {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet res;

			res = dbmd.getTables(null, null, "individual", null);
			if(!res.next()) {
				IndividualDB.genTables();
			}

			res = dbmd.getTables(null, null, "company", null);
			if(!res.next()) {
				CompanyDB.genTables();
			}

			res = dbmd.getTables(null, null, "merchant", null);
			if(!res.next()) {
				MerchantDB.genTables();
			}

			// TODO: Add clauses for "TransactionDB"
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DBCommon.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(URL + ":" + PORT + "/" + DATABASE
				+ "?zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", UNAME, PASSWD);
		} catch(SQLException | ClassNotFoundException ex) {
			// Log exception
			Logger.getLogger(DBCommon.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
