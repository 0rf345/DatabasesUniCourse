/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ppg.cs360proj.cs360app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author looselyrigorous
 */
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
		String user = req.getParameter("usern");
		String password = req.getParameter("userp");

		final PrintWriter out = res.getWriter();
		if(user.equals("cs360") && password.equals("cs360")) {
			HttpSession session = req.getSession();
			session.setAttribute("usern", user);
			out.print("authenticated");
		} else {
			out.print("unauthorized");
		}

		out.close();
	}
}
