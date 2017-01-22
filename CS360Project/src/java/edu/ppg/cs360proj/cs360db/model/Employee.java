/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

public class Employee {
	private String employeeID;
	private String fName;
	private String lName;

	public String getEmployeeID() {
		return employeeID;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public Employee() {
		this.employeeID = "";
		this.fName = "";
		this.lName = "";
	}
	
	@Override
	public String toString() {
		return "Employee{" + "employeeID=" + employeeID + ", fName=" + fName + ", lName=" + lName + '}';
	}	
}
