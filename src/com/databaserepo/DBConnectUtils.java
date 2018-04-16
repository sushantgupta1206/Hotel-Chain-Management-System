package com.databaserepo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


/**
 * @author Kartik Shah
 *
 */

/*
 * This class provides Database connection when we provide specific database drivers, username and password.
 */
public class DBConnectUtils {
	private static String sourceClass = DBConnectUtils.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/ljackma"; // Using SERVICE_NAME
    private static final String user = "ljackma";
    private static final String password = "group4";    


    public static String DBSCHEMA="ljackma";
	public final Connection getConnection(int flag) throws SQLException {
		String sourceMethod = "getConnection";

		if (flag == -12) {
			//Flag = -12 is a random selected number if programmer wants to use DB2 connection practice version. 
			//It should use DB2 database credentials.
			DBSCHEMA="ljackma";
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

	//Provides Maria DB connection
	private Connection getMariaDBConnection() {
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, user, password);
			return connection;
		} catch(Throwable oops) {
            oops.printStackTrace();
            return null;
        }
	}

	private Connection getDB2Connection() throws SQLException {
		//Replace all Maria db connection with db2.
		return getMariaDBConnection();
	}
}
