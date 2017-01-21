/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

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

	public String getUserName() {
		return usern;
	}

	public String getUserPass() {
		return userp;
	}

	public void setUserName(String usern) {
		this.usern = usern;
	}

	public void setUserPass(String userp) {
		this.userp = userp;
	}

	public LoginRequest(String usern, String userp) {
		this.usern = usern;
		this.userp = userp;
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
		res.setContentType("text/html;charset=UTF-8");
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (IOException ex) {
			Logger.getLogger("cs360app.LoginServlet").log(Level.SEVERE, null, ex);
		}

		Gson gson = new Gson();
		LoginRequest lq = gson.fromJson(jb.toString(), LoginRequest.class);

		final PrintWriter out = res.getWriter();
		if(lq.getUserName().equals("cs360") && lq.getUserPass().equals("cs360")) {
			HttpSession session = req.getSession();
			session.setAttribute("usern", lq.getUserName());
			out.print("authenticated");
		} else {
			out.print("unauthorized");
		}

		out.close();
	}
}
