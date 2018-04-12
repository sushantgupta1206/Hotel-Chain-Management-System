package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.dataobject.Customer;
import com.dataobject.Hotel;
import com.dataobject.Room;
import com.dataobject.Staff;
import java.util.*;

/**
 * @author Leah Jackman
 *
 */
public class ReportsDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	/**Report occupancy and percent occupancy by hotel.
	 * @param dbFlag
	 * @return
	 */
	public void occHotel(String checkInDate, String checkOutDate, int dbFlag){		
/*
 * note: check in and check out should be the same day/time
 * inputs: date
 * outputs: string of hotel IDs, occupancy and percent occupancy
 */
		String sourceMethod = "occHotel";
		//List<> hotelOccupancy = new ArrayList<>();
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = " SELECT HOTELS.HOTEL_ID, "
					+ "SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY, "
					+ "SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY"
					+"FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) ON CHECK_IN <= ? "
					+ "AND CHECK_OUT >= ? AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND"
					+ "ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.HOTEL_ID";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setString(1,  checkInDate);
			stmt.setString(2, checkOutDate);
			selectQueryRS = stmt.executeQuery();
			while (selectQueryRS.next()) {
				//Hotel hotel = new Hotel();
				int hotelId = selectQueryRS.getInt("HOTEL_ID");
				int occupancy = selectQueryRS.getInt("OCCUPANCY");
				float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
				System.out.println(hotelId + " " + occupancy + " " + percent);
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

	/*
	 * note: check in and check out should be the same day/time
	 * inputs: date
	 * outputs: string of hotel IDs, occupancy and percent occupancy
	 */
	
	public void occCity(String checkInDate, String checkOutDate, int dbFlag) {
		// TODO Auto-generated method stub
		
				String sourceMethod = "occCity";
				//List<> hotelOccupancy = new ArrayList<>();
				Connection dbConn = null;
				ResultSet selectQueryRS = null;
				PreparedStatement stmt = null;
				try {
					dbConn = dbUtil.getConnection(dbFlag);
					String selectStatement = " SELECT HOTELS.CITY, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY, "
 + "SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY " 
 +  "FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) ON CHECK_IN <= ? " 
 +  "AND CHECK_OUT >= ? AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND "
 +  "ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.CITY" ;
					stmt = dbConn.prepareStatement(selectStatement);
					stmt.setString(1,  checkInDate);
					stmt.setString(2, checkOutDate);
					selectQueryRS = stmt.executeQuery();
					while (selectQueryRS.next()) {
						//Hotel hotel = new Hotel();
						String hotelCity = selectQueryRS.getString("CITY");
						int occupancy = selectQueryRS.getInt("OCCUPANCY");
						float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
						System.out.println(hotelCity + " " + occupancy + " " + percent);
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

	public void occRoom(String checkInDate, String checkOutDate, int dbFlag) {
		// TODO Auto-generated method stub
		String sourceMethod = "occRoom";
		//List<> hotelOccupancy = new ArrayList<>();
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = " SELECT ROOM_HAS.ROOM_CATEGORY_ID, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) "
 +  "AS OCCUPANCY,SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) "
 +  "AS PERCENT_OCCUPANCY FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) LEFT OUTER JOIN " 
 +  "ROOM_HAS ON ROOMS.ROOM_NO = ROOM_HAS.ROOM_NO AND  ROOMS.HOTEL_ID = ROOM_HAS.HOTEL_ID "  
 +  "ON CHECK_IN <= ? AND CHECK_OUT >= ? AND " 
 +  "ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY ROOM_HAS.ROOM_CATEGORY_ID";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setString(1,  checkInDate);
			stmt.setString(2, checkOutDate);
			selectQueryRS = stmt.executeQuery();
			while (selectQueryRS.next()) {
				//Hotel hotel = new Hotel();
				int roomCatId = selectQueryRS.getInt("ROOM_CATEGORY_ID");
				int occupancy = selectQueryRS.getInt("OCCUPANCY");
				float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
				System.out.println(roomCatId + " " + occupancy + " " + percent);
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

	public void showStaff(int dbFlag) {
		// TODO Auto-generated method stub
		String sourceMethod = "showStaff";
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".STAFF ORDER BY TITLE";
			selectQueryRS = stmt.executeQuery(selectStatement);
			while (selectQueryRS.next()) {
				int staffId = selectQueryRS.getInt("STAFF_ID");
				int phone = selectQueryRS.getInt("PHONE");
				String name = selectQueryRS.getString("NAME");
				String address = selectQueryRS.getString("ADDRESS");
				Date dob = selectQueryRS.getDate("DOB");
				String dpt = selectQueryRS.getString("DEPARTMENT");
				String title = selectQueryRS.getString("TITLE");
				int age = selectQueryRS.getInt("AGE");
				System.out.println(staffId + " " + phone + " " + name + " " + address + " " + dob + " " + dpt + " " + title + " " + age);
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

	/**
	 * @param checkInDate
	 * @param custId
	 * @param dbFlag
	 */
	public void custStaff(String checkInDate, int custId, int dbFlag) {
		String sourceMethod = "custStaff";
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT ASSIGNS.STAFF_ID FROM "+DBConnectUtils.DBSCHEMA+".ASSIGNS JOIN "+DBConnectUtils.DBSCHEMA+".PROVIDES ON ASSIGNS.CUSTOMER_ID = PROVIDES.CUSTOMER_ID "
						 + "AND PROVIDES.TIMESTATE BETWEEN ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT WHERE PROVIDES.CUSTOMER_ID = ? " 
						 + "AND ASSIGNS.CHECK_IN = ? UNION SELECT PROVIDES.STAFF_ID FROM "+DBConnectUtils.DBSCHEMA+".ASSIGNS JOIN " 
						 +  DBConnectUtils.DBSCHEMA+".PROVIDES ON ASSIGNS.CUSTOMER_ID = PROVIDES.CUSTOMER_ID AND PROVIDES.TIMESTATE BETWEEN "
						 + "ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT WHERE PROVIDES.CUSTOMER_ID = ? AND " 
						 + "ASSIGNS.CHECK_IN = ?";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, custId);
			stmt.setString(2,  checkInDate);
			stmt.setInt(3, custId);
			stmt.setString(4,  checkInDate);
			selectQueryRS = stmt.executeQuery();
			
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No staff found serving this customer");
			}else{
				System.out.println("Staff found serving this customer");
				while (selectQueryRS.next()) {
					int staffId = selectQueryRS.getInt("STAFF_ID");
					System.out.println(staffId);
				}
			}
		} catch (Exception e) {
			System.out.println("Wrong parameters");
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

	/**
	 * @param startDate
	 * @param endDate
	 * @param dbFlag
	 */
	public void revHotel(String startDate, String endDate, int dbFlag) {
		String sourceMethod = "revHotel";
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = " SELECT HOTEL_ID, NIGHTLY_RATE* DATEDIFF(CHECK_OUT, CHECK_IN) AS TOTAL_REVENUE FROM "+DBConnectUtils.DBSCHEMA+".ASSIGNS " 
						 + "NATURAL JOIN "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ASSIGNS.CHECK_IN BETWEEN ? AND ? " 
						 + "AND ASSIGNS.CHECK_OUT BETWEEN ? AND ? GROUP BY HOTEL_ID" ;
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,  startDate);
			stmt.setString(2, endDate);
			stmt.setString(3,  startDate);
			stmt.setString(4, endDate);
			selectQueryRS = stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No Hotels Revenue");
			}else{
				System.out.println("Hotels Revenue");
				while (selectQueryRS.next()) {
					int hotelId = selectQueryRS.getInt("HOTEL_ID");
					int revenue = selectQueryRS.getInt("TOTAL_REVENUE");
					System.out.println(hotelId + " " + revenue);
				}
			}
		} catch (Exception e) {
			System.out.println("Wrong parameters");
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
