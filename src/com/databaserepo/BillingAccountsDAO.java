package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dataobject.Hotel;

public class BillingAccountsDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	public String addBill(int billId, int amt, int discountedAmt, String billingAddress, int dbFlag){
		String sourceMethod = "addBill";
		String insertHotelDataQuery = "INSERT INTO "+DBConnectUtils.DBSCHEMA+".BILLS (BILL_ID, AMOUNT, DISCOUNTED_AMT, BILLING_ADDRESS) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, billId);
			preparedStatement.setInt(2, amt);
			preparedStatement.setInt(3, discountedAmt);
			preparedStatement.setString(4, billingAddress);
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		log.exiting(sourceClass, sourceMethod, "Added");
		return "Added Billing Records";
	}
	
	public void updateBill(int amt, int discountedAmt, String billingAddress,int billId, int dbFlag) {
		String sourceMethod = "updateBill";
		String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".BILLS SET AMOUNT = ?, DISCOUNTED_AMT = ?, BILLING_ADDRESS = ? WHERE BILL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfUpdatedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, amt);
			preparedStatement.setInt(2, discountedAmt);
			preparedStatement.setString(3, billingAddress);
			preparedStatement.setInt(4, billId);
			numberOfUpdatedRows = preparedStatement.executeUpdate();
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
		log.exiting(sourceClass, sourceMethod, numberOfUpdatedRows);
		System.out.println("numberOfUpdatedRows: "+numberOfUpdatedRows);
	}

	public void deleteBill(int billId, int dbFlag) {
		String sourceMethod = "deleteBill";
		String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".BILLS WHERE BILL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfDeletedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
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

	public String payBill(int customerId, int billId, int payId, int payPersonSSN, int dbFlag) {
		String sourceMethod = "payBill";
		String insertHotelDataQuery = "INSERT INTO "+DBConnectUtils.DBSCHEMA+".PAYS ( CUSTOMER_ID, BILL_ID, PAY_ID, PAYPERSONSSN) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, customerId);
			preparedStatement.setInt(2, billId);
			preparedStatement.setInt(3, payId);
			preparedStatement.setInt(4, payPersonSSN);
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		log.exiting(sourceClass, sourceMethod, "Added");
		return "Added Pay Records";
	}

	public String addGenerate(int staffId, int billId, int dbFlag) {
		String sourceMethod = "addGenerate";
		String insertHotelDataQuery = "INSERT INTO "+DBConnectUtils.DBSCHEMA+".GENERATES ( STAFF_ID, BILL_ID) VALUES(?,?)";
		PreparedStatement preparedStatement = null;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, staffId);
			preparedStatement.setInt(2, billId);
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
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
		log.exiting(sourceClass, sourceMethod, "Added");
		return "Added Who Generates Records";
	}

	public void checkTotalAmount(int customerId, int dbFlag) {
		String sourceMethod = "checkTotalAmount";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT CUSTOMER.NAME, BILLS.AMOUNT, CASE WHEN PAYMENT.PAY_METHOD = 'hotel credit' THEN AMOUNT-DISCOUNTED_AMT ELSE AMOUNT END AS Total_Amount_Due_After_Discount "+
										"FROM (( "+DBConnectUtils.DBSCHEMA+".CUSTOMER AS CUSTOMER INNER JOIN "+DBConnectUtils.DBSCHEMA+".PAYS AS PAYS ON CUSTOMER.CUSTOMER_ID=PAYS.CUSTOMER_ID "+
										"INNER JOIN  "+DBConnectUtils.DBSCHEMA+".PAYMENT AS PAYMENT ON PAYS.PAY_ID=PAYMENT.PAY_ID) INNER JOIN  "+DBConnectUtils.DBSCHEMA+".BILLS AS BILLS ON BILLS.BILL_ID=PAYS.BILL_ID) "+
										"WHERE CUSTOMER.CUSTOMER_ID=?";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, customerId);
			selectQueryRS = stmt.executeQuery();
			while (selectQueryRS.next()) {
				String name= selectQueryRS.getString("NAME");
				int amount= selectQueryRS.getInt("AMOUNT");
				int totalamount= selectQueryRS.getInt("Total_Amount_Due_After_Discount");
				System.out.println(name + " "+amount+" "+totalamount);
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

	public void checkItemizedTotalAmount(int customerId, int dbFlag) {
		String sourceMethod = "checkItemizedTotalAmount";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			
			String selectStatement = "SELECT CUSTOMER.NAME, SERVICE_RECORDS.NAME AS SERVICENAME, SERVICE_RECORDS.COST,PROVIDES.TIMESTATE "+
									"FROM (("+DBConnectUtils.DBSCHEMA+".CUSTOMER AS CUSTOMER INNER JOIN "+DBConnectUtils.DBSCHEMA+".PROVIDES AS PROVIDES ON CUSTOMER.CUSTOMER_ID=PROVIDES.CUSTOMER_ID) "+
									"INNER JOIN  "+DBConnectUtils.DBSCHEMA+".SERVICE_RECORDS AS SERVICE_RECORDS ON PROVIDES.SERVICE_ID=SERVICE_RECORDS.SERVICE_RECORD_ID) "+
									"WHERE CUSTOMER.CUSTOMER_ID=?";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, customerId);
			selectQueryRS = stmt.executeQuery();
			while (selectQueryRS.next()) {
				String name= selectQueryRS.getString("NAME");
				String name1= selectQueryRS.getString("SERVICENAME");
				int amount= selectQueryRS.getInt("COST");
				String timestate= selectQueryRS.getString("TIMESTATE");
				System.out.println(name+" "+name1 + " "+amount+" " +timestate);
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
