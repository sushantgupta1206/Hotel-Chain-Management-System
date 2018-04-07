package com.dataconfig;

import com.databaserepo.BillingAccountsDAO;

public class ConfigBillingAccounts {
	public void addBill(String[] args) {
		int billId = Integer.parseInt(args[1]);
		int amt = Integer.parseInt(args[2]);
		int discountedAmt = Integer.parseInt(args[3]);
		String billingAddress = args[4];
		int dbFlag = Integer.parseInt(args[5]);
                
                if (billingAddress != null && billId > 0 && amt > 0 && discountedAmt >= 0 && dbFlag > 0) {
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.addBill(billId, amt, discountedAmt, billingAddress, dbFlag);
                }
                else {
                    System.out.println("Not valid data");
                }
	}
	
	public void updateBill(String[] args) {
		int amt = Integer.parseInt(args[1]);
		int discountedAmt = Integer.parseInt(args[2]);
		String billingAddress =args[3];
		int billId = Integer.parseInt(args[4]);
		int dbFlag = Integer.parseInt(args[5]);
                
                if (billingAddress != null && billId > 0 && amt > 0 && discountedAmt >= 0 && dbFlag > 0) {                
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.updateBill(amt, discountedAmt, billingAddress, billId, dbFlag);
                }
                else {
                    System.out.println("Not valid data");
                }
	}

	public void deleteBill(String[] args) {
		int billId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
                
                if (billId > 0 && dbFlag > 0) {                
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.deleteBill(billId, dbFlag);
                }
                else {
                    System.out.println("Not valid data");
                }
	}

	public void addPay(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int billId = Integer.parseInt(args[2]);
		int payId = Integer.parseInt(args[3]);
		int payPersonSSN = Integer.parseInt(args[4]);
		int dbFlag = Integer.parseInt(args[5]);
                
                if (customerId > 0 && billId > 0 && payId > 0 && String.valueOf(payPersonSSN).length() == 7 && dbFlag > 0) {
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.payBill(customerId, billId, payId, payPersonSSN, dbFlag);
                }
                else {
                    System.out.println("Not valid data");
                }
	}

	public void addGenerate(String[] args) {
		int staffId = Integer.parseInt(args[1]);
		int billId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
                if (staffId > 0 && billId > 0 && dbFlag > 0) {
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.addGenerate(staffId, billId, dbFlag);
                }
                else {
                    System.out.println("Not valid data");
                }
	}

	public void checkTotalAmount(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
                
                if (customerId > 0 && dbFlag > 0) {
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.checkTotalAmount(customerId, dbFlag);
                }
                else {
                    System.out.println("Not valid data");   
                }
	}

	public void checkItemizedTotalAmount(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
                
                if (customerId > 0 && dbFlag > 0) {
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.checkItemizedTotalAmount(customerId, dbFlag);
                }
                else {
                    System.out.println("Not valid data");  
                }
	}
	
}
