
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

/*
 * This class is the application starting point. Based on the command a switch case is executed. 
 * Depending on the operations Config class and DAO class is called.
 * Config class (i.e. assign user input to java variables.) 
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
					e.printStackTrace();
				}
				break;
			case "showstaffs":
				configHotel.showStaffs(args);
				break;
			case "deletestaff":
				configHotel.deleteStaff(args);
				break;
			case "showAllRoomCat":
				configHotel.showAllRoomCat(args);
				break;
			case "addManager":
				configHotel.addManager(args);
				break;
			case "addcustomer":
				try {
					configHotel.addCustomer(args);
				} catch (ParseException e) {
					System.out.println("DATE NOT CORRECT FORMAT");
					e.printStackTrace();
				}
				break;
			case "updatecustomer":
				try {
					configHotel.updateCustomer(args);
				} catch (ParseException e) {
					System.out.println("DATE NOT CORRECT FORMAT");
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
			case "checkRoomsAvailability":
				configHotel.checkRoomsAvailability(args);
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
			case "releaseroom":
				configHotel.releaseRoom(args);
				break;	
			case "providesservices":
				serviceRecords.addProvidesServices(args);
				break;
			case "updateservices":
				serviceRecords.updateProvidesServices(args);
				break;
			case "checkout":
				billingAccounts.checkOut(args);
				break;
			case "updatebill":
				billingAccounts.updatePayBill(args);
				break;
			case "deletebill":
				billingAccounts.deletePayBill(args);
				break;
			case "checkitemizedtotalamount":
				billingAccounts.checkItemizedTotalAmount(args);
				break;
			case "checktotalamountduringstay":
				billingAccounts.checkTotalAmountDuringStay(args);
				break;	
			case "occupancybyhotel":
				configReports.occHotel(args);
				break;
			case "occupancybycity":
				configReports.occCity(args);
				break;	
			case "occupancybyroomtype":
				configReports.occRoom(args);
				break;	
			case "occupancybydaterange":
				configReports.occDateRng(args);
				break;	
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
		System.out.println("java -jar WolfInn.jar addhotel [<Id> <3 DIGIT Phone> <Name> <Address> <City> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showhotel [<For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showhotels [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatehotel [<Id> <Phone> <Name> <Address> <City> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletehotel [<For which Hotel Id> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar addroom [<ROOM_NO> <HOTEL_ID> <MAX_OCCUPANCY> <NIGHTLY_RATE> <ROOM_CATEGORY> <AVAILABILITY> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updateroom [<ROOM_NO> <HOTEL_ID> <MAX_OCCUPANCY> <NIGHTLY_RATE> <AVAILABILITY> <For which Room Num> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showrooms [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deleteroom [<For which Room Num> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar addstaff [<STAFF_ID> <PHONE> <NAME> <ADDRESS> <DOB> <DEPARTMENT> <TITLE> <AGE> <HOTEL_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatestaff [<STAFF_ID> <PHONE> <NAME> <ADDRESS> <DOB> <DEPARTMENT> <TITLE> <AGE> <For which STAFF_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showstaffs [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletestaff [<For which Staff Id> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar addcustomer [<CUSTOMER_ID> <PHONE> <NAME> <EMAIL_ADDRESS> <DOB> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatecustomer [<CUSTOMER_ID> <PHONE> <NAME> <EMAIL_ADDRESS> <DOB> <For which CUSTOMER_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showcustomers [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletecustomer [<For which CUSTOMER_ID> <DBflag=2 for MariaDB>]");
		
		System.out.println("java -jar WolfInn.jar providesservices <For which CUSTOMER_ID> <For which STAFF_ID> <For which SERVICE_ID> <DBflag=2 for MariaDB>");
		System.out.println("java -jar WolfInn.jar updateservices <SERVICE_ID> <STAFF_ID> <For WHICH SERVICE TIME> <For which CUSTOMER_ID>  <DBflag=2 for MariaDB>");
		
		System.out.println("java -jar WolfInn.jar checkRoomAvailability [<ROOM_NO> <HOTEL_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar checkRoomTypeAvailability [<ROOM_TYPE_NAME> <HOTEL_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar setRoomAvailability [<AVAILABILITY> <ROOM_NO> <HOTEL_ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar assignroom [<STAFF_ID> <CUSTOMER_ID> <CHECK_IN> <CHECK_OUT> <NO_OF_GUESTS> <HOTEL_ID> <ROOM_NO> <DBflag=2 for MariaDB>]");

		System.out.println("java -jar WolfInn.jar checkout [<custID>, <checkIn>, <payMethodID>, <billingAddress>, <paySSN>, <roomNo>], <hotelID>, <dbFlag>");
		System.out.println("java -jar WolfInn.jar updatebill [<billId>, <custID>, <checkIn>, <payMethodID>, <billingAddress>, <dbFlag>]");
		System.out.println("java -jar WolfInn.jar deletebill [<billId> <dbFlag>]");
		System.out.println("java -jar WolfInn.jar checkitemizedtotalamountint [<customerId> <checkIn> <dbFlag>]");
		System.out.println("java -jar WolfInn.jar checktotalamountduringstay [<customerId> <checkIn> <dbFlag>]");
		
		System.out.println("java -jar WolfInn.jar occupancybyhotel [<date of report, use time 23:59:59> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar occupancybycity [<date of report, use time 23:59:59> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar occupancybyroomtype [<date of report, use time 23:59:59> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar occupancybydaterange [<report start date> <report end date> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar staffinfobyrole [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar staffpercustomer [<CHECKIN> <CUSTOMER ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar getrevenue [<report start date> <report end date> <DBflag=2 for MariaDB>]");
	}
}
