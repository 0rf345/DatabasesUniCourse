/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import edu.ppg.cs360proj.cs360db.IndividualDB;
import edu.ppg.cs360proj.cs360db.MerchantDB;
import edu.ppg.cs360proj.cs360db.CompanyDB;
import edu.ppg.cs360proj.cs360db.model.Client;

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

import java.util.logging.Level;
import java.util.logging.Logger;

class LoginRequest {
	@SerializedName("usern")
	private String usern;
	@SerializedName("userp")
	private String userp;

	public String getUName() {
		return usern;
	}

	public String getUPass() {
		return userp;
	}
	
	@Override
	public String toString() {
		return "LoginRequest{" + "usern=" + usern + ", userp=" + userp + '}';
	}
}

class LoginResponse {
	@SerializedName("authstatus")
	private String authstatus;

	public void setAuthStatus(String authstatus) {
		this.authstatus = authstatus;
	}
	
	public LoginResponse() {
		this.authstatus = "";
	}
	
	@Override
	public String toString() {
		return "LoginResponse{" + "authstatus=" + authstatus + '}';
	}
}

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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

		LoginRequest lq = gson.fromJson(jb.toString(), LoginRequest.class);

		try (PrintWriter out = res.getWriter()) {
			Client clnt;
			HttpSession session;
			LoginResponse ls = new LoginResponse();
			ls.setAuthStatus("unauthorised");
			String strLs = "";
			
			session = req.getSession(false);
			if(session != null) {
				ls.setAuthStatus("already_authenticated");
				strLs = gson.toJson(ls);
				out.print(strLs);
				out.close();
				return;
			}
			
			clnt = IndividualDB.getIndividual(lq.getUName());
			if(clnt.getClientUName().equals(lq.getUName())) {
				if(clnt.getClientPass().equals(lq.getUPass())) {
					session = req.getSession();
					session.setAttribute("usern", lq.getUName());
					session.setAttribute("userk", "indiv");
					ls.setAuthStatus("authenticated");
					strLs = gson.toJson(ls);
					out.print(strLs);
					out.close();
					return;
					
				}
			}
			
			clnt = MerchantDB.getMerchant(lq.getUName());
			if(clnt.getClientUName().equals(lq.getUName())) {
				if(clnt.getClientPass().equals(lq.getUPass())) {
					session = req.getSession();
					session.setAttribute("usern", lq.getUName());
					session.setAttribute("userk", "merch");
					ls.setAuthStatus("authenticated");
					strLs = gson.toJson(ls);
					out.print(strLs);
					out.close();
					return;
				}
			}
			
			clnt = CompanyDB.getCompany(lq.getUName());
			if(clnt.getClientUName().equals(lq.getUName())) {
				if(clnt.getClientPass().equals(lq.getUPass())) {
					session = req.getSession();
					session.setAttribute("usern", lq.getUName());
					session.setAttribute("userk", "comp");
					ls.setAuthStatus("authenticated");
					strLs = gson.toJson(ls);
					out.print(strLs);
					out.close();
					return;
				}
			}

			strLs = gson.toJson(ls);
			out.print(strLs);
			out.close();
		} catch (IOException ex) {
			Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}
