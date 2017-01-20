/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

public class Individual extends Client {
	private String fName;
	private String lName;

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public void setfName(String fname) {
		this.fName = fname;
	}

	public void setlName(String lname) {
		this.lName = lname;
	}

	public Individual() {
		super();

		this.fName = "";
		this.lName = "";
	}

	public Individual(String fname, String lname, String clientName, String clientPass,
			String accountNo, String expDate, Integer creditLimit, Integer currentDebt,
			Integer availableCredit) {
		super(clientName, clientPass, accountNo, expDate, creditLimit, currentDebt, availableCredit);
		this.fName = fname;
		this.lName = lname;
	}

	@Override
	public String toString() {
		return "Individual{" + "fName=" + fName + ", lName=" + lName + '}';
	}
}
