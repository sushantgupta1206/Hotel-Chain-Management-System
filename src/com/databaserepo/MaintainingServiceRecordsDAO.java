package com.databaserepo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dataobject.Hotel;

/**
 * @author Kartik Shah
 *
 */
public class MaintainingServiceRecordsDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	public String addProvidesServices(int customerId, int staffId, int serviceID,  String serviceTime, int dbFlag){
		String sourceMethod = "addServices";
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
		return "Added Service Records";
	}
}
