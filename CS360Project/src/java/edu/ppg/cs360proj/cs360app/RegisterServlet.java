/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import edu.ppg.cs360proj.cs360db.model.Client;
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
import java.math.BigDecimal;

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
	@SerializedName("credit")
	private String creditlmt;
	@SerializedName("first")
	private String fname;
	@SerializedName("last")
	private String lname;
	@SerializedName("name")
	private String name;
	@SerializedName("employees")
	private ArrayList<EmployeeInfo> employees;

	public String getUName() {
		return usern;
	}

	public String getUPass() {
		return userp;
	}

	public String getUKind() {
		return userk;
	}

	public String getCreditLimit() {
		return creditlmt;
	}
	
	public String getFName() {
		return fname;
	}

	public String getLName() {
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
		return "RegisterRequest{" + "usern=" + usern + ", userp=" + userp + ", userk=" + userk + ", creditlmt=" + creditlmt + ", fname=" + fname + ", lname=" + lname + ", name=" + name + ", employees=" + employees + '}';
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

class RegisterResponse {
	@SerializedName("regstatus")
	private String regstatus;
	@SerializedName("reason")
	private String reason;

	public void setRegStatus(String regstatus) {
		this.regstatus = regstatus;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "RegisterResponse{" + "regstatus=" + regstatus + ", reason=" + reason + '}';
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
		res.setContentType("application/json;charset=UTF-8");
		Gson gson = new Gson();
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try(BufferedReader reader = req.getReader()) {
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (IOException ex) {
			Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		RegisterRequest rq = gson.fromJson(jb.toString(), RegisterRequest.class);

		try (PrintWriter out = res.getWriter()) {
			Client clnt;
			RegisterResponse rs = new RegisterResponse();
			String strRs = "";
		
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 3);
			Date expDate = cal.getTime();
			DateFormat dateFrmt = new SimpleDateFormat("YYYY-MM-DD");
			String strExpDate = dateFrmt.format(expDate);
			DBCommon.validateDB();
			
			
			clnt = IndividualDB.getIndividual(rq.getUName());
			if(clnt.getClientUName().equals(rq.getUName())) {
				rs.setRegStatus("failure");
				rs.setReason("user_exists");

				strRs = gson.toJson(rs);
				out.print(strRs);
				out.close();
				return;
			}
			
			clnt = MerchantDB.getMerchant(rq.getUName());
			if(clnt.getClientUName().equals(rq.getUName())) {
				rs.setRegStatus("failure");
				rs.setReason("user_exists");

				strRs = gson.toJson(rs);
				out.print(strRs);
				out.close();
				return;
			}
			
			clnt = CompanyDB.getCompany(rq.getUName());
			if(clnt.getClientUName().equals(rq.getUName())) {
				rs.setRegStatus("failure");
				rs.setReason("user_exists");

				strRs = gson.toJson(rs);
				out.print(strRs);
				out.close();
				return;
			}
			
			if(rq.getUKind().equals("individual")) {
				Individual indiv = new Individual();
				indiv.setClientUName(rq.getUName());
				indiv.setClientPass(rq.getUPass());
				indiv.setExpDate(strExpDate);
				indiv.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(rq.getCreditLimit())));
				indiv.setAvailableCredit(BigDecimal.valueOf(Double.parseDouble(rq.getCreditLimit())));
				indiv.setfName(rq.getFName());
				indiv.setlName(rq.getLName());
				IndividualDB.addIndividual(indiv);
			} else if(rq.getUKind().equals("merchant")) {
				Merchant merch = new Merchant();
				merch.setClientUName(rq.getUName());
				merch.setClientPass(rq.getUPass());
				merch.setExpDate(strExpDate);
				merch.setfName(rq.getFName());
				merch.setlName(rq.getLName());
				MerchantDB.addMerchant(merch);
			} else {
				Company cmp = new Company();
				cmp.setClientUName(rq.getUName());
				cmp.setClientPass(rq.getUPass());
				cmp.setExpDate(strExpDate);
				cmp.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(rq.getCreditLimit())));
				cmp.setAvailableCredit(BigDecimal.valueOf(Double.parseDouble(rq.getCreditLimit())));
				cmp.setCompanyName(rq.getName());
				for(EmployeeInfo empinfo : rq.getEmployees()) {
					Employee emp = new Employee();
					emp.setfName(empinfo.getfName());
					emp.setlName(empinfo.getlName());
					cmp.addEmployee(emp);
				}
				CompanyDB.addCompany(cmp);
			}

			rs.setRegStatus("success");
			rs.setReason("");

			strRs = gson.toJson(rs);
			out.print(strRs);
			out.close();
		}
	}
}
