/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.io.Serializable;

public class IndivTransaction  implements Serializable {
	private String trsctID;
	private String indivID;
	private String merchID;
	private String trsctDate;
	private int amount;
	private boolean isCharge;

	public String getTrsctID() {
		return trsctID;
	}

	public String getIndivID() {
		return indivID;
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

	public void setIndivID(String indivID) {
		this.indivID = indivID;
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

	public IndivTransaction() {
		this.trsctID = "";
		this.indivID = "";
		this.merchID = "";
		this.trsctDate = "";
		this.amount = 0;
		this.isCharge = false;
	}
	
	@Override
	public String toString() {
		return "IndivTransaction{" + "trsctID=" + trsctID + ", indivID=" + indivID + ", merchID=" + merchID + ", trsctDate=" + trsctDate + ", amount=" + amount + ", isCharge=" + isCharge + '}';
	}
}
