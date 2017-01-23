/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import edu.ppg.cs360proj.cs360db.model.IndivTransaction;

import java.math.BigDecimal;

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
public class IndivTransactionDB {
	public static List<IndivTransaction> getTransactions() {
		List<IndivTransaction> itrs = new ArrayList<>();

		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from individual_transaction;");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			while(res.next() == true) {
				IndivTransaction itr = new IndivTransaction();
				itr.setTrsctID(res.getString("id"));
				itr.setIndivID(res.getString("indiv_id"));
				itr.setMerchID(res.getString("merch_id"));
				itr.setTrsctDate(res.getString("trsctdate"));
				itr.setAmount(BigDecimal.valueOf(res.getDouble("amount")));
				itr.setIsCharge(res.getBoolean("ischarge"));
				itrs.add(itr);
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndivTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}

		return itrs;
	}

	public static IndivTransaction getTransaction(String trsctid) {
		IndivTransaction itr = new IndivTransaction();
		
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from individual_transaction ")
					.append(" where ")
					.append(" id = ").append("'").append(trsctid).append("'")
					.append(";");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			if (res.next() == true) {
				itr.setTrsctID(res.getString("id"));
				itr.setIndivID(res.getString("indiv_id"));
				itr.setMerchID(res.getString("merch_id"));
				itr.setTrsctDate(res.getString("trsctdate"));
				itr.setAmount(BigDecimal.valueOf(res.getDouble("amount")));
				itr.setIsCharge(res.getBoolean("ischarge"));
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndivTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return itr;
	}
	
	public static void addTransaction(IndivTransaction itr) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("insert into individual_transaction ")
					.append(" ( indiv_id, merch_id, trsctdate, amount, ischarge ) ")
					.append(" VALUES (")
					.append("'").append(itr.getIndivID()).append("',")
					.append("'").append(itr.getMerchID()).append("',")
					.append("'").append(itr.getTrsctDate()).append("',")
					.append("'").append(itr.getAmount()).append("',")
					.append("'").append(itr.isCharge()).append("'")
					.append(");");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully added to the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndivTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void updateTransaction(IndivTransaction itr) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("update individual_transaction")
					.append(" set ")
					.append(" indiv_id = ").append("'").append(itr.getIndivID()).append("',")
					.append(" merch_id = ").append("'").append(itr.getMerchID()).append("',")
					.append(" trsctdate = ").append("'").append(itr.getTrsctDate()).append("',")
					.append(" amount = ").append("'").append(itr.getAmount()).append("',")
					.append(" ischarge = ").append("'").append(itr.isCharge()).append("',")
					.append(" where id = ").append("'").append(itr.getTrsctID()).append("'")
					.append(";");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully updated in the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndivTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void deleteTransaction(String trsctID) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("delete from individual_transaction")
					.append(" where ")
					.append(" usern = ").append("'").append(trsctID).append("'")
					.append(";");
			
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully deleted from the database.");
			
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(IndivTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void genTable() throws SQLException {
		String tableQuery =
			"create table individual_transaction\n" +
			"(\n" +
			"	id         int(8) not null auto_increment primary key,\n" +
			"	indiv_id   int(6),\n" +
			"	merch_id   int(6),\n" +
			"	trsctdate  date not null,\n" +
			"	amount     decimal(15,2) not null default 0,\n" +
			"	ischarge   boolean not null default false,\n" +
			"	constraint `fk_indivtrs_indiv_id`\n" +
			"		foreign key (indiv_id) references individual (id)\n" +
			"		on update cascade,\n" +
			"	constraint `fk_indivtrs_merch_id`\n" +
			"		foreign key (merch_id) references merchant (id)\n" +
			"		on update cascade\n" +
			") engine = InnoDB;";

		Connection con = DBCommon.getConnection();
		Statement stmt = con.createStatement();

		stmt.executeUpdate(tableQuery);
		stmt.executeUpdate("alter table individual_transaction auto_increment = 10000000;");
		
		stmt.close();
		con.close();
	}
}