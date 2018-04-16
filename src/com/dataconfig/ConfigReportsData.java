
package com.dataconfig;

import com.databaserepo.ReportsDAO;

/**
 * @author Leah Jackman
 *
 */

/*
 * This class is an interface that assigns user input to java variables. 
   This class also calls DAO for further processing of the user requests.
   DAO class (i.e. Process the user requests and supports applications core business logic operation).
   This class takes information to provide reports. 
 */
public class ConfigReportsData {
	
	/*
	 * inputs: date
	 * outputs: string of hotel IDs, occupancy and percent occupancy
	 * 
	 */
	public void occHotel(String args[]) {
		if (args.length ==4) {	
			String checkInDate = args[1];
			String checkOutDate = args[2];
			int dbFlag = Integer.parseInt(args[3]);
			ReportsDAO reportsDAO = new ReportsDAO();
			reportsDAO.occHotel(checkInDate, checkOutDate, dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}
	
	/* 
	 *   input: date
	 *   output: string of cities, occupancy and percent occupancy
	 *   
	 */
	public void occCity(String args[]){
		if (args.length ==4) {	
			String checkInDate = args[1];
			String checkOutDate = args[2];
			int dbFlag = Integer.parseInt(args[3]);
			ReportsDAO reportsDAO = new ReportsDAO();
			reportsDAO.occCity(checkInDate, checkOutDate, dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}

	/*
	 *  input: date
	 *  output: string of room category IDs, occupancy and percent occupancy
	 */
	public void occRoom(String args[]){
		if (args.length ==4) {	
			String checkInDate = args[1];
			String checkOutDate = args[2];
			int dbFlag = Integer.parseInt(args[3]);
			ReportsDAO reportsDAO = new ReportsDAO();
			reportsDAO.occRoom(checkInDate, checkOutDate, dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}
	
	public void occDateRng(String args[]){
/*
 *  input: begin date, end date
 *  output: string of dates, occupancy and percent occupancy
 *  
 *  NOTE! Will need to execute this query for each day in date range and take union of results.
 *  So, will need to build query statement in application based on user input.
 */
		if (args.length ==4) {	
			String firstDate = args[1];
			String lastDate = args[2];
			int dbFlag = Integer.parseInt(args[3]);
			ReportsDAO reportsDAO=new ReportsDAO();
			reportsDAO.occDateRng(firstDate, lastDate, dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}

	/*
	 * input: none (the result of some event ie. button click)
	 * output: string of all staff information by title
	 */
	public void showStaff(String[] args) {
		if (args.length ==2) {	
			int dbFlag = Integer.parseInt(args[1]);
			ReportsDAO reportsDAO=new ReportsDAO();
			reportsDAO.showStaff(dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}

/*
 * input: begin date, end date
 * output: string of hotel ID, and total income
 * 
 * NOTE! if application does not have data format consistent with that in query, will need to 
 * modify user input.
 */
	public void revHotel(String[] args) {
		if (args.length ==4) {	
			String startDate = args[1];
			String endDate = args[2];
			int dbFlag = Integer.parseInt(args[3]);
			ReportsDAO reportsDAO = new ReportsDAO();
			reportsDAO.revHotel(startDate, endDate, dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}

	/*
	 * 
	 * input: customer ID, check in date and time
	 * output: string of each staff member (service and front desk) who served customer
	 */
	public void custStaff(String[] args) {
		if (args.length ==4) {
			String checkInDate = args[1];
			int custId = Integer.parseInt(args[2]);
			int dbFlag = Integer.parseInt(args[3]);
			ReportsDAO reportsDAO = new ReportsDAO();
			reportsDAO.custStaff(checkInDate, custId, dbFlag);
		}else{
			System.out.println("Parameters mismatch");
		}
	}

}
