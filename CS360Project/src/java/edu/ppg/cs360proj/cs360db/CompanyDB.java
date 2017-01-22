/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db;

import edu.ppg.cs360proj.cs360db.model.Company;
import edu.ppg.cs360proj.cs360db.model.Employee;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyDB extends ClientDB {
	public static List<Company> getCompanies() {
		List<Company> cmps = new ArrayList<>();

		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from company;");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			while(res.next() == true) {
				Company cmp = new Company();
				cmp.setAccountID(res.getString("accid"));
				cmp.setClientUName(res.getString("usern"));
				cmp.setClientPass(res.getString("userp"));
				cmp.setExpDate(res.getString("expdate"));
				cmp.setCreditLimit(res.getInt("creditlmt"));
				cmp.setCurrentDebt(res.getInt("currdebt"));
				cmp.setAvailableCredit(res.getInt("availcredit"));
				cmp.setCompanyName(res.getString("companyname"));
				
				insQuery.setLength(0);
				insQuery.append("select * from company_employee")
						.append(" where ")
						.append(" company_accid = '").append(cmp.getAccountID()).append("'");
				res = stmt.getResultSet();
				while(res.next() == true) {
					Employee emp = new Employee();
					emp.setEmployeeID(res.getString("empid"));
					emp.setfName(res.getString("fname"));
					emp.setlName(res.getString("lname"));
					cmp.addEmployee(emp);
				}
				cmps.add(cmp);
			}

			stmt.close();
			con.close();

		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
		}

		return cmps;
	}

	public static Company getCompany(String clientUName) {
		Company cmp = new Company();
		
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("select * from Company ")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");
			stmt.execute(insQuery.toString());

			ResultSet res = stmt.getResultSet();
			if (res.next() == true) {
				cmp.setAccountID(res.getString("accid"));
				cmp.setClientUName(res.getString("usern"));
				cmp.setClientPass(res.getString("userp"));
				cmp.setExpDate(res.getString("expdate"));
				cmp.setCreditLimit(res.getInt("creditlmt"));
				cmp.setCurrentDebt(res.getInt("currdebt"));
				cmp.setAvailableCredit(res.getInt("availcredit"));
				cmp.setCompanyName(res.getString("companyname"));
				
				insQuery.setLength(0);
				insQuery.append("select * from company_employee")
						.append(" where ")
						.append(" company_accid = '").append(cmp.getAccountID()).append("'");
				res = stmt.getResultSet();
				while(res.next() == true) {
					Employee emp = new Employee();
					emp.setEmployeeID(res.getString("empid"));
					emp.setfName(res.getString("fname"));
					emp.setlName(res.getString("lname"));
					cmp.addEmployee(emp);
				}
			}

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return cmp;
	}
	
	public static void addCompany(Company cmp) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();
			ResultSet res;

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("insert into company")
					.append(" ( usern, userp, expdate, creditlmt, currdebt, availcredit, companyname ) ")
					.append(" VALUES (")
					.append("'").append(cmp.getClientUName()).append("'")
					.append("'").append(cmp.getClientPass()).append("'")
					.append("'").append(cmp.getExpDate()).append("'")
					.append("'").append(cmp.getCreditLimit()).append("'")
					.append("'").append(cmp.getCurrentDebt()).append("'")
					.append("'").append(cmp.getAvailableCredit()).append("'")
					.append("'").append(cmp.getCompanyName()).append("'")
					.append(");");
			stmt.executeUpdate(insQuery.toString());

			insQuery.setLength(0);
			insQuery.append("select accid from Company ")
					.append(" where ")
					.append(" usern = ").append("'").append(cmp.getClientUName()).append("'")
					.append(";");
			stmt.execute(insQuery.toString());
			res = stmt.getResultSet();
			cmp.setAccountID(res.getString("accid"));
			
			for(Employee emp : cmp.getEmployees()){
				insQuery.setLength(0);
				insQuery.append("insert into company_employee")
						.append(" ( company_accid, fname, lname ) ")
						.append(" VALUES (")
						.append("'").append(cmp.getAccountID()).append("'")
						.append("'").append(emp.getfName()).append("'")
						.append("'").append(emp.getlName()).append("'")
						.append(");");
				stmt.execute(insQuery.toString());
			}
			
			System.out.println("#DB: The member was successfully added to the database.");
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void updateCompany(Company cmp) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("update company")
					.append(" set ")
					.append(" userp = ").append("'").append(cmp.getClientPass()).append("'")
					.append(" expdate = ").append("'").append(cmp.getExpDate()).append("'")
					.append(" creditlmt = ").append("'").append(cmp.getCreditLimit()).append("'")
					.append(" currdebt = ").append("'").append(cmp.getCurrentDebt()).append("'")
					.append(" availcredit = ").append("'").append(cmp.getAvailableCredit()).append("'")
					.append(" companyname = ").append("'").append(cmp.getCompanyName()).append("'")
					.append(" where usern = ").append("'").append(cmp.getClientUName()).append("'")
					.append(";");
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully updated in the database.");
			// TODO: Add relevant code for updating employee tables
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void deleteCompany(String clientUName) {
		try {
			Connection con = DBCommon.getConnection();
			Statement stmt = con.createStatement();

			StringBuilder insQuery = new StringBuilder();
			insQuery.append("delete from company")
					.append(" where ")
					.append(" usern = ").append("'").append(clientUName).append("'")
					.append(";");
			
			stmt.executeUpdate(insQuery.toString());
			System.out.println("#DB: The member was successfully deleted from the database.");
			
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			// Log exception
			Logger.getLogger(CompanyDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void genTables() throws SQLException {
		String companyTableQuery =
			"create table company\n" +
			"(\n" +
			"	id           int(6) not null auto_increment primary key,\n" +
			"	usern        varchar(32) not null,\n" +
			"	userp        varchar(64) not null,\n" +
			"	expdate      date,\n" +
			"	creditlmt    decimal(15,2) not null default 0,\n" +
			"	currdebt     decimal(15,2) not null default 0,\n" +
			"	availcredit  decimal(15,2) not null default 0,\n" +
			"	companyname  varchar(64) not null\n" +
			") engine = InnoDB;";
		
		String companyEmployeeTableQuery =
			"create table company_employee\n" +
			"(\n" +
			"	id          int(8) not null auto_increment primary key,\n" +
			"	company_id  int(6) not null,\n" +
			"	fname       varchar(64) not null,\n" +
			"	lname       varchar(64) not null,\n" +
			"	constraint `fk_cmpemp_company_id`\n" +
			"		foreign key (company_id) references company (id)\n" +
			"		on update cascade\n" +
			"		on delete cascade\n" +
			") engine = InnoDB;";
				
		Connection con = DBCommon.getConnection();
		Statement stmt = con.createStatement();

		stmt.executeUpdate(companyTableQuery);
		stmt.executeUpdate("alter table company auto_increment = 200000;");
		stmt.executeUpdate(companyEmployeeTableQuery);

		stmt.close();
		con.close();
	}
}
