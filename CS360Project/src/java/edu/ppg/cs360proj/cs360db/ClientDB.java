/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDB {
	public static boolean checkValidUName(String clientUName) {
		boolean valid = false;

		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from individual, company, merchant")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");

			stmt.execute(insQuery.toString());
			if (stmt.getResultSet().next() == true) {
				System.out.println("#DB: The member already exists");
				valid = false;
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return valid;
	}
}
