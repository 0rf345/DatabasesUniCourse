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
	private String clientAccNo;
	private String merchantAccNo;
	private String trsctDate;
	private int amount;
	private boolean isCharge;

	public String getTrsctID() {
		return trsctID;
	}

	public String getClientAccNo() {
		return clientAccNo;
	}

	public String getMerchantAccNo() {
		return merchantAccNo;
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

	public void setClientAccNo(String clientAccNo) {
		this.clientAccNo = clientAccNo;
	}

	public void setMerchantAccNo(String merchantAccNo) {
		this.merchantAccNo = merchantAccNo;
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
		this.clientAccNo = "";
		this.merchantAccNo = "";
		this.trsctDate = "";
		this.amount = 0;
		this.isCharge = false;
	}

	public Transaction(String trsctID, String clientAccNo, String merchantAccNo,
			String trsctDate, int amount, boolean isCharge) {
		this.trsctID = trsctID;
		this.clientAccNo = clientAccNo;
		this.merchantAccNo = merchantAccNo;
		this.trsctDate = trsctDate;
		this.amount = amount;
		this.isCharge = isCharge;
	}

	@Override
	public String toString() {
		return "Transaction{" + "trsctID=" + trsctID + ", clientAccNo=" + clientAccNo + ", merchantAccNo=" + merchantAccNo + ", trsctDate=" + trsctDate + ", amount=" + amount + ", isCharge=" + isCharge + '}';
	}
}
