package com.startup;

import java.text.ParseException;

import com.dataconfig.ConfigBillingAccounts;
import com.dataconfig.ConfigInformationProcessingData;
import com.dataconfig.ConfigMaintainingServiceRecords;
import com.dataconfig.ConfigReportsData;

/**
 * @author Kartik  Shah
 *
 */
public class WolfInnStart {
	/**Accept arguments from user
	 * @param args
	 */
	public static void main(String args[]) {
		ConfigInformationProcessingData configHotel = new ConfigInformationProcessingData();
		ConfigMaintainingServiceRecords serviceRecords =new ConfigMaintainingServiceRecords();
		ConfigBillingAccounts billingAccounts = new ConfigBillingAccounts();
		ConfigReportsData configReports = new ConfigReportsData();
		if (args.length > 0) {
			switch (args[0]) {
			case "addhotel":
				configHotel.addHotel(args);
				break;
			case "showhotel":
				configHotel.showHotel(args);
				break;
			case "showhotels":
				configHotel.showHotels(args);
				break;
			case "updatehotel":
				configHotel.updateHotel(args);
				break;
			case "deletehotel":
				configHotel.deleteHotel(args);
				break;

			case "addroom":
				configHotel.addRoom(args);
				break;
			case "updateroom":
				configHotel.updateRoom(args);
				break;
			case "showrooms":
				configHotel.showRooms(args);
				break;
			case "deleteroom":
				configHotel.deleteRoom(args);
				break;
			
			case "addstaff":
				try {
					configHotel.addStaff(args);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case "updatestaff":
				try {
					configHotel.updateStaff(args);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "showstaffs":
				configHotel.showStaffs(args);
				break;
			case "deletestaff":
				configHotel.deleteStaff(args);
				break;
				
				
			case "addcustomer":
				try {
					configHotel.addCustomer(args);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case "updatecustomer":
				try {
					configHotel.updateCustomer(args);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "showcustomers":
				configHotel.showCustomers(args);
				break;
			case "deletecustomer":
				configHotel.deleteCustomer(args);
				break;
				
			
			case "checkRoomAvailability":
				configHotel.checkRoomAvailability(args);
				break;
				
			case "checkRoomTypeAvailability":
				configHotel.checkRoomTypeAvailability(args);
				break;
				
			case "setRoomAvailability":
				configHotel.setRoomAvailability(args);
				break;
				
			case "assignroom":
				configHotel.assignRoom(args);
				break;
				
				
			case "providesservices":
				serviceRecords.addProvidesServices(args);
				break;
			case "updateservices":
				serviceRecords.updateProvidesServices(args);
				break;

				
			case "addbill":
				billingAccounts.addBill(args);
				break;
			case "updatebill":
				billingAccounts.updateBill(args);
				break;
			case "deletebill":
				billingAccounts.deleteBill(args);
				break;
			case "addpay":
				billingAccounts.addPay(args);
				break;
			case "addgenerate":
				billingAccounts.addGenerate(args);
				break;
			case "checktotalamount":
				billingAccounts.checkTotalAmount(args);
				break;	
			case "checkitemizedtotalamount":
				billingAccounts.checkItemizedTotalAmount(args);
				break;
			case "occupancybyhotel":
				configReports.occHotel(args);
				break;
			case "occupancybycity":
				configReports.occCity(args);
				break;	
			case "occupancybyroom":
				configReports.occRoom(args);
				break;	
//			case "occupancybydaterange":
//				configReports.occRange(args);
//				break;	
			case "staffinfobyrole":
				configReports.showStaff(args);
				break;
			case "staffpercustomer":
				configReports.custStaff(args);
				break;
			case "getrevenue":
				configReports.revHotel(args);
				break;
			default:
				System.out.println("Invalid command.  Available options:");
				printHelp();
			}
		} else { // without arguments, just print out the resources being used.
			System.out.println("Invalid command ");
		}
	}

	/**
	 * Help to execute commands
	 */
	private static void printHelp() {
		System.out.println("java -jar WolfInn.jar addhotel [<Id> <Phone> <Name> <Address> <City> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showhotel [<For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showhotels [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatehotel [<Id> <Phone> <Name> <Address> <City> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletehotel [<For which Hotel Id> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar addroom [<ROOM_NO> <HOTEL_ID> <MAX_OCCUPANCY> <NIGHTLY_RATE> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updateroom [<ROOM_NO> <HOTEL_ID> <MAX_OCCUPANCY> <NIGHTLY_RATE> <AVAILABILITY> <For which Room Num> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showrooms [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deleteroom [<For which Room Num> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar addstaff [<STAFF_ID> <PHONE> <NAME> <ADDRESS> <DOB> <DEPARTMENT> <TITLE> <AGE> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatestaff [<STAFF_ID> <PHONE> <NAME> <ADDRESS> <DOB> <DEPARTMENT> <TITLE> <AGE> <For which STAFF_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showstaffs [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletestaff [<For which Staff Id> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar addcustomer [<CUSTOMER_ID> <PHONE> <NAME> <EMAIL_ADDRESS> <DOB> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatecustomer [<CUSTOMER_ID> <PHONE> <NAME> <EMAIL_ADDRESS> <DOB> <For which CUSTOMER_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showcustomers [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletecustomer [<For which CUSTOMER_ID> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar checkRoomAvailability [<ROOM_NO> <HOTEL_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar checkRoomTypeAvailability [<ROOM_TYPE_NAME> <HOTEL_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar setRoomAvailability [<AVAILABILITY> <ROOM_NO> <HOTEL_ID> <DBflag=2 for MariaDB>]");
	}
}
