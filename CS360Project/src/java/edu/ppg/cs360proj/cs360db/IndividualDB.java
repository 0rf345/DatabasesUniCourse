/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import edu.ppg.cs360proj.cs360db.model.Individual;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IndividualDB extends ClientDB {
	public static List<Individual> getIndividuals() {
		List<Individual> indivs = new ArrayList<>();

		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from individual;");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			while(res.next() == true) {
				Individual indiv = new Individual();
				indiv.setAccountID(res.getString("accid"));
				indiv.setClientUName(res.getString("usern"));
				indiv.setClientPass(res.getString("userp"));
				indiv.setExpDate(res.getString("expdate"));
				indiv.setCreditLimit(res.getInt("creditlmt"));
				indiv.setCurrentDebt(res.getInt("currdebt"));
				indiv.setAvailableCredit(res.getInt("availcredit"));
				indiv.setfName(res.getString("fname"));
				indiv.setlName(res.getString("fname"));
				indivs.add(indiv);
			}

			stmt.close();
			con.close();

		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}

		return indivs;
	}

	public static Individual getIndividual(String clientUName) {
		Individual indiv = new Individual();
		
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from individual ")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			if (res.next() == true) {
				indiv.setAccountID(res.getString("accid"));
				indiv.setClientUName(res.getString("usern"));
				indiv.setClientPass(res.getString("userp"));
				indiv.setExpDate(res.getString("expdate"));
				indiv.setCreditLimit(res.getInt("creditlmt"));
				indiv.setCurrentDebt(res.getInt("currdebt"));
				indiv.setAvailableCredit(res.getInt("availcredit"));
				indiv.setfName(res.getString("fname"));
				indiv.setlName(res.getString("fname"));
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return indiv;
	}
	
	public static void addIndividual(Individual indiv) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("insert into individual")
					.append(" ( usern, userp, expdate, creditlmt, currdebt, availcredit, fname, lname ) ")
					.append(" VALUES (")
					.append("'").append(indiv.getClientUName()).append("'")
					.append("'").append(indiv.getClientPass()).append("'")
					.append("'").append(indiv.getExpDate()).append("'")
					.append("'").append(indiv.getCreditLimit()).append("'")
					.append("'").append(indiv.getCurrentDebt()).append("'")
					.append("'").append(indiv.getAvailableCredit()).append("'")
					.append("'").append(indiv.getfName()).append("'")
					.append("'").append(indiv.getlName()).append("'")
					.append(");");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully deleted from the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void updateIndividual(Individual indiv) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("update individual")
					.append(" set ")
					.append(" userp = ").append("'").append(indiv.getClientPass()).append("'")
					.append(" expdate = ").append("'").append(indiv.getExpDate()).append("'")
					.append(" creditlmt = ").append("'").append(indiv.getCreditLimit()).append("'")
					.append(" currdebt = ").append("'").append(indiv.getCurrentDebt()).append("'")
					.append(" availcredit = ").append("'").append(indiv.getAvailableCredit()).append("'")
					.append(" fname = ").append("'").append(indiv.getfName()).append("'")
					.append(" lname = ").append("'").append(indiv.getlName()).append("'")
					.append(" where usern = ").append("'").append(indiv.getClientUName()).append("'")
					.append(";");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully updated in the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void deleteIndividual(String clientUName) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("delete from individual")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");
			
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully deleted from the database.");
			
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void genTables() {
		String tableQuery =
			"create table individual\n" +
			"(\n" +
			"	id           int(6) not null auto_increment primary key,\n" +
			"	accid        varchar(9) as '00-'+right('000000'+cast(id as varchar(6)), 6) persistent,\n" +
			"	usern        varchar(32) not null,\n" +
			"	userp        varchar(64) not null,\n" +
			"	expdate      date,\n" +
			"	creditlmt    decimal(15,2) not null default 0,\n" +
			"	currdebt     decimal(15,2) not null default 0,\n" +
			"	availcredit  decimal(15,2) not null default 0,\n" +
			"	fname        varchar(64) not null,\n" +
			"	lname        varchar(64) not null\n" +
			") engine = InnoDB;";
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			stmt.executeUpdate(tableQuery);

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndividualDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}