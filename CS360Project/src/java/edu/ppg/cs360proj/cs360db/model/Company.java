/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.util.HashMap;

public class Company extends Client {
	private String companyName;
	private HashMap <Integer,String> employees;

	public String getCompanyName() {
		return companyName;
	}

	public HashMap<Integer, String> getEmployees() {
		return employees;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmployees(HashMap<Integer, String> employees) {
		this.employees = employees;
	}

	public Company() {
		super();

		this.companyName = "";
		this.employees = null;
	}

	public Company(String companyName, HashMap<Integer, String> employees,
			String clientName, String clientPass, String accountID, String expDate,
			Integer creditLimit, Integer currentDebt, Integer availableCredit) {
		super(clientName, clientPass, accountID, expDate, creditLimit, currentDebt, availableCredit);
		this.companyName = companyName;
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Company{" + "companyName=" + companyName + ", employees=" + employees + '}';
	}
}
