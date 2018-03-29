package com.databaserepo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


public class DBConnectUtils {
	private static String sourceClass = DBConnectUtils.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	public static final String DRIVER_NAME_DB2 = "com.ibm.db2.jcc.DB2Driver";
	public static final String DATABASE_HOSTNAME_DB2 = "mtdb01.multitool.lenovo.com";
	public static final String DATABASE_PORTNUMBER_DB2 = "50002";
	public static final String DATABASE_NAME_DB2 = "Multidb";
	public static final String DATABASE_USERNAME_DB2 = "custlogs";
	public static final String DATABASE_PASSWORD_DB2 = "zaq12wsx";

	public static final String DBSCHEMA="DB2INST2";
	
	public final Connection getConnection(int flag) throws SQLException {
		String sourceMethod = "getConnection";

		if (flag == 1) {
			// If DB2 connection
			Connection dbConn = getDB2Connection();
			log.exiting(sourceClass, sourceMethod);
			return dbConn;
		} else {
			//If Maria DB connection
			Connection dbConn = getMariaDBConnection();
			log.exiting(sourceClass, sourceMethod);
			return dbConn;
		}
	}

	private Connection getMariaDBConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	private Connection getDB2Connection() throws SQLException {
		try {
			Class.forName(DRIVER_NAME_DB2);
		} catch (ClassNotFoundException thisIsBad) {
			throw new IllegalArgumentException(thisIsBad);
		}
		String url = "jdbc:db2://" + DATABASE_HOSTNAME_DB2 + ":" + DATABASE_PORTNUMBER_DB2 + "/" + DATABASE_NAME_DB2;
		Connection db2Conn = DriverManager.getConnection(url, DATABASE_USERNAME_DB2, DATABASE_PASSWORD_DB2);
		return db2Conn;
	}
}
