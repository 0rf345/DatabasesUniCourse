/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.util.ArrayList;

public class Company extends Client {
	private String companyName;
	private ArrayList<Employee> employees;

	public String getCompanyName() {
		return companyName;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee emp) {
		this.employees.add(emp);
	}
	
	public Company() {
		super();

		this.companyName = "";
		this.employees = new ArrayList<>();
	}

	public Company(String companyName, ArrayList<Employee> employees,
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
