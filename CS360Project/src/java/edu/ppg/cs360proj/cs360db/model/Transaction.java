/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.io.Serializable;

public class Transaction implements Serializable {
	private String trsctID;
	private String clientAccID;
	private String merchantAccID;
	private String trsctDate;
	private int amount;
	private boolean isCharge;

	public String getTrsctID() {
		return trsctID;
	}

	public String getClientAccID() {
		return clientAccID;
	}

	public String getMerchantAccID() {
		return merchantAccID;
	}

	public String getTrsctDate() {
		return trsctDate;
	}

	public int getAmount() {
		return amount;
	}

	public boolean isCharge() {
		return isCharge;
	}

	public void setTrsctID(String trsctID) {
		this.trsctID = trsctID;
	}

	public void setClientAccID(String clientAccID) {
		this.clientAccID = clientAccID;
	}

	public void setMerchantAccID(String merchantAccID) {
		this.merchantAccID = merchantAccID;
	}

	public void setTrsctDate(String trsctDate) {
		this.trsctDate = trsctDate;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setIsCharge(boolean isCharge) {
		this.isCharge = isCharge;
	}

	public Transaction() {
		this.trsctID = "";
		this.clientAccID = "";
		this.merchantAccID = "";
		this.trsctDate = "";
		this.amount = 0;
		this.isCharge = false;
	}

	public Transaction(String trsctID, String clientAccID, String merchantAccID,
			String trsctDate, int amount, boolean isCharge) {
		this.trsctID = trsctID;
		this.clientAccID = clientAccID;
		this.merchantAccID = merchantAccID;
		this.trsctDate = trsctDate;
		this.amount = amount;
		this.isCharge = isCharge;
	}

	@Override
	public String toString() {
		return "Transaction{" + "trsctID=" + trsctID + ", clientAccID=" + clientAccID + ", merchantAccID=" + merchantAccID + ", trsctDate=" + trsctDate + ", amount=" + amount + ", isCharge=" + isCharge + '}';
	}	
}
