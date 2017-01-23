/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import edu.ppg.cs360proj.cs360db.model.CompTransaction;

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
public class CompTransactionDB {
	public static List<CompTransaction> getTransactions() {
		List<CompTransaction> ctrs = new ArrayList<>();

		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from company_transaction;");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			while(res.next() == true) {
				CompTransaction ctr = new CompTransaction();
				ctr.setTrsctID(res.getString("id"));
				ctr.setCompID(res.getString("comp_id"));
				ctr.setEmpID(res.getString("emp_id"));
				ctr.setMerchID(res.getString("merch_id"));
				ctr.setTrsctDate(res.getString("trsctdate"));
				ctr.setAmount(BigDecimal.valueOf(res.getDouble("amount")));
				ctr.setIsCharge(res.getBoolean("ischarge"));
				ctrs.add(ctr);
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}

		return ctrs;
	}

	public static CompTransaction getTransaction(String trsctid) {
		CompTransaction ctr = new CompTransaction();

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
				ctr.setTrsctID(res.getString("id"));
				ctr.setCompID(res.getString("comp_id"));
				ctr.setEmpID(res.getString("emp_id"));
				ctr.setMerchID(res.getString("merch_id"));
				ctr.setTrsctDate(res.getString("trsctdate"));
				ctr.setAmount(BigDecimal.valueOf(res.getDouble("amount")));
				ctr.setIsCharge(res.getBoolean("ischarge"));
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}

		return ctr;
	}

	public static void addTransaction(CompTransaction ctr) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("insert into individual_transaction ")
					.append(" ( comp_id, emp_id, merch_id, trsctdate, amount, ischarge ) ")
					.append(" VALUES (")
					.append("'").append(ctr.getCompID()).append("',")
					.append("'").append(ctr.getEmpID()).append("',")
					.append("'").append(ctr.getMerchID()).append("',")
					.append("'").append(ctr.getTrsctDate()).append("',")
					.append("'").append(ctr.getAmount()).append("',")
					.append("'").append(ctr.isCharge()).append("'")
					.append(");");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully added to the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void updateTransaction(CompTransaction ctr) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("update individual_transaction")
					.append(" set ")
					.append(" comp_id = ").append("'").append(ctr.getCompID()).append("',")
					.append(" emp_id = ").append("'").append(ctr.getEmpID()).append("',")
					.append(" merch_id = ").append("'").append(ctr.getMerchID()).append("',")
					.append(" trsctdate = ").append("'").append(ctr.getTrsctDate()).append("',")
					.append(" amount = ").append("'").append(ctr.getAmount()).append("',")
					.append(" ischarge = ").append("'").append(ctr.isCharge()).append("',")
					.append(" where id = ").append("'").append(ctr.getTrsctID()).append("'")
					.append(";");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully updated in the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void deleteTransaction(String trsctID) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("delete from individual_transaction")
					.append(" where ")
					.append(" id = ").append("'").append(trsctID).append("'")
					.append(";");

			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully deleted from the database.");

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompTransactionDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void genTable() throws SQLException {
		String tableQuery =
			"create table company_transaction\n" +
			"(\n" +
			"	id         int(8) not null auto_increment primary key,\n" +
			"	comp_id    int(6),\n" +
			"	emp_id     int(8),\n" +
			"	merch_id   int(6),\n" +
			"	trsctdate  date not null,\n" +
			"	amount     decimal(15,2) not null default 0,\n" +
			"	ischarge   boolean not null default false,\n" +
			"	constraint `fk_cmptrs_company_id`\n" +
			"		foreign key (comp_id) references company (id)\n" +
			"		on update cascade,\n" +
			"	constraint `fk_cmptrs_employee_id`\n" +
			"		foreign key (emp_id) references company_employee (id)\n" +
			"		on update cascade,\n" +
			"	constraint `fk_cmptrs_merch_id`\n" +
			"		foreign key (merch_id) references merchant (id)\n" +
			"		on update cascade\n" +
			"\n" +
			") engine = InnoDB;";

		Connection con = DBCommon.getConnection();
		Statement stmt = con.createStatement();

		stmt.executeUpdate(tableQuery);
		stmt.executeUpdate("alter table company_transaction auto_increment = 20000000;");
		
		stmt.close();
		con.close();
	}
}
