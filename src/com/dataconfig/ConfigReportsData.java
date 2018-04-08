
package com.dataconfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.databaserepo.InformationProcessingDAO;
import com.dataobject.Customer;
import com.dataobject.Hotel;
import com.dataobject.Room;
import com.dataobject.Staff;


/**
 * @author Leah Jackman
 *
 */

public class ConfigReportsData {
	
	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	public void occHotel(String args[]) {
/*
 * SELECT HOTELS.HOTEL_ID, SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY,
 * SUM(CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY 
 * FROM ASSIGNS RIGHT OUTER JOIN (HOTELS NATURAL JOIN ROOMS) ON CHECK_IN <= '2013-09-15 23:59:59' 
 * AND CHECK_OUT >= '2013-09-15 23:59:59' AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND 
 * ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.HOTEL_ID;
 * 
 * inputs: date
 * outputs: string of hotel IDs, occupancy and percent occupancy
 * 
 * when to use "[]" for String/args?
 */
		Date date = new Date();
		int dbFlag = Integer.parseInt(args[2]);
		ReportsDAO reportsDAO = new ReportsDAO();
		ReportsDAO.occHotel(date, dbFlag);
	}
	
	public void occCity(String args[]){
/*
 * SELECT HOTELS.CITY, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END) AS OCCUPANCY,
 * SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) AS PERCENT_OCCUPANCY
 *   FROM ASSIGNS RIGHT OUTER JOIN (HOTELS NATURAL JOIN ROOMS) ON CHECK_IN <= '2013-09-15 23:59:59' 
 *   AND CHECK_OUT >= '2013-09-15 23:59:59' AND ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND 
 *   ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY HOTELS.CITY;
 *   
 *   input: date
 *   output: string of cities, occupancy and percent occupancy
 *   
 *   does getCity need to have an argument?
 */
		Date date = new Date();
		int dbFlag = Integer.parseInt(args[1]);
		ReportsDAO reportsDAO=new ReportsDAO();
		ReportsDAO.occCity(dbFlag);
	}

	public void occRoomCat(String args[]){
/*
 * SELECT ROOM_HAS.ROOM_CATEGORY_ID, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)
 *  AS OCCUPANCY,SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) 
 *  AS PERCENT_OCCUPANCY FROM ASSIGNS RIGHT OUTER JOIN (HOTELS NATURAL JOIN ROOMS) LEFT OUTER JOIN 
 *  ROOM_HAS ON ROOMS.ROOM_NO = ROOM_HAS.ROOM_NO AND  ROOMS.HOTEL_ID = ROOM_HAS.HOTEL_ID  
 *  ON CHECK_IN <= '2013-09-15 23:59:59' AND CHECK_OUT >= '2013-09-15 23:59:59' AND 
 *  ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY ROOM_HAS.ROOM_CATEGORY_ID;
 *  
 *  input: date
 *  output: string of room category IDs, occupancy and percent occupancy
 *  
 *  
 */
		Date date = new Date();
		int dbFlag = Integer.parseInt(args[1]);
		ReportsDAO reportsDAO=new ReportsDAO();
		ReportsDAO.occCity(dbFlag);
	}
	
	public void occDateRng(String args[]){
/*
 * SELECT '9/15/13' AS OCCUPANCY_DATE, SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)
 *  AS OCCUPANCY,SUM( CASE WHEN ASSIGNS.ROOM_NO = ROOMS.ROOM_NO THEN 1 ELSE 0 END)/ COUNT(HOTELS.HOTEL_ID) 
 *  AS PERCENT_OCCUPANCY FROM ASSIGNS RIGHT OUTER JOIN (HOTELS NATURAL JOIN ROOMS) ON 
 *  CHECK_IN <= '2013-09-15 23:59:59' AND CHECK_OUT >= '2013-09-15 23:59:59' AND 
 *  ASSIGNS.HOTEL_ID = HOTELS.HOTEL_ID AND ASSIGNS.ROOM_NO = ROOMS.ROOM_NO GROUP BY OCCUPANCY_DATE;
 *  
 *  input: begin date, end date
 *  output: string of dates, occupancy and percent occupancy
 *  
 *  NOTE! Will need to execute this query for each day in date range and take union of results.
 *  So, will need to build query statement in application based on user input.
 */
		Date startDate = new Date();
		Date endDate = new Date();
		int dbFlag = Integer.parseInt(args[1]);
		ReportsDAO reportsDAO=new ReportsDAO();
		ReportsDAO.occDateRng(dbFlag);
	}

	public void showStaff(String[] args) {
/*
 * SELECT * FROM STAFF ORDER BY TITLE;
 * 
 * input: none (the result of some event ie. button click)
 * output: string of all staff information by title
 */
		int dbFlag = Integer.parseInt(args[1]);
		ReportsDAO reportsDAO=new ReportsDAO();
		ReportsDAO.showStaff(dbFlag);
	}
/*
 * SELECT HOTEL_ID, NIGHTLY_RATE* DATEDIFF(CHECK_OUT, CHECK_IN) AS TOTAL_REVENUE FROM ASSIGNS 
 * NATURAL JOIN ROOMS WHERE ASSIGNS.CHECK_IN BETWEEN '2013-10-10 00:00:00' AND '2018-03-16 00:00:00' 
 * AND ASSIGNS.CHECK_OUT BETWEEN '2013-10-10 00:00:00' AND '2018-03-16 00:00:00' GROUP BY HOTEL_ID ;
 * 
 * input: begin date, end date
 * output: string of hotel ID, and total income
 * 
 * NOTE! if application does not have data format consistent with that in query, will need to 
 * modify user input.
 */
	public void revHotel(String[] args) {
		Date startDate = new Date();
		Date endDate = new Date();
		int dbFlag = Integer.parseInt(args[1]);
		ReportsDAO reportsDAO=new ReportsDAO();
		ReportsDAO.revHotel(dbFlag);
	}

	public void custStaff(String[] args) {
/*
 * SELECT ASSIGNS.STAFF_ID FROM ASSIGNS JOIN PROVIDES ON ASSIGNS.CUSTOMER_ID = PROVIDES.CUSTOMER_ID 
 * AND PROVIDES.TIMESTATE BETWEEN ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT WHERE PROVIDES.CUSTOMER_ID = 4 
 * AND ASSIGNS.CHECK_IN = '2017-09-15 02:15:00' UNION SELECT PROVIDES.STAFF_ID FROM ASSIGNS JOIN 
 * PROVIDES ON ASSIGNS.CUSTOMER_ID = PROVIDES.CUSTOMER_ID AND PROVIDES.TIMESTATE BETWEEN 
 * ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT WHERE PROVIDES.CUSTOMER_ID = ? AND 
 * ASSIGNS.CHECK_IN = '2017-05-11 02:15:00';
 * 
 * input: customer ID, check in date and time
 * output: string of each staff member (service and front desk) who served customer

 */
		Int customerID = new Date();
		Date checkIn = new Date();
		int dbFlag = Integer.parseInt(args[1]);
		ReportsDAO reportsDAO=new ReportsDAO();
		ReportsDAO.custStaff(dbFlag);
		
	}

	
}
