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
	private HashMap <String,Employee> employees;

	public String getCompanyName() {
		return companyName;
	}

	public HashMap<String, Employee> getEmployees() {
		return employees;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmployees(HashMap<String, Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee emp) {
		this.employees.put(emp.getEmployeeID(), emp);
	}
	
	public Company() {
		super();

		this.companyName = "";
		this.employees = new HashMap<>();
	}

	public Company(String companyName, HashMap<String, Employee> employees,
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
