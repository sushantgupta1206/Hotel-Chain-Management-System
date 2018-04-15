package com.dataconfig;

import com.databaserepo.BillingAccountsDAO;

public class ConfigBillingAccounts {
	   /*
    input: bill ID, customer ID, check in time, payment method id, billing 

address
    output: N/A (Adds a new bill to table)
*/
public void payBill(String[] args) {
	int billId = Integer.parseInt(args[1]);
	int custID = Integer.parseInt(args[2]);
	String checkIn = args[3];
	int payMethodID = Integer.parseInt(args[4]);
	String billingAddress = args[5];
	int dbFlag = Integer.parseInt(args[6]);  
	if (billingAddress != null && billId > 0 && dbFlag > 0) {
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.payBill(billId, custID, checkIn, payMethodID, billingAddress, dbFlag);
	}
	else{
		System.out.println("Not valid data");	
	}
}

/*
    input: bill ID, customer ID, check in time, payment method id, billing 

address
    output: string telling user the number of updates made
*/
public void updatePayBill(String[] args) {
	int billId = Integer.parseInt(args[1]);
	int custID = Integer.parseInt(args[2]);
	String checkIn = args[3];
	int payMethodID = Integer.parseInt(args[4]);
	String billingAddress = args[5];
	int dbFlag = Integer.parseInt(args[6]);  
	if (billingAddress != null && billId > 0 && dbFlag > 0) {
		BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
		billingAccountsDAO.updatePayBill(billId, custID, checkIn, payMethodID, billingAddress, dbFlag);
	}
	else {
		System.out.println("Not valid data");	
	}
}        /*
            input: Bill ID
            output: N/A (Deletes a specific bill from table)
        */
	public void deletePayBill(String[] args) {
		int billId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
                
                if (billId > 0 && dbFlag > 0) {                
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.deletePayBill(billId, dbFlag);
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
		String checkIn = args[2];
		int dbFlag = Integer.parseInt(args[3]);
                
                if (customerId > 0 && dbFlag > 0) {
                    BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
                    billingAccountsDAO.checkItemizedTotalAmount(customerId, checkIn,dbFlag);
                }
                else {
                    System.out.println("Not valid data");  
                }
	}


public void checkTotalAmountDuringStay(String[] args) {
	int customerId = Integer.parseInt(args[1]);
	String checkIn = args[2];
	int dbFlag = Integer.parseInt(args[3]);
    
    if (customerId > 0 && dbFlag > 0) {
        BillingAccountsDAO billingAccountsDAO = new BillingAccountsDAO();
        billingAccountsDAO.checkTotalAmountDuringStay(customerId, checkIn, dbFlag);
    }
    else {
        System.out.println("Not valid data");   
    }
}
	
}
