/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import edu.ppg.cs360proj.cs360db.model.Individual;
import edu.ppg.cs360proj.cs360db.model.Merchant;
import edu.ppg.cs360proj.cs360db.model.Company;
import edu.ppg.cs360proj.cs360db.model.Employee;
import edu.ppg.cs360proj.cs360db.IndividualDB;
import edu.ppg.cs360proj.cs360db.MerchantDB;
import edu.ppg.cs360proj.cs360db.CompanyDB;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import edu.ppg.cs360proj.cs360db.DBCommon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

class RegisterRequest {
	@SerializedName("usern")
	private String usern;
	@SerializedName("userp")
	private String userp;
	@SerializedName("client")
	private String userk;
	@SerializedName("first")
	private String fname;
	@SerializedName("last")
	private String lname;
	@SerializedName("name")
	private String name;
	@SerializedName("employees")
	private ArrayList<EmployeeInfo> employees;

	public String getUsern() {
		return usern;
	}

	public String getUserp() {
		return userp;
	}

	public String getUserk() {
		return userk;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getName() {
		return name;
	}

	public ArrayList<EmployeeInfo> getEmployees() {
		return employees;
	}
	
	@Override
	public String toString() {
		return "RegisterRequest{" + "usern=" + usern + ", userp=" + userp + ", userk=" + userk + ", fname=" + fname + ", lname=" + lname + ", name=" + name + ", employees=" + employees + '}';
	}
}

class EmployeeInfo {
	@SerializedName("first")
	private String fname;
	@SerializedName("last")
	private String lname;

	public String getfName() {
		return fname;
	}

	public String getlName() {
		return lname;
	}
	
	@Override
	public String toString() {
		return "EmployeeInfo{" + "fname=" + fname + ", lname=" + lname + '}';
	}
}

@WebServlet(name="RegisterServlet", displayName="Register", urlPatterns="/register")
public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session != null)
			res.sendRedirect("/index.html");
		else
			res.sendRedirect("/register.html");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		final PrintWriter out = res.getWriter();

		res.setContentType("text/html");
	
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (IOException ex) {
			Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
		}

		Gson gson = new Gson();
		RegisterRequest rr = gson.fromJson(jb.toString(), RegisterRequest.class);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 3); // to get previous year add -1
		Date expDate = cal.getTime();
		DateFormat dateFrmt = new SimpleDateFormat("YYYY-MM-DD");
		String strExpDate = dateFrmt.format(expDate);
		
		DBCommon.validateDB();
		
		if(rr.getUserk().equals("individual")) {
			Individual indiv = new Individual();
			indiv.setClientUName(rr.getUsern());
			indiv.setClientPass(rr.getUserp());
			indiv.setExpDate(strExpDate);
			indiv.setCreditLimit(0);
			indiv.setCurrentDebt(0);
			indiv.setAvailableCredit(0);
			indiv.setfName(rr.getFname());
			indiv.setlName(rr.getLname());
			IndividualDB.addIndividual(indiv);
		} else if(rr.getUserk().equals("merchant")) {
			Merchant merch = new Merchant();
			merch.setClientUName(rr.getUsern());
			merch.setClientPass(rr.getUserp());
			merch.setExpDate(strExpDate);
			merch.setCreditLimit(0);
			merch.setCurrentDebt(0);
			merch.setAvailableCredit(0);
			merch.setfName(rr.getFname());
			merch.setlName(rr.getLname());
			merch.setCommission(0);
			merch.setProfit(0);
			MerchantDB.addMerchant(merch);
		} else {
			Company cmp = new Company();
			cmp.setClientUName(rr.getUsern());
			cmp.setClientPass(rr.getUserp());
			cmp.setExpDate(strExpDate);
			cmp.setCreditLimit(0);
			cmp.setCurrentDebt(0);
			cmp.setAvailableCredit(0);
			cmp.setCompanyName(rr.getName());
			for(EmployeeInfo empinfo : rr.getEmployees()) {
				Employee emp = new Employee();
				emp.setfName(empinfo.getfName());
				emp.setlName(empinfo.getlName());
				cmp.addEmployee(emp);
			}
			CompanyDB.addCompany(cmp);
		}
		
		out.close();
	}
}
