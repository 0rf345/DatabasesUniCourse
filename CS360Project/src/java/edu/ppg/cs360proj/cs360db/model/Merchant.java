/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

public class Merchant extends Individual {
	private Integer commission;
	private Integer profit;

	public Integer getCommission() {
		return commission;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public Merchant() {
		super();

		this.commission = 0;
		this.profit = 0;
	}

	@Override
	public String toString() {
		return "Merchant{" + "commission=" + commission + ", profit=" + profit + '}';
	}
}
