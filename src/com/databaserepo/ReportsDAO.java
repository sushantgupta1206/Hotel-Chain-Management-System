package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.dataobject.Staff;
import java.util.*;

/**
 * @author Leah Jackman
 *
 */

/*
 * Contains methods for each reports operation.
 */
public class ReportsDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	/**Report occupancy and percent occupancy by hotel.
	 * @param dbFlag
	 * @return
	 */
	public void occHotel(String dateOfReport, int dbFlag){		
/*
 * note: check in and check out should be the same day/time
 * inputs: date
 * outputs: string of hotel IDs, occupancy and percent occupancy
 */
		String sourceMethod = "occHotel";
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = " SELECT HOTELS.HOTEL_ID,  "
					+ "SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY, "
					+ "SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY "
					+"FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) ON CHECK_IN <= ? "
					+ "AND CHECK_OUT > ? AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND "
					+ "ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.HOTEL_ID";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setString(1,  dateOfReport);
			stmt.setString(2, dateOfReport);
			selectQueryRS = stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No list found");
			}else{
				System.out.println("List found");
				System.out.println("HOTEL_ID  OCCUPANCY  PERCENT_OCCUPANCY");
				while (selectQueryRS.next()) {
					//Hotel hotel = new Hotel();
					int hotelId = selectQueryRS.getInt("HOTEL_ID");
					int occupancy = selectQueryRS.getInt("OCCUPANCY");
					float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
					System.out.println(hotelId + "   		 " + occupancy + "    		 " + percent);
				}
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
	
	public void occCity(String dateOfReport, int dbFlag) {
				String sourceMethod = "occCity";
				Connection dbConn = null;
				ResultSet selectQueryRS = null;
				PreparedStatement stmt = null;
				try {
					dbConn = dbUtil.getConnection(dbFlag);
					String selectStatement = " SELECT HOTELS.CITY, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY, "
						 + "SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY " 
						 +  "FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) ON CHECK_IN <= ? " 
						 +  "AND CHECK_OUT > ? AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND "
						 +  "ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.CITY" ;
					stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					stmt.setString(1,  dateOfReport);
					stmt.setString(2, dateOfReport);
					selectQueryRS = stmt.executeQuery();
					if (!selectQueryRS.isBeforeFirst()) {
						System.out.println("No list found");
					}else{
						System.out.println("List found");
						System.out.println("CITY  		OCCUPANCY  		PERCENT_OCCUPANCY");
						while (selectQueryRS.next()) {
							String hotelCity = selectQueryRS.getString("CITY");
							int occupancy = selectQueryRS.getInt("OCCUPANCY");
							float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
							System.out.println(hotelCity + "    	" + occupancy + "    	" + percent);
						}
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
 * 
 */
	public void occRoom(String dateOfReport, int dbFlag) {
		String sourceMethod = "occRoom";
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = " SELECT ROOM_HAS.ROOM_CATEGORY_ID, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) "
				 +  "AS OCCUPANCY,SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) "
				 +  "AS PERCENT_OCCUPANCY FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) LEFT OUTER JOIN " 
				 +   DBConnectUtils.DBSCHEMA+".ROOM_HAS ON ROOMS.ROOM_NO = ROOM_HAS.ROOM_NO AND  ROOMS.HOTEL_ID = ROOM_HAS.HOTEL_ID "  
				 +  "ON CHECK_IN <= ? AND CHECK_OUT > ? AND " 
				 +  "ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY ROOM_HAS.ROOM_CATEGORY_ID";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,  dateOfReport);
			stmt.setString(2, dateOfReport);
			selectQueryRS = stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No list found");
			}else{
				System.out.println("List found");
				System.out.println("ROOM_CATEGORY_ID  OCCUPANCY  PERCENT_OCCUPANCY");
				while (selectQueryRS.next()) {
					int roomCatId = selectQueryRS.getInt("ROOM_CATEGORY_ID");
					int occupancy = selectQueryRS.getInt("OCCUPANCY");
					float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
					System.out.println(roomCatId + "    	 	" + occupancy + "	 		 " + percent);
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
	
	public void occDateRng(String firstDate, String lastDate, int dbFlag) {
		String sourceMethod = "occDateRng";
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		PreparedStatement stmt = null;
		try {
			java.util.Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
			System.out.println(date1);
			java.util.Date date2= new SimpleDateFormat("yyyy-MM-dd").parse(lastDate);
			System.out.println(date2);
			
			Calendar start = Calendar.getInstance();
			start.setTime(date1);
			Calendar end = Calendar.getInstance();
			end.setTime(date2);

			for (Date date = start.getTime(); start.before(end) || start.equals(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
				java.sql.Date iDate= convertUtilToSql(date);
			    
			    dbConn = dbUtil.getConnection(dbFlag);
					    
				String selectStatement = " SELECT ? AS OCCUPANCY_DATE, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY,SUM( CASE WHEN "
					    + " ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY FROM  "+DBConnectUtils.DBSCHEMA+".ASSIGNS RIGHT "
					    		+ "OUTER JOIN ( "+DBConnectUtils.DBSCHEMA+".HOTELS NATURAL JOIN  "+DBConnectUtils.DBSCHEMA+".ROOMS) ON "
			    		+ " CHECK_IN <= ? AND CHECK_OUT > ? AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP "
			    		+ " BY OCCUPANCY_DATE";
				stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setDate(1,  iDate);
				stmt.setDate(2,  iDate);
				stmt.setDate(3,  iDate);
				selectQueryRS = stmt.executeQuery();
				if (!selectQueryRS.isBeforeFirst()) {
					System.out.println("No list found");
				}else{
					System.out.println("List found");
					System.out.println("OCCUPANCY_DATE  OCCUPANCY  PERCENT_OCCUPANCY");
					while (selectQueryRS.next()) {
						String roomCatId = selectQueryRS.getString("OCCUPANCY_DATE");
						int occupancy = selectQueryRS.getInt("OCCUPANCY");
						float percent = selectQueryRS.getFloat("PERCENT_OCCUPANCY");
						System.out.println(roomCatId + "    	 	" + occupancy + "	 		 " + percent);
					}
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

	/**Show all staffs
	 * @param dbFlag
	 */
	public void showStaff(int dbFlag) {
		String sourceMethod = "showStaff";
		List<Staff> details = new ArrayList<Staff>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".STAFF ORDER BY TITLE";
			selectQueryRS = stmt.executeQuery(selectStatement);
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No staff found");
			}else{
				while (selectQueryRS.next()) {
					Staff detail=new Staff();
					detail.setStaffId(selectQueryRS.getInt("STAFF_ID"));
					detail.setPhone(selectQueryRS.getString("PHONE"));
					detail.setName(selectQueryRS.getString("NAME"));
					detail.setAddress(selectQueryRS.getString("ADDRESS"));
					detail.setDob(selectQueryRS.getDate("DOB"));
					detail.setDepartment(selectQueryRS.getString("DEPARTMENT"));
					detail.setTitle(selectQueryRS.getString("TITLE"));
					detail.setAge(selectQueryRS.getInt("AGE"));
					details.add(detail);
					System.out.println(detail);
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
				System.out.println("STAFF_ID");
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
			
			//SELECT HOTEL_ID, SUM(BILLS.AMOUNT)AS REVENUE FROM BILLS NATURAL JOIN PAYS NATURAL JOIN ASSIGNS WHERE BILLS.BILL_TIMESTAMP BETWEEN '2017-05-10%' AND '2018-05-05%' GROUP BY HOTEL_ID;

			String selectStatement = " SELECT HOTEL_ID, SUM(BILLS.AMOUNT)AS REVENUE FROM "+DBConnectUtils.DBSCHEMA+".BILLS " 
						 + "NATURAL JOIN "+DBConnectUtils.DBSCHEMA+".PAYS NATURAL JOIN "+DBConnectUtils.DBSCHEMA+".ASSIGNS WHERE BILLS.BILL_TIMESTAMP BETWEEN ? AND ? " 
						 + "GROUP BY HOTEL_ID" ;
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1,  startDate);
			stmt.setString(2, endDate);
			selectQueryRS = stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No Hotels Revenue");
			}else{
				System.out.println("Hotels Revenue");
				while (selectQueryRS.next()) {
					int hotelId = selectQueryRS.getInt("HOTEL_ID");
					int revenue = selectQueryRS.getInt("REVENUE");
					System.out.println(hotelId + "		 " + revenue);
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

	
	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
	}
	
}
