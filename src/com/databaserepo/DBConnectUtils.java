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
	
	private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/ljackma"; // Using SERVICE_NAME
    private static final String user = "ljackma";
    private static final String password = "group4";    


    public static String DBSCHEMA="ljackma";
	public final Connection getConnection(int flag) throws SQLException {
		String sourceMethod = "getConnection";

		if (flag == -12) {
			// If DB2 connection practice version
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
		//Replaced all Maria db connection with db2.
		return getMariaDBConnection();
	}
}
