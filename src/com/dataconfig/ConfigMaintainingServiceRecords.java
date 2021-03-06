package com.dataconfig;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.databaserepo.MaintainingServiceRecordsDAO;

/*
 * This class is an interface that assigns user input to java variables. 
   This class also calls DAO for further processing of the user requests.
   DAO class (i.e. Process the user requests and supports applications core business logic operation).
   This class takes basic information to maintain services and its associated entities. 
 */

public class ConfigMaintainingServiceRecords {
	
	private static String convertDateToCurrent() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }
	
	public static void main(String arg[]){
		convertDateToCurrent();
	}
	
    /*
     * input: Customer ID, Staff ID, Service ID, dbflag
     * output: N/A (Adds a new service type)
    */    
	
	public void addProvidesServices(String[] args) {
		int customerId = Integer.parseInt(args[1]);
		int staffId = Integer.parseInt(args[2]);
		int serviceID = Integer.parseInt(args[3]);
		int dbFlag = Integer.parseInt(args[4]);      
        if (customerId > 0 && staffId > 0 && serviceID > 0 && dbFlag > 0) {
            MaintainingServiceRecordsDAO maintainingServiceRecordsDAO = new MaintainingServiceRecordsDAO();
            maintainingServiceRecordsDAO.addProvidesServices(customerId, staffId, serviceID, convertDateToCurrent(), dbFlag);
        }
        else {
            System.out.println("Not valid data");
        }
	}
	
    /*
     * input: Service ID, Staff ID, Service Time, Customer ID, dbflag
     * output: N/A (Updates a currently provided service)  
    */ 
	
	public void updateProvidesServices(String[] args) {
		int serviceID = Integer.parseInt(args[1]);
		int staffId = Integer.parseInt(args[2]);
		String serviceTime = args[3];
		int customerId = Integer.parseInt(args[4]);
		int dbFlag = Integer.parseInt(args[5]);
        if (serviceID > 0 && staffId > 0 && serviceTime != null && customerId > 0 && dbFlag > 0) {
            MaintainingServiceRecordsDAO maintainingServiceRecordsDAO = new MaintainingServiceRecordsDAO();
            maintainingServiceRecordsDAO.updateProvidesServices(serviceID, staffId, serviceTime, customerId, dbFlag);
        }
        else {
            System.out.println("Not valid data");
        }
	}	
}
