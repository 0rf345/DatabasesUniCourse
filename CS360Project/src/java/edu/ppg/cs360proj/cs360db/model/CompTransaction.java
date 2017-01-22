/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.io.Serializable;

/**
 *
 * @author Constantine Kalivas
 */
public class CompTransaction implements Serializable {
	private String trsctID;
	private String compID;
	private String empID;
	private String merchID;
	private String trsctDate;
	private int amount;
	private boolean isCharge;

	public String getTrsctID() {
		return trsctID;
	}

	public String getCompID() {
		return compID;
	}

	public String getEmpID() {
		return empID;
	}

	public String getMerchID() {
		return merchID;
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

	public void setCompID(String compID) {
		this.compID = compID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public void setMerchID(String merchID) {
		this.merchID = merchID;
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

	public CompTransaction() {
		this.trsctID = "";
		this.compID = "";
		this.empID = "";
		this.merchID = "";
		this.trsctDate = "";
		this.amount = 0;
		this.isCharge = false;
	}

	@Override
	public String toString() {
		return "CompTransaction{" + "trsctID=" + trsctID + ", compID=" + compID + ", empID=" + empID + ", merchID=" + merchID + ", trsctDate=" + trsctDate + ", amount=" + amount + ", isCharge=" + isCharge + '}';
	}

	
}
