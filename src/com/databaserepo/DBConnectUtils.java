package com.databaserepo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DBConnectUtils {
		private static String sourceClass = DBConnectUtils.class.getName();
		private static Logger log = Logger.getLogger(sourceClass);
		private DataSource dataSource;
		public static final String DRIVER_NAME="com.ibm.db2.jcc.DB2Driver";
		public static final String DATABASE_HOSTNAME="mtdb01.multitool.lenovo.com";
		public static final String DATABASE_PORTNUMBER = "50002";
		public static final String DATABASE_NAME = "Multidb";
		public static final String DATABASE_USERNAME = "custlogs";
		public static final String DATABASE_PASSWORD="zaq12wsx";
		  
		public final Connection getConnection() throws SQLException {
		    String sourceMethod = "getConnection";
		    try {
		    	Class.forName(DRIVER_NAME);
		    }
		    catch (ClassNotFoundException thisIsBad) {
		    	throw new IllegalArgumentException(thisIsBad);
		    }
		    String url= "jdbc:db2://" + DATABASE_HOSTNAME + ":" + DATABASE_PORTNUMBER + "/" + DATABASE_NAME;
		    Connection db2Conn = DriverManager.getConnection (url, DATABASE_USERNAME, DATABASE_PASSWORD);
		    log.exiting(sourceClass, sourceMethod);
		    return db2Conn;
		  }
}
