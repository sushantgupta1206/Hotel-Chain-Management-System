package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Kartik Shah
 *
 */

/*
 * This class performs all the core application task and operations to maintain services and its associated entities.
   This class calls DBConnectUtils to get the database connection.
 */

public class MaintainingServiceRecordsDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	/*
	 * inputs: customer id, staffid, service id, service time, dbflag
	 * outputs: N/A (adds a row to the provides table)
	 */
	
	public void addProvidesServices(int customerId, int staffId, int serviceID,  String serviceTime, int dbFlag){
		String sourceMethod = "addServices";
		InformationProcessingDAO informationProcessingDAO = new InformationProcessingDAO();
		if(informationProcessingDAO.showCustomer(customerId, dbFlag) && informationProcessingDAO.showStaff(staffId, dbFlag) && 
				informationProcessingDAO.checkCustomerByTime(customerId,serviceTime, dbFlag)){
			
			String insertHotelDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".PROVIDES VALUES(?,?,?,?)";
			PreparedStatement preparedStatement = null;
			Connection dbConn = null;
			ResultSet rs = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, customerId);
				preparedStatement.setInt(2, staffId);
				preparedStatement.setInt(3, serviceID);
				preparedStatement.setString(4, serviceTime);
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
			System.out.println("Added Service Records");
		}
	}
	
	/*
	 * inputs: service record id, staff id, timestamp, customer id, dbflag
	 * outputs: N/A (updates a row in the provides table)
	 */
	
	public void updateProvidesServices(int serviceID, int staffId, String serviceTime, int customerId, int dbFlag) {
		String sourceMethod = "updateProvidesServices";
		InformationProcessingDAO informationProcessingDAO = new InformationProcessingDAO();
		if(informationProcessingDAO.showCustomer(customerId, dbFlag) && informationProcessingDAO.showStaff(staffId, dbFlag)){
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".PROVIDES SET SERVICE_ID = ?, STAFF_ID=? WHERE TIMESTATE = ? and CUSTOMER_ID=?";
			PreparedStatement preparedStatement = null;
			int numberOfUpdatedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, serviceID);
				preparedStatement.setInt(2, staffId);
				preparedStatement.setString(3, serviceTime);
				preparedStatement.setInt(4, customerId);
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
	}
}
