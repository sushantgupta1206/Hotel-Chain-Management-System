package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Contains methods for each task and operation in maintaining billing accounts.
 */
public class BillingAccountsDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	
	public String payBill(int billID, int custID, String checkIn, int payMethodID, String billingAddress, int dbFlag){
	/*
	 * input: billID, customerID, checkIn time, payment method, billing address, dbflag
	 * purpose: add a bill for a customer's stay to the database
	 * Note: amount is set based on the getAmtQuery which calculates the cost of the room and services used.
	 * Note:discounted amount is set based on the payment method. If the method is hotel credit then a 5% discount is applied.
	 * 
	 */
		String sourceMethod = "addBill";
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		Connection dbConn = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String getAmtQuery = "SELECT SUM(B.COST) + DATEDIFF(A.CHECK_OUT, A.CHECK_IN)*A.NIGHTLY_RATE FROM "
					+ "(SELECT * FROM "+DBConnectUtils.DBSCHEMA+".PROVIDES JOIN "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS ON "
					+ "SERVICE_RECORDS.SERVICE_RECORD_ID = PROVIDES.SERVICE_ID) AS B JOIN "
					+ "(SELECT ASSIGNS.CUSTOMER_ID, ASSIGNS.CHECK_IN, ASSIGNS.CHECK_OUT, NIGHTLY_RATE FROM "
					+DBConnectUtils.DBSCHEMA+".ASSIGNS NATURAL JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ASSIGNS.CHECK_IN LIKE ?) AS A ON B.CUSTOMER_ID = A.CUSTOMER_ID WHERE B.TIMESTATE "
					+ "BETWEEN A.CHECK_IN AND A.CHECK_OUT AND B.CUSTOMER_ID = ?;";
			final int AMOUNT_RESULT_COLUMN = 1;
			preparedStatement2 = dbConn.prepareStatement(getAmtQuery);
			int amt = 0;
			int discountedAmt = 0;
			preparedStatement2.setString(1, checkIn);
			preparedStatement2.setInt(2, custID);
			rs2 = preparedStatement2.executeQuery();
			if(rs2.next()){
				amt = rs2.getInt(AMOUNT_RESULT_COLUMN);
			}
			if(payMethodID == 2){
				discountedAmt = Math.round(amt*0.05f);
			} else {
				discountedAmt = 0;
			}
			String insertBillQuery = "INSERT INTO "+DBConnectUtils.DBSCHEMA+".BILLS (BILL_ID, AMOUNT, DISCOUNTED_AMT, BILLING_ADDRESS) VALUES(?,?,?,?)";
			final int BILL_ID_COLUMN = 1;
			final int AMOUNT_COLUMN = 2;
			final int DISCOUNTED_AMT_COLUMN = 3;
			final int BILLING_ADDRESS_COLUMN = 4;
			preparedStatement1 = dbConn.prepareStatement(insertBillQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement1.setInt(BILL_ID_COLUMN, billID);
			preparedStatement1.setInt(AMOUNT_COLUMN, amt);
			preparedStatement1.setInt(DISCOUNTED_AMT_COLUMN, discountedAmt);
			preparedStatement1.setString(BILLING_ADDRESS_COLUMN, billingAddress);
			preparedStatement1.execute();
			rs1 = preparedStatement1.getGeneratedKeys();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (rs1 != null) {
					rs1.close();
				}
				if (preparedStatement1 != null) {
					preparedStatement1.close();
				} 
				if (rs2 != null) {
					rs2.close();
				}
				if (preparedStatement2 != null) {
					preparedStatement2.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			}catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, "Added");
		return "Added Billing Records";
	}
	
	public void updatePayBill(int billID, int custID, String checkIn, int payMethodID, String billingAddress, int dbFlag) {
		/*
		 * input: billID, amount, discounted amount, billing address
		 * purpose: update a bill row in the database
		 * note: amount and discounted amount are recalculated to ensure accuracy of update.
		 */
		String sourceMethod = "updateBill";
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		Connection dbConn = null;
		ResultSet rs2 = null;
		int numberOfUpdatedRows = 0;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String getAmtQuery = "SELECT SUM(B.COST) + DATEDIFF(A.CHECK_OUT, A.CHECK_IN)*A.NIGHTLY_RATE FROM "
					+ "(SELECT * FROM "+DBConnectUtils.DBSCHEMA+".PROVIDES JOIN "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS ON "
					+ "SERVICE_RECORDS.SERVICE_RECORD_ID = PROVIDES.SERVICE_ID) AS B JOIN "
					+ "(SELECT ASSIGNS.CUSTOMER_ID, ASSIGNS.CHECK_IN, ASSIGNS.CHECK_OUT, NIGHTLY_RATE FROM "
					+DBConnectUtils.DBSCHEMA+".ASSIGNS NATURAL JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ASSIGNS.CHECK_IN LIKE ?) AS A ON B.CUSTOMER_ID = A.CUSTOMER_ID WHERE B.TIMESTATE "
					+ "BETWEEN A.CHECK_IN AND A.CHECK_OUT AND B.CUSTOMER_ID = ?;";
			final int AMOUNT_RESULT_COLUMN = 1;
			int amt = 0;
			int discountedAmt = 0;
			preparedStatement2 = dbConn.prepareStatement(getAmtQuery);
			preparedStatement2.setString(1, checkIn);
			preparedStatement2.setInt(2, custID);
			rs2 = preparedStatement2.executeQuery();
			if(rs2.next()){
				amt = rs2.getInt(AMOUNT_RESULT_COLUMN);
			}
			if(payMethodID == 2){
				discountedAmt = Math.round(amt*0.05f);
			} else {
				discountedAmt = 0;
			}
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".BILLS SET AMOUNT = ?, DISCOUNTED_AMT = ?, BILLING_ADDRESS = ? WHERE BILL_ID = ?";
			preparedStatement1 = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement1.setInt(1, amt);
			preparedStatement1.setInt(2, discountedAmt);
			preparedStatement1.setString(3, billingAddress);
			preparedStatement1.setInt(4, billID);
			numberOfUpdatedRows = preparedStatement1.executeUpdate();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (preparedStatement1 != null) {
					preparedStatement1.close();
				} 
				if (preparedStatement2 != null) {
					preparedStatement2.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			}catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, numberOfUpdatedRows);
		System.out.println("numberOfUpdatedRows: "+numberOfUpdatedRows);
	}

	public void deletePayBill(int billId, int dbFlag) {
		String sourceMethod = "deleteBill";
		PreparedStatement preparedStatement = null;
		int numberOfDeletedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".BILLS WHERE BILL_ID = ?";
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, billId);
			numberOfDeletedRows = preparedStatement.executeUpdate();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			}catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, numberOfDeletedRows);
		System.out.println("numberOfDeletedRows: "+numberOfDeletedRows);
		
	}

		public void checkItemizedTotalAmount(int customerId, String checkIn, int dbFlag) {
		String sourceMethod = "checkItemizedTotalAmount";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT PROVIDES.CUSTOMER_ID,SERVICE_RECORDS.NAME AS 'SERVICE_NAME', PROVIDES.SERVICE_ID, 0 AS 'No_Days', SERVICE_RECORDS.COST AS 'RATE' "
					+ "FROM (("+DBConnectUtils.DBSCHEMA+".PROVIDES INNER "
					+ "JOIN "+DBConnectUtils.DBSCHEMA+".ASSIGNS ON "
				+	"PROVIDES.CUSTOMER_ID=ASSIGNS.CUSTOMER_ID) INNER JOIN "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS ON "
						+ "PROVIDES.SERVICE_ID=SERVICE_RECORDS.SERVICE_RECORD_ID) WHERE "
			+"PROVIDES.CUSTOMER_ID=?  AND ASSIGNS.CHECK_IN LIKE ? AND PROVIDES.TIMESTATE BETWEEN ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT "
			+"UNION SELECT ASSIGNS.CUSTOMER_ID, 'ROOM' AS 'SERVICE_NAME' ,' ' AS SERVICE_ID , DATEDIFF(ASSIGNS.CHECK_OUT, ASSIGNS.CHECK_IN) as 'No_Days', "
			+ "ROOMS.NIGHTLY_RATE AS 'RATE' FROM ("+DBConnectUtils.DBSCHEMA+".ASSIGNS INNER JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS ON "
					+ "ASSIGNS.ROOM_NO=ROOMS.ROOM_NO AND ASSIGNS.HOTEL_ID=ROOMS.HOTEL_ID) WHERE ASSIGNS.CUSTOMER_ID=?";

			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, customerId);
			stmt.setString(2, checkIn);
			stmt.setInt(3, customerId);
			selectQueryRS = stmt.executeQuery();
			System.out.println("CUSTOMER_ID    SERVICE_NAME		SERVICE_ID   No_of_Days    RATE ");
			while (selectQueryRS.next()) {
				int custId= selectQueryRS.getInt("CUSTOMER_ID");
				String servname= selectQueryRS.getString("SERVICE_NAME");
				String servId= selectQueryRS.getString("SERVICE_ID");
				int nod= selectQueryRS.getInt("No_Days");
				int amount= selectQueryRS.getInt("RATE");
				System.out.println(custId+"	 	"+servname+"			"+servId + " 		"+nod+" 	" +amount);
			}
			
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (selectQueryRS != null) {
					selectQueryRS.close();
				}
				if (stmt != null) {
					stmt.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			} catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
	}
	
	public void checkTotalAmountDuringStay(int customerId, String checkIn, int dbFlag) {
		String sourceMethod = "checkItemizedTotalAmount";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String getAmtQuery = "SELECT SUM(B.COST) + DATEDIFF(A.CHECK_OUT, A.CHECK_IN)*A.NIGHTLY_RATE FROM "
					+ "(SELECT * FROM "+DBConnectUtils.DBSCHEMA+".PROVIDES JOIN "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS ON "
					+ "SERVICE_RECORDS.SERVICE_RECORD_ID = PROVIDES.SERVICE_ID) AS B JOIN "
					+ "(SELECT ASSIGNS.CUSTOMER_ID, ASSIGNS.CHECK_IN, ASSIGNS.CHECK_OUT, NIGHTLY_RATE FROM "
					+DBConnectUtils.DBSCHEMA+".ASSIGNS NATURAL JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ASSIGNS.CHECK_IN LIKE ?) AS A ON B.CUSTOMER_ID = A.CUSTOMER_ID WHERE B.TIMESTATE "
					+ "BETWEEN A.CHECK_IN AND A.CHECK_OUT AND B.CUSTOMER_ID = ?;";
			final int AMOUNT_RESULT_COLUMN = 1;
			stmt = dbConn.prepareStatement(getAmtQuery);
			int totalamount=0;
			int discountedAmt = 0;
			stmt.setString(1, checkIn);
			stmt.setInt(2, customerId);
			selectQueryRS = stmt.executeQuery();
			if(selectQueryRS.next()){
				totalamount = selectQueryRS.getInt(AMOUNT_RESULT_COLUMN);
				discountedAmt = Math.round(totalamount*0.95f);
				System.out.println("TOTAL:" + totalamount);
				System.out.println("TOTAL IF USING HOTEL CREDIT:" + discountedAmt);
			}else{
				System.out.println("No results returned.");
			}
			
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (selectQueryRS != null) {
					selectQueryRS.close();
				}
				if (stmt != null) {
					stmt.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			} catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
	}
}
