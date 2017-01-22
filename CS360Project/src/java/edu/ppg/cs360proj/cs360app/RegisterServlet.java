/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

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

	public RegisterRequest(String usern, String userp, String userk, String fname, String lname) {
		this.usern = usern;
		this.userp = userp;
		this.userk = userk;
		this.fname = fname;
		this.lname = lname;
	}

	public RegisterRequest(String usern, String userp, String userk, String name) {
		this.usern = usern;
		this.userp = userp;
		this.userk = userk;
		this.name = name;
	}

	@Override
	public String toString() {
		return "RegisterRequest{" + "usern=" + usern + ", userp=" + userp + ", userk=" + userk + ", fname=" + fname + ", lname=" + lname + ", name=" + name + '}';
	}
}

class EmployeeInfo {
	private String fname;
	private String lname;
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
			Logger.getLogger("cs360app.LoginServlet").log(Level.SEVERE, null, ex);
		}

		Gson gson = new Gson();
		RegisterRequest rr = gson.fromJson(jb.toString(), RegisterRequest.class);
	
		System.err.print(rr.toString());
		
		out.close();
	}
}
