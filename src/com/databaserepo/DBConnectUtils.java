package com.databaserepo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


/**
 * @author Kartik Shah
 *
 */
public class DBConnectUtils {
	private static String sourceClass = DBConnectUtils.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	public static final String DRIVER_NAME_DB2 = "com.ibm.db2.jcc.DB2Driver";
	public static final String DATABASE_HOSTNAME_DB2 = "mtdb01.multitool.lenovo.com";
	public static final String DATABASE_PORTNUMBER_DB2 = "50002";
	public static final String DATABASE_NAME_DB2 = "Multidb";
	public static final String DATABASE_USERNAME_DB2 = "custlogs";
	public static final String DATABASE_PASSWORD_DB2 = "zaq12wsx";
	
	private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/ljackma"; // Using SERVICE_NAME
    private static final String user = "ljackma";
    private static final String password = "group4";    


	//
	public static String DBSCHEMA="db2inst2";
	
	public final Connection getConnection(int flag) throws SQLException {
		String sourceMethod = "getConnection";

		if (flag == 1) {
			// If DB2 connection
			DBSCHEMA="db2inst2";
			Connection dbConn = getDB2Connection();
			log.exiting(sourceClass, sourceMethod);
			return dbConn;
		} else {
			//If Maria DB connection
			DBSCHEMA="ljackma";
			Connection dbConn = getMariaDBConnection();
			log.exiting(sourceClass, sourceMethod);
			return dbConn;
		}
	}

	private Connection getMariaDBConnection() {
		// TODO Auto-generated method stub
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, user, password);
			return connection;
		} catch(Throwable oops) {
            oops.printStackTrace();
            return null;
        }
		
		//return null;
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
