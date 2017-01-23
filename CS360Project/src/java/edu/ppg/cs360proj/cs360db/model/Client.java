/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Client implements Serializable {
	private String clientUName;
	private String clientPass;
	private String accountID;
	private String expDate;
	private BigDecimal creditLimit;
	private BigDecimal currentDebt;
	private BigDecimal availableCredit;

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

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public BigDecimal getCurrentDebt() {
		return currentDebt;
	}

	public BigDecimal getAvailableCredit() {
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

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public void setCurrentDebt(BigDecimal currentDebt) {
		this.currentDebt = currentDebt;
	}

	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}

	public Client() {
		this.clientUName = "";
		this.clientPass = "";
		this.accountID = "";
		this.expDate = null;
		this.creditLimit = new BigDecimal(0);
		this.currentDebt = new BigDecimal(0);
		this.availableCredit = new BigDecimal(0);
	}

	@Override
	public String toString() {
		return "Client{" + "clientName=" + clientUName + ", clientPass=" + clientPass + ", accountNo=" + accountID + ", expDate=" + expDate + ", creditLimit=" + creditLimit + ", currentDebt=" + currentDebt + ", availableCredit=" + availableCredit + '}';
	}
}
