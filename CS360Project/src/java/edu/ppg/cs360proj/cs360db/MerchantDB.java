/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import edu.ppg.cs360proj.cs360db.model.Merchant;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Constantine Kalivas
 */
public class MerchantDB extends ClientDB {
	public static List<Merchant> getMerchants() {
		List<Merchant> merchs = new ArrayList<>();

		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from merchant;");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			while(res.next() == true) {
				Merchant merch = new Merchant();
				merch.setAccountID(res.getString("id"));
				merch.setClientUName(res.getString("usern"));
				merch.setClientPass(res.getString("userp"));
				merch.setExpDate(res.getString("expdate"));
				merch.setCreditLimit(res.getInt("creditlmt"));
				merch.setCurrentDebt(res.getInt("currdebt"));
				merch.setAvailableCredit(res.getInt("availcredit"));
				merch.setfName(res.getString("fname"));
				merch.setlName(res.getString("fname"));
				merch.setCommission(res.getInt("commission"));
				merch.setProfit(res.getInt("profit"));
				merchs.add(merch);
			}

			stmt.close();
			con.close();

		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(MerchantDB.class.getName()).log(Level.SEVERE, null, ex);
		}

		return merchs;
	}

	public static Merchant getMerchant(String clientUName) {
		Merchant merch = new Merchant();
		
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from merchant ")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			if (res.next() == true) {
				merch.setAccountID(res.getString("id"));
				merch.setClientUName(res.getString("usern"));
				merch.setClientPass(res.getString("userp"));
				merch.setExpDate(res.getString("expdate"));
				merch.setCreditLimit(res.getInt("creditlmt"));
				merch.setCurrentDebt(res.getInt("currdebt"));
				merch.setAvailableCredit(res.getInt("availcredit"));
				merch.setfName(res.getString("fname"));
				merch.setlName(res.getString("fname"));
				merch.setCommission(res.getInt("commission"));
				merch.setProfit(res.getInt("profit"));
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(MerchantDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return merch;
	}
	
	public static void addMerchant(Merchant merch) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("insert into merchant")
					.append(" ( usern, userp, expdate, creditlmt, currdebt, availcredit, fname, lname, commission, profit ) ")
					.append(" VALUES (")
					.append("'").append(merch.getClientUName()).append("',")
					.append("'").append(merch.getClientPass()).append("',")
					.append("'").append(merch.getExpDate()).append("',")
					.append("'").append(merch.getCreditLimit()).append("',")
					.append("'").append(merch.getCurrentDebt()).append("',")
					.append("'").append(merch.getAvailableCredit()).append("',")
					.append("'").append(merch.getfName()).append("',")
					.append("'").append(merch.getlName()).append("',")
					.append("'").append(merch.getCommission()).append("',")
					.append("'").append(merch.getProfit()).append("'")
					.append(");");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully added to the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(MerchantDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void updateMerchant(Merchant merch) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("update merchant")
					.append(" set ")
					.append(" userp = ").append("'").append(merch.getClientPass()).append("',")
					.append(" expdate = ").append("'").append(merch.getExpDate()).append("',")
					.append(" creditlmt = ").append("'").append(merch.getCreditLimit()).append("',")
					.append(" currdebt = ").append("'").append(merch.getCurrentDebt()).append("',")
					.append(" availcredit = ").append("'").append(merch.getAvailableCredit()).append("',")
					.append(" fname = ").append("'").append(merch.getfName()).append("',")
					.append(" lname = ").append("'").append(merch.getlName()).append("',")
					.append(" commission = ").append("'").append(merch.getCommission()).append("',")
					.append(" profit = ").append("'").append(merch.getProfit()).append("',")
					.append(" where usern = ").append("'").append(merch.getClientUName()).append("'")
					.append(";");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully updated in the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(MerchantDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void deleteMerchant(String clientUName) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("delete from merchant")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");
			
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully deleted from the database.");
			
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(MerchantDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void genTable() throws SQLException {
		String tableQuery =
			"create table merchant\n" +
			"(\n" +
			"	id           int(6) not null auto_increment primary key,\n" +
			"	usern        varchar(32) not null,\n" +
			"	userp        varchar(64) not null,\n" +
			"	expdate      date,\n" +
			"	creditlmt    decimal(15,2) not null default 0,\n" +
			"	currdebt     decimal(15,2) not null default 0,\n" +
			"	availcredit  decimal(15,2) not null default 0,\n" +
			"	fname        varchar(64) not null,\n" +
			"	lname        varchar(64) not null,\n" +
			"	commission   decimal(5,2) not null default 5.00,\n" +
			"	profit       decimal(15,2) not null\n" +
			") engine = InnoDB;";
		
		Connection con = DBCommon.getConnection();
		Statement stmt = con.createStatement();

		stmt.executeUpdate(tableQuery);
		stmt.executeUpdate("alter table merchant auto_increment = 300000;");

		stmt.close();
		con.close();
	}
}
