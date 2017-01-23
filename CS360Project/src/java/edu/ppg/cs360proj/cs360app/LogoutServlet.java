/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

/**
 *
 * @author Constantine Kalivas
 */
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
import edu.ppg.cs360proj.cs360db.CompanyDB;
import edu.ppg.cs360proj.cs360db.IndividualDB;
import edu.ppg.cs360proj.cs360db.MerchantDB;
import edu.ppg.cs360proj.cs360db.model.Client;

import java.util.logging.Level;
import java.util.logging.Logger;

class LogoutRequest {
	@SerializedName("action")
	private String action;

	public String getAction() {
		return action;
	}

	@Override
	public String toString() {
		return "LogoutRequest{" + "action=" + action + '}';
	}
}

class LogoutResponse {
	@SerializedName("logoutstatus")
	private String logoutstatus;

	public void setLogoutStatus(String logoutstatus) {
		this.logoutstatus = logoutstatus;
	}

	@Override
	public String toString() {
		return "LogoutResponse{" + "logoutstatus=" + logoutstatus + '}';
	}
}

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

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

		LogoutRequest lq = gson.fromJson(jb.toString(), LogoutRequest.class);

		try (PrintWriter out = res.getWriter()) {
			HttpSession session;
			LogoutResponse ls = new LogoutResponse();
			ls.setLogoutStatus("failure");
			String strLs = "";
			
			if(lq.getAction().equals("logout")) {
				session = req.getSession(false);
				if(session != null) {
					session.invalidate();
					ls.setLogoutStatus("success");
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
