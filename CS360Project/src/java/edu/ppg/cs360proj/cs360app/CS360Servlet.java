/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import edu.ppg.cs360proj.cs360db.CompanyDB;
import edu.ppg.cs360proj.cs360db.model.Company;
import edu.ppg.cs360proj.cs360db.model.Employee;
import edu.ppg.cs360proj.cs360db.MerchantDB;
import edu.ppg.cs360proj.cs360db.model.Merchant;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

class CS360Request {
	@SerializedName("action")
	private String action;

	public String getAction() {
		return action;
	}

	@Override
	public String toString() {
		return "CS360Request{" + "action=" + action + '}';
	}
}

class CS360Response {
	@SerializedName("status")
	private String status;
	@SerializedName("reason")
	private String reason;

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "CS360Response{" + "status=" + status + ", reason=" + reason + '}';
	}
}

class GetMerchantsResponse extends CS360Response {
	@SerializedName("merchants")
	ArrayList<MerchantInfo> merchants;
	
	public void addMerchant(MerchantInfo merch_info) {
		merchants.add(merch_info);
	}
	
	public GetMerchantsResponse() {
		this.merchants = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "GetMerchantsResponse{" + "merchants=" + merchants + '}';
	}
}

class MerchantInfo {
	@SerializedName("id")
	private String id;
	@SerializedName("fname")
	private String fname;
	@SerializedName("lname")
	private String lname;

	public void setID(String id) {
		this.id = id;
	}

	public void setfName(String fname) {
		this.fname = fname;
	}

	public void setlName(String lname) {
		this.lname = lname;
	}
	
	@Override
	public String toString() {
		return "MerchantInfo{" + "id=" + id + ", fname=" + fname + ", lname=" + lname + '}';
	}
}

class GetEmployeesResponse extends CS360Response {
	@SerializedName("employees")
	ArrayList<EmployeeInfo> employees;

	public void addEmployee(EmployeeInfo emp_info) {
		employees.add(emp_info);
	}
	
	public GetEmployeesResponse() {
		this.employees = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "GetEmployeesResponse{" + "employees=" + employees + '}';
	}
}

class GetUserKindResponse extends CS360Response {
	@SerializedName("client")
	private String ukind;

	public void setUKind(String ukind) {
		this.ukind = ukind;
	}

	public GetUserKindResponse() {
		this.ukind = "";
	}
	
	@Override
	public String toString() {
		return "getUserKindResponse{" + "ukind=" + ukind + '}';
	}
}

@WebServlet(name = "cs360Servlet", urlPatterns = {"/cs360"})
public class CS360Servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
	HttpSession session = req.getSession(false);
	if(session != null)
		res.sendRedirect("/index.html");
	else
		res.sendRedirect("/login.html");
	}

	@Override
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
			Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
		}

		CS360Request csq = gson.fromJson(jb.toString(), CS360Request.class);

		try (PrintWriter out = res.getWriter()) {
			HttpSession session;
			CS360Response css = new CS360Response();
			String strCSs = "";
			
			session = req.getSession(false);
			if(session == null) {
				css.setStatus("failure");
				css.setReason("unauthorized");
				strCSs = gson.toJson(css);
				out.print(strCSs);
				out.close();
				return;
			}

			if(csq.getAction().equals("getmerchants")) {
				GetMerchantsResponse gmrs = new GetMerchantsResponse();
				String strGMRs = "";
				
				for(Merchant merch : MerchantDB.getMerchants()) {
					MerchantInfo merch_info = new MerchantInfo();
					merch_info.setID(merch.getAccountID());
					merch_info.setfName(merch.getfName());
					merch_info.setlName(merch.getlName());
					gmrs.addMerchant(merch_info);
				}
				
				gmrs.setStatus("success");
				css.setReason("");
				strGMRs = gson.toJson(gmrs, GetMerchantsResponse.class);
				out.print(strGMRs);
			} else if(csq.getAction().equals("getemployees")) {
				String usern = (String) session.getAttribute("usern");
				String userkind = (String) session.getAttribute("userk");
				if(!userkind.equals("company")) {
					css.setStatus("failure");
					css.setReason("not_company");
					strCSs = gson.toJson(css);
					out.print(strCSs);
					out.close();
					return;
				}
				
				GetEmployeesResponse gers = new GetEmployeesResponse();
				String strGERs = "";

				for(Employee emp : CompanyDB.getCompany(usern).getEmployees()) {
					EmployeeInfo emp_info = new EmployeeInfo();
					emp_info.setID(emp.getEmployeeID());
					emp_info.setfName(emp.getfName());
					emp_info.setlName(emp.getlName());
					gers.addEmployee(emp_info);
				}
				
				gers.setStatus("success");
				gers.setReason("");
				strGERs = gson.toJson(gers, GetEmployeesResponse.class);
				out.print(strGERs);
			} else if(csq.getAction().equals("userkind")) {
				GetUserKindResponse guks = new GetUserKindResponse();
				String strGUKs = "";
				
				guks.setUKind((String) session.getAttribute("userk"));
				
				guks.setStatus("success");
				css.setReason("");
				strGUKs = gson.toJson(guks, GetUserKindResponse.class);
				out.print(strGUKs);
			}
			
			out.close();
		} catch (IOException ex) {
			Logger.getLogger(CS360Servlet.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}