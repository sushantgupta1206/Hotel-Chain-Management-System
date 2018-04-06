package com.dataconfig;

import com.databaserepo.BillingAccountsDAO;

public class ConfigBillingAccounts {
	public void addBill(String[] args) {
		int billId = Integer.parseInt(args[1]);
		int amt = Integer.parseInt(args[2]);
		int discountedAmt = Integer.parseInt(args[3]);
		String billingAddress =args[4];
		int dbFlag = Integer.parseInt(args[5]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.addBill(billId, amt, discountedAmt, billingAddress, dbFlag);
	}
	
	public void updateBill(String[] args) {
		int amt = Integer.parseInt(args[1]);
		int discountedAmt = Integer.parseInt(args[2]);
		String billingAddress =args[3];
		int billId = Integer.parseInt(args[4]);
		int dbFlag = Integer.parseInt(args[5]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.updateBill(amt, discountedAmt, billingAddress, billId, dbFlag);
	}

	public void deleteBill(String[] args) {
		int billId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.deleteBill(billId, dbFlag);
		
	}

	public void addPay(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int billId = Integer.parseInt(args[2]);
		int payId = Integer.parseInt(args[3]);
		int payPersonSSN = Integer.parseInt(args[4]);
		int dbFlag = Integer.parseInt(args[5]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.payBill(customerId, billId, payId, payPersonSSN, dbFlag);
	}

	public void addGenerate(String[] args) {
		int staffId = Integer.parseInt(args[1]);
		int billId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.addGenerate(staffId, billId, dbFlag);
	}

	public void checkTotalAmount(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.checkTotalAmount(customerId, dbFlag);
	}

	public void checkItemizedTotalAmount(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.checkItemizedTotalAmount(customerId, dbFlag);
	}
	
}
