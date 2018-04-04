package com.startup;

import java.text.ParseException;

import com.dataconfig.ConfigInformationProcessingData;

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
