/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.io.Serializable;

public class Client implements Serializable {
	private String clientUName;
	private String clientPass;
	private String accountID;
	private String expDate;
	private Integer creditLimit;
	private Integer currentDebt;
	private Integer availableCredit;

	public String getClientUName() {
		return clientUName;
	}

	public String getClientPass() {
		return clientPass;
	}

	public String getAccountID() {
		return accountID;
	}

	public String getExpDate() {
		return expDate;
	}

	public Integer getCreditLimit() {
		return creditLimit;
	}

	public Integer getCurrentDebt() {
		return currentDebt;
	}

	public Integer getAvailableCredit() {
		return availableCredit;
	}

	public void setClientUName(String clientUName) {
		this.clientUName = clientUName;
	}

	public void setClientPass(String clientPass) {
		this.clientPass = clientPass;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public void setCreditLimit(Integer creditLimit) {
		this.creditLimit = creditLimit;
	}

	public void setCurrentDebt(Integer currentDebt) {
		this.currentDebt = currentDebt;
	}

	public void setAvailableCredit(Integer availableCredit) {
		this.availableCredit = availableCredit;
	}

	public Client() {
		this.clientUName = "";
		this.clientPass = "";
		this.accountID = "";
		this.expDate = null;
		this.creditLimit = 0;
		this.currentDebt = 0;
		this.availableCredit = 0;
	}

	public Client(String clientName, String clientPass, String accountNo, String expDate, Integer creditLimit, Integer currentDebt, Integer availableCredit) {
		this.clientUName = clientName;
		this.clientPass = clientPass;
		this.accountID = accountNo;
		this.expDate = expDate;
		this.creditLimit = creditLimit;
		this.currentDebt = currentDebt;
		this.availableCredit = availableCredit;
	}

	@Override
	public String toString() {
		return "Client{" + "clientName=" + clientUName + ", clientPass=" + clientPass + ", accountNo=" + accountID + ", expDate=" + expDate + ", creditLimit=" + creditLimit + ", currentDebt=" + currentDebt + ", availableCredit=" + availableCredit + '}';
	}
}
