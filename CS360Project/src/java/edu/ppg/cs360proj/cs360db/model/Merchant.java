/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360db.model;

import java.math.BigDecimal;

public class Merchant extends Individual {
	private BigDecimal commission;
	private BigDecimal profit;

	public BigDecimal getCommission() {
		return commission;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Merchant() {
		super();

		this.commission = new BigDecimal(0);
		this.profit = new BigDecimal(0);
	}

	@Override
	public String toString() {
		return "Merchant{" + "commission=" + commission + ", profit=" + profit + '}';
	}
}
