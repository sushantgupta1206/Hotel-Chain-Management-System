package com.dataconfig;

import com.databaserepo.BillingAccountsDAO;

public class ConfigBillingAccounts {
        /*
            input: Bill ID, Amount, Discounted Amount, Billing Address
            output: N/A (Adds a new bill to table)
        */
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
	
        /*
            input: Bill ID, Amount, Discounted Amount, Billing Address
            output: N/A (Changes information on a specific bill)
        */
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

        /*
            input: Bill ID
            output: N/A (Deletes a specific bill from table)
        */
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

        /*
            input: Customer ID, Bill ID, Payment ID, Payer SSN
            output: N/A (Adds a payment method for customer)
        */
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

        /*
            input: Staff ID, Bill ID
            output: N/A (Adds a new note on who generated a bill)
        */
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

        /*
            input: Customer ID
            output: Gives total amount that a customer owns
        */        
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

        /*
            input: Customer ID
            output: Gives itemized receipt of specific customer (Services, cost of stay)
        */        
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
