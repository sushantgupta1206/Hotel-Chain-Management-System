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
	public int occHotel(Date date, int dbFlag){		
/*
 * note: check in and check out should be the same day/time
 * inputs: date
 * outputs: string of hotel IDs, occupancy and percent occupancy
 */
		String sourceMethod = "occHotel";
		List<> hotelOccupancy = new ArrayList<>();
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String getOccHotelQuery = " SELECT " + DBConnectUtils.DBSCHEMA + ".HOTELS.HOTEL_ID, "
					+ "SUM(CASE WHEN " + DBConnectUtils.DBSCHEMA + ".ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY, "
					+ "SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY"
					+"FROM ASSIGNS RIGHT OUTER JOIN (HOTELS NATURAL JOIN ROOMS) ON CHECK_IN <= '?' "
					+ "AND CHECK_OUT >= '?' AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND"
					+ "ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.HOTEL_ID;";
			selectQueryRS = stmt.executeQuery(getOccHotelQuery);
			while (selectQueryRS.next()) {
				Hotel hotel = new Hotel();
				hotel.setId(selectQueryRS.getInt("HOTEL_ID"));
				hotel.setPhone(selectQueryRS.getInt("PHONE"));
				hotel.setName(selectQueryRS.getString("NAME"));
				hotel.setAddress(selectQueryRS.getString("ADDRESS"));
				hotel.setCity(selectQueryRS.getString("CITY"));
				hotelDetails.add(hotel);
				System.out.println(hotel);
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