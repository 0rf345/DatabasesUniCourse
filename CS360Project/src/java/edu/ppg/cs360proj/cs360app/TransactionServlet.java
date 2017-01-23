/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import edu.ppg.cs360proj.cs360db.IndivTransactionDB;
import edu.ppg.cs360proj.cs360db.CompTransactionDB;
import edu.ppg.cs360proj.cs360db.IndividualDB;
import edu.ppg.cs360proj.cs360db.CompanyDB;
import edu.ppg.cs360proj.cs360db.MerchantDB;

import edu.ppg.cs360proj.cs360db.model.Individual;
import edu.ppg.cs360proj.cs360db.model.Merchant;
import edu.ppg.cs360proj.cs360db.model.Company;
import edu.ppg.cs360proj.cs360db.model.Employee;

import edu.ppg.cs360proj.cs360db.model.IndivTransaction;
import edu.ppg.cs360proj.cs360db.model.CompTransaction;

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
import java.math.BigDecimal;

import java.util.logging.Level;
import java.util.logging.Logger;

class TransactionRequest {
	@SerializedName("action")
	private String action;
	@SerializedName("merch_id")
	private String merchid;
	@SerializedName("amount")
	private String amount;

	public String getAction() {
		return action;
	}

	public String getMerchID() {
		return merchid;
	}

	public String getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "TransactionRequest{" + "action=" + action + ", merchid=" + merchid + ", amount=" + amount + '}';
	}
	
}

class CompTransactionRequest extends TransactionRequest {
	@SerializedName("emp_id")
	private String emp_id;

	public String getEmpID() {
		return emp_id;
	}

	@Override
	public String toString() {
		return "CompTransactionRequest{" + "emp_id=" + emp_id + '}';
	}
}

class TransactionResponse {
	@SerializedName("status")
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}
	
	public TransactionResponse() {
		this.status = "";
	}
	
	@Override
	public String toString() {
		return "TransactionResponse{" + "status=" + status + '}';
	}
}

@WebServlet(name = "TransactionServlet", urlPatterns = {"/transaction"})
public class TransactionServlet extends HttpServlet {

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
			Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
		}

		try (PrintWriter out = res.getWriter()) {
			HttpSession session;
			TransactionResponse trs = new TransactionResponse();
			String strTRs = "";
			
			session = req.getSession(false);
			if(session == null) {
				trs.setStatus("unauthorized");
				strTRs = gson.toJson(trs);
				out.print(strTRs);
				out.close();
				return;
			}
			
			boolean ret;
			String usern = (String) session.getAttribute("usern");
			String userkind = (String) session.getAttribute("userk");
			if(userkind.equals("individual")) {
				TransactionRequest trq = gson.fromJson(jb.toString(), TransactionRequest.class);
				Individual indiv = IndividualDB.getIndividual(usern);
				Merchant merch = MerchantDB.getMerchantByID(trq.getMerchID());
				
				if(trq.getAction().equals("buy")) {
					ret = TransactionClient.individualBuy(indiv, merch, BigDecimal.valueOf(Double.parseDouble(trq.getAmount())));
					if(ret) {
						trs.setStatus("success");
						strTRs = gson.toJson(trs);
						out.print(strTRs);
						out.close();
						return;
					}
				} else if(trq.getAction().equals("return")) {
					
				}
				
			} if(userkind.equals("company")) {
				CompTransactionRequest ctrq = gson.fromJson(jb.toString(), CompTransactionRequest.class);
				Company comp = CompanyDB.getCompany(usern);
				Merchant merch = MerchantDB.getMerchantByID(ctrq.getMerchID());
				Employee emp = comp.getEmployee(ctrq.getEmpID());
				
				if(ctrq.getAction().equals("buy")) {
					ret = TransactionClient.companyBuy(comp, emp, merch, BigDecimal.valueOf(Double.parseDouble(ctrq.getAmount())));
					if(ret) {
						trs.setStatus("success");
						strTRs = gson.toJson(trs);
						out.print(strTRs);
						out.close();
						return;
					}
				} else if(ctrq.getAction().equals("return")) {
					
				}
			}
			
			trs.setStatus("failure");
			strTRs = gson.toJson(trs);
			out.print(strTRs);
			out.close();
		} catch (IOException ex) {
			Logger.getLogger(TransactionServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
