package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dataobject.Hotel;

public class InformationProcessingDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	public void getFileDataList(int flag) {
		String sourceMethod = "getFileDataList";
		/*List<CustomerLogFile> logFileCustomDetails = new ArrayList<CustomerLogFile>();*/
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(flag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT fd.id FROM UPLOADS.FILE_DATA fd WHERE fd.DISPOSITON_ID != 1";
			selectQueryRS = stmt.executeQuery(selectStatement);
			while (selectQueryRS.next()) {
				System.out.println(selectQueryRS.getInt("id"));
				/*CustomerLogFile customerLogFileCustomDetail = new CustomerLogFile();
				customerLogFileCustomDetail.setId(selectQueryRS.getInt("id"));
				customerLogFileCustomDetail.setUrl(uriInfo.getRequestUri().getPath()+"upload/files/"+ customerLogFileCustomDetail.getId());
				logFileCustomDetails.add(customerLogFileCustomDetail);*/
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
	
	
	
	public int addHotelDB(Hotel hotelData, int dbFlag){
		String sourceMethod = "addHotelDB";
		String insertHotelDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".HOTELS ( HOTEL_ID, PHONE, NAME, ADDRESS, CITY ) VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int generatedKey = 0;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, hotelData.getId());
			preparedStatement.setInt(2, hotelData.getPhone());
			preparedStatement.setString(3, hotelData.getName());
			preparedStatement.setString(4, hotelData.getAddress());
			preparedStatement.setString(5, hotelData.getCity());
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
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
		log.exiting(sourceClass, sourceMethod, generatedKey);
		return generatedKey;
	}
	
	
}
