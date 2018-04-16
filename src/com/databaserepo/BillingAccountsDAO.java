package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	

	//Design Decision and what does assignRoomAndSetAvailability method does:
	/*
	 * At Step 1:
		 We change the auto commit to false because we want to rollback a transaction if something goes wrong or execute everything if everything goes right. 
		 So, we want to commit it manually and not automatically.
		 We do a select query to check the total amount obtained from the services that the customer used 
		 and total number of nights he stayed multiply by nightly rate of that room. 
		 Select query can be outside of auto commit false because select query is independent of commits. 
		 We have kept select query within auto commit false just because it doesn't hurt to keep there and also it is for 
		 our understanding that Select is a part of transaction.
		 
		 At Step 2:
		 The real transaction starts from here. We insert in bills table the record. 
		 Basically, generate the bill(Select query in Step 1 and insert bill record in Step 2). 
		 It will generate a bill id that will be used in next step.
		 
		 At Step 3:
		 Transaction still continues and isn't committed yet. 
		 We inserted the record in pays table to map bill using bill id( from Step 2) and payment details.

		 At Step 4:
		 We update the Rooms table with availability = 1 meaning the room is available.
		 If above all these executes properly then in the end we commit the transaction.
		 
 		 At Step 5:
		 If there is an exception, we rollback the whole transaction and nothing persists.
		 
		 At Step 6:
		 We set the auto commit to true always in the finally block because we want other transactions to behave in original manner like execute and persist way automatically.
		 We close all the connection.
		 
	 */
	public String checkOut(int custID, String checkIn, int payMethodID, String billingAddress, String paySSN, int roomNo, int hotelID, String checkOut, int dbFlag){
		/*
		 * input: Customer ID, check-in time, payment method, billing address, person paying SSN, room number, hotel, dbFlag
		 * purpose: check out a customer (add bill, pay bill, change room availability)
		 * Note: amount is set based on the getAmtQuery which calculates the cost of the room and services used.
		 * Note:discounted amount is set based on the payment method. If the method is hotel credit then a 5% discount is applied.
		 */
		
		String sourceMethod = "checkout";
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		Connection dbConn = null;
		ResultSet rsBill = null;
		ResultSet rs2 = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			//Step 1:
			dbConn.setAutoCommit(false);
			String getAmtQuery = "SELECT DATEDIFF(ASSIGNS.CHECK_OUT, ASSIGNS.CHECK_IN)*NIGHTLY_RATE"
					+"+ IFNULL((SELECT SUM(SERVICE_RECORDS.COST) AS TOTALCOST FROM "
					+ "("+DBConnectUtils.DBSCHEMA+".PROVIDES JOIN "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS ON SERVICE_RECORDS.SERVICE_RECORD_ID = PROVIDES.SERVICE_ID) "
					+ "JOIN "+DBConnectUtils.DBSCHEMA+".ASSIGNS ON PROVIDES.CUSTOMER_ID = ASSIGNS.CUSTOMER_ID WHERE PROVIDES.TIMESTATE BETWEEN "
					+ "ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT AND ASSIGNS.CUSTOMER_ID = ? AND ASSIGNS.CHECK_IN LIKE "
					+ "? GROUP BY ASSIGNS.CUSTOMER_ID, ASSIGNS.CHECK_IN),0) AS RATE FROM "+DBConnectUtils.DBSCHEMA+".ASSIGNS NATURAL "
					+"JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ASSIGNS.CHECK_IN LIKE ? AND CUSTOMER_ID = ?";
			final int AMOUNT_RESULT_COLUMN = 1;
			preparedStatement2 = dbConn.prepareStatement(getAmtQuery);
			int amt = 0;
			int discountedAmt = 0;
			preparedStatement2.setInt(1, custID);
			preparedStatement2.setString(2, checkIn);
			preparedStatement2.setString(3, checkIn);
			preparedStatement2.setInt(4, custID);
			rs2 = preparedStatement2.executeQuery();//Execute query is used for select query.
			if(rs2.next()){
				amt = rs2.getInt(AMOUNT_RESULT_COLUMN); //Get amount
			}
			if(payMethodID == 2){
				discountedAmt = Math.round(amt*0.05f); //Get discounted amount if payment method is hotel credit card.
			} else {
				discountedAmt = 0;
			}
			
			//Step 2:
			String insertBillQuery = "INSERT INTO "+DBConnectUtils.DBSCHEMA+".BILLS (AMOUNT, DISCOUNTED_AMT, BILLING_ADDRESS,BILL_TIMESTAMP) VALUES(?,?,?,?)";
			final int AMOUNT_COLUMN = 1;
			final int DISCOUNTED_AMT_COLUMN = 2;
			final int BILLING_ADDRESS_COLUMN = 3;
			final int TIME_COLUMN = 4;
			preparedStatement1 = dbConn.prepareStatement(insertBillQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement1.setInt(AMOUNT_COLUMN, amt);
			preparedStatement1.setInt(DISCOUNTED_AMT_COLUMN, discountedAmt);
			preparedStatement1.setString(BILLING_ADDRESS_COLUMN, billingAddress);
			preparedStatement1.setString(TIME_COLUMN, checkOut);
			preparedStatement1.execute();
			rsBill = preparedStatement1.getGeneratedKeys();
			int akey = 0;
			while(rsBill.next()){
				akey = Integer.parseInt(rsBill.getString(1));  //Return Auto generated Bill Id Key
			}
			
			//Step 3:
			String insertPayQuery = "INSERT INTO "+DBConnectUtils.DBSCHEMA+".PAYS () VALUES(?,?,?,?)";
			preparedStatement3 = dbConn.prepareStatement(insertPayQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement3.setInt(1, custID);
			preparedStatement3.setInt(2, akey);
			preparedStatement3.setInt(3, payMethodID);
			preparedStatement3.setString(4, paySSN);
			preparedStatement3.execute(); //Execute is used to insert query data.
			
			//Step 4:
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOMS SET AVAILABILITY=? WHERE ROOM_NO=? AND HOTEL_ID=?";
			preparedStatement3 = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement3.setInt(1, 1);
			preparedStatement3.setInt(2, roomNo);
			preparedStatement3.setInt(3, hotelID);
			preparedStatement3.executeUpdate(); //Execute update is used for update query
			dbConn.commit();
			System.out.println("Check out complete. Bill paid. Room set to available.");
		} catch (SQLException e) {
			try {
				//Step 5:
				dbConn.rollback();
			} catch (SQLException e1) {
				System.out.println("The transaction will be rolled back because :");
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			// Prints the error message if something goes wrong when updating.
		} finally {
			try {
				//Step 6:
				dbConn.setAutoCommit(true);
				if (preparedStatement1 != null) {
					preparedStatement1.close();
				}
				if (dbConn != null) {
					/*
					 * Since we are using a dbConn.commit() or
					 * dbConn.rollback() prior to the close so, the
					 * connection remains in progress when we try to close. This
					 * step will help to close the connections. It is in final
					 * block so that it always executes and the program doesn't
					 * throw any exception while closing connection.
					 */
					dbConn.close();
				}
			} catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, "Added");
		System.out.println("Added Billing Records");
		return "Added Billing Records";
	}
	
	public void updatePayBill(int billID, int custID, String checkIn, int payMethodID, String billingAddress, int dbFlag) {
		/*
		 * input: bill ID, customer ID, check-in time, billing address, dbflag
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
		/*
		 * input: bill ID, dbflag
		 * purpose: deletes a bill from the bills table. 
		 * note: Bill ID records will be deleted from all child tables by foreign key constraint
		 */
		String sourceMethod = "deletebill";
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
		/*
		 * input: customer ID, check-in time, dbflag
		 * purpose: returns itemized bill (rate of room, number of days room occupied, services used and cost of services)
		 */	
		String sourceMethod = "checkitemizedtotalamount";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT PROVIDES.CUSTOMER_ID,SERVICE_RECORDS.NAME AS 'SERVICE_NAME', PROVIDES.SERVICE_ID, 0 AS 'No_Days', SERVICE_RECORDS.COST AS 'RATE' "
					+ "FROM (("+DBConnectUtils.DBSCHEMA+".PROVIDES INNER "
					+ "JOIN "+DBConnectUtils.DBSCHEMA+".ASSIGNS ON "
					+ "PROVIDES.CUSTOMER_ID=ASSIGNS.CUSTOMER_ID) INNER JOIN "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS ON "
					+ "PROVIDES.SERVICE_ID=SERVICE_RECORDS.SERVICE_RECORD_ID) WHERE "
					+ "PROVIDES.CUSTOMER_ID=?  AND ASSIGNS.CHECK_IN LIKE ? AND PROVIDES.TIMESTATE BETWEEN ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT "
					+ "UNION SELECT ASSIGNS.CUSTOMER_ID, 'ROOM' AS 'SERVICE_NAME' ,' ' AS SERVICE_ID , DATEDIFF(ASSIGNS.CHECK_OUT, ASSIGNS.CHECK_IN) as 'No_Days', "
					+ "ROOMS.NIGHTLY_RATE AS 'RATE' FROM ("+DBConnectUtils.DBSCHEMA+".ASSIGNS INNER JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS ON "
					+ "ASSIGNS.ROOM_NO=ROOMS.ROOM_NO AND ASSIGNS.HOTEL_ID=ROOMS.HOTEL_ID) WHERE ASSIGNS.CUSTOMER_ID=? AND ASSIGNS.CHECK_IN LIKE ?";

			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, customerId);
			stmt.setString(2, checkIn);
			stmt.setInt(3, customerId);
			stmt.setString(4, checkIn);
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
		/*
		 * input: customer ID, check-in time, dbflag
		 * purpose: returns total amount due to customer
		 * note: two returns - 1) total amount 2) total amount if using hotel credit (5% discount, rounded to nearest $)
		 */	
		String sourceMethod = "checktotalamountduringstay";
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
