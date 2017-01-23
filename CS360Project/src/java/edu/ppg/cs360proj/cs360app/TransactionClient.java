/*
 * Project for UoC's CS360 Class
 * Authors:
 * Constantine Kalivas <csd3203@csd.uoc.gr>
 * Orpheus Kalipolitis <csd3285@csd.uoc.gr>
 * Olympia Ksanthaki <xanthaki@csd.uoc.gr>
 */
package edu.ppg.cs360proj.cs360app;

import edu.ppg.cs360proj.cs360db.IndivTransactionDB;
import edu.ppg.cs360proj.cs360db.CompTransactionDB;
import edu.ppg.cs360proj.cs360db.IndividualDB;
import edu.ppg.cs360proj.cs360db.CompanyDB;
import edu.ppg.cs360proj.cs360db.MerchantDB;

import edu.ppg.cs360proj.cs360db.model.Individual;
import edu.ppg.cs360proj.cs360db.model.Merchant;
import edu.ppg.cs360proj.cs360db.model.Company;
import edu.ppg.cs360proj.cs360db.model.Employee;

import edu.ppg.cs360proj.cs360db.model.IndivTransaction;
import edu.ppg.cs360proj.cs360db.model.CompTransaction;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

public class TransactionClient {
	static BigDecimal percentage(BigDecimal base, BigDecimal pct){
		return base.multiply(pct).divide(new BigDecimal(100));
	}
	
	public static boolean individualBuy(Individual indiv, Merchant merch, BigDecimal amount) {
		if(indiv.getAvailableCredit().compareTo(amount) >= 0) {
			IndivTransaction trsct = new IndivTransaction();
			trsct.setIndivID(indiv.getAccountID());
			trsct.setMerchID(merch.getAccountID());
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			DateFormat dateFrmt = new SimpleDateFormat("YYYY-MM-DD");
			String strDate = dateFrmt.format(date);
			trsct.setTrsctDate(strDate);
			trsct.setAmount(amount);
			trsct.setIsCharge(true);
			
			indiv.setAvailableCredit(indiv.getAvailableCredit().subtract(amount));
			indiv.setCurrentDebt(indiv.getAvailableCredit().add(amount));
			
			merch.setAvailableCredit(merch.getAvailableCredit().add(amount));
			merch.setCurrentDebt(merch.getCurrentDebt().add(percentage(amount,merch.getCommission())));
			
			IndividualDB.updateIndividual(indiv);
			MerchantDB.updateMerchant(merch);
			IndivTransactionDB.addTransaction(trsct);
				
			return true;
		}
		
		return false;
	}
	
	public static boolean companyBuy(Company comp, Employee emp, Merchant merch, BigDecimal amount) {
		if(comp.getAvailableCredit().compareTo(amount) >= 0) {
			CompTransaction trsct = new CompTransaction();
			trsct.setCompID(comp.getAccountID());
			trsct.setMerchID(merch.getAccountID());
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			DateFormat dateFrmt = new SimpleDateFormat("YYYY-MM-DD");
			String strDate = dateFrmt.format(date);
			trsct.setTrsctDate(strDate);
			trsct.setAmount(amount);
			trsct.setIsCharge(true);
			
			comp.setAvailableCredit(comp.getAvailableCredit().subtract(amount));
			comp.setCurrentDebt(comp.getAvailableCredit().add(amount));
			
			merch.setAvailableCredit(merch.getAvailableCredit().add(amount));
			merch.setCurrentDebt(merch.getCurrentDebt().add(percentage(amount,merch.getCommission())));
			
			CompanyDB.updateCompany(comp);
			MerchantDB.updateMerchant(merch);
			CompTransactionDB.addTransaction(trsct);
				
			return true;
		}
		
		return false;
	}
	
	public static boolean individualReturn(Individual indiv, IndivTransaction itrs) {
		Merchant merch = MerchantDB.getMerchant(itrs.getMerchID());
		if(merch.getAvailableCredit().compareTo(itrs.getAmount()) >= 0) {
			itrs.setIsCharge(false);
			
			indiv.setAvailableCredit(indiv.getAvailableCredit().add(itrs.getAmount()));
			indiv.setCurrentDebt(indiv.getAvailableCredit().subtract(itrs.getAmount()));
			
			merch.setAvailableCredit(merch.getAvailableCredit().subtract(itrs.getAmount()));
			merch.setCurrentDebt(merch.getCurrentDebt().subtract(percentage(itrs.getAmount(),merch.getCommission())));
			
			IndividualDB.updateIndividual(indiv);
			MerchantDB.updateMerchant(merch);
			IndivTransactionDB.updateTransaction(itrs);
			return true;
		}
		return false;
	}
	
	public static boolean companyReturn(Company comp, Employee emp, CompTransaction ctrs) {
		Merchant merch = MerchantDB.getMerchant(ctrs.getMerchID());
		if(merch.getAvailableCredit().compareTo(ctrs.getAmount()) >= 0) {
			ctrs.setIsCharge(false);
			
			comp.setAvailableCredit(comp.getAvailableCredit().add(ctrs.getAmount()));
			comp.setCurrentDebt(comp.getAvailableCredit().subtract(ctrs.getAmount()));
			
			merch.setAvailableCredit(merch.getAvailableCredit().subtract(ctrs.getAmount()));
			merch.setCurrentDebt(merch.getCurrentDebt().subtract(percentage(ctrs.getAmount(),merch.getCommission())));
			
			CompanyDB.updateCompany(comp);
			MerchantDB.updateMerchant(merch);
			CompTransactionDB.updateTransaction(ctrs);
			return true;
		}
		return false;
	}
}
