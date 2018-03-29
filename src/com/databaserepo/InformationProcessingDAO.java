package com.databaserepo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InformationProcessingDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	public void getFileDataList() {
		String sourceMethod = "getFileDataList";
		/*List<CustomerLogFile> logFileCustomDetails = new ArrayList<CustomerLogFile>();*/
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection();
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
	public static void main(String args[]){
		InformationProcessingDAO dao=new InformationProcessingDAO();
		dao.getFileDataList();
	}

}
