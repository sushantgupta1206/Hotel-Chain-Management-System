
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
				//java -jar WolfInn.jar addhotel 11 "919" "Hilton" "46 West" Raleigh 2
				configHotel.addHotel(args);
				break;
			case "showhotel":
				//java -jar WolfInn.jar showhotel 11 2
				configHotel.showHotel(args);
				break;
			case "showhotels":
				//java -jar WolfInn.jar showhotels 2
				configHotel.showHotels(args);
				break;
			case "updatehotel":
				//java -jar WolfInn.jar updatehotel 11 913 "Hilton" "46 West" Raleigh 11 2
				configHotel.updateHotel(args);
				break;
			case "deletehotel":
				//java -jar WolfInn.jar deletehotel 11 2
				configHotel.deleteHotel(args);
				break;

			case "addroom":
				//java -jar WolfInn.jar addroom 1172 11 3 140 1 2
				configHotel.addRoom(args);
				break;
			case "updateroom":
				//java -jar WolfInn.jar updateroom 11 11 4 110 0 11 11 2
				configHotel.updateRoom(args);
				break;
			case "showrooms":
				//java -jar WolfInn.jar showrooms 2
				configHotel.showRooms(args);
				break;
			case "deleteroom":
				//java -jar WolfInn.jar deleteroom 11 11 2
				configHotel.deleteRoom(args);
				break;
			
			case "addstaff":
				//java -jar WolfInn.jar addstaff 14 919 Kartik "Rasin St" 1983-04-02 "Catering" "Catering Staff"  21 11 2
				//java -jar WolfInn.jar addstaff 12 919 Kartik "Rasin St" 1983-04-02 "Management" "Manager"  21 11 2
				try {
					configHotel.addStaff(args);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case "updatestaff":
				//java -jar WolfInn.jar updatestaff 15 919 Kartik "Rasin St" 1983-04-02 "Management" "Manager" 21 14 2
				try {
					configHotel.updateStaff(args);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case "showstaffs":
				//java -jar WolfInn.jar showstaffs 2
				configHotel.showStaffs(args);
				break;
			case "deletestaff":
				//java -jar WolfInn.jar deletestaff 15 2
				configHotel.deleteStaff(args);
				break;
			case "showAllRoomCat":
				//java -jar WolfInn.jar showAllRoomCat 2
				configHotel.showAllRoomCat(args);
				break;
			case "addManager":
				configHotel.addManager(args);
				break;	

			case "addcustomer":
				try {
					//java -jar WolfInn.jar addcustomer 7 929 "Mark Ruby" "mruby@email.com" "1990-02-01" 2
					configHotel.addCustomer(args);
				} catch (ParseException e) {
					System.out.println("DATE NOT CORRECT FORMAT");
					e.printStackTrace();
				}
				break;
			case "updatecustomer":
				try {
					//java -jar WolfInn.jar updatecustomer 7 913 "Mark Ruby" "mruby@email.com" "1990-02-01" 7 2
					configHotel.updateCustomer(args);
				} catch (ParseException e) {
					System.out.println("DATE NOT CORRECT FORMAT");
					e.printStackTrace();
				}
				break;
			case "showcustomers":
				//java -jar WolfInn.jar showcustomers 2
				configHotel.showCustomers(args);
				break;
			case "deletecustomer":
				//java -jar WolfInn.jar deletecustomer 7 2
				configHotel.deleteCustomer(args);
				break;
				
			
			case "checkRoomAvailability":
				//java -jar WolfInn.jar checkRoomAvailability 11 11 2
				configHotel.checkRoomAvailability(args);
				break;
				
			case "checkRoomTypeAvailability":
				//java -jar WolfInn.jar checkRoomTypeAvailability "Economy" 11 2
				configHotel.checkRoomTypeAvailability(args);
				break;
				
			case "setRoomAvailability":
				configHotel.setRoomAvailability(args);
				break;
				
			case "assignroom":
				//java -jar WolfInn.jar assignroom 1001 101 "2018-05-10 15:17:00" "2018-05-11 15:17:00" 5 0001 01 2
				configHotel.assignRoom(args);
				break;
			case "releaseroom":
				//java -jar WolfInn.jar releaseroom 1001 101 
				configHotel.releaseRoom(args);
				break;	
				
			case "providesservices":
				//java -jar WolfInn.jar providesservices 7 5 1 2
				serviceRecords.addProvidesServices(args);
				break;
			case "updateservices":
				//java -jar WolfInn.jar updateservices 1 5 "2017-05-10 15:17:00" 7 2
				serviceRecords.updateProvidesServices(args);
				break;
			//java -jar WolfInn.jar addbill 6 1001 "2017-05-10 15:17:00" 2 "blah d" 2	
			case "addbill":
				billingAccounts.addBill(args);
				break;
			//java -jar WolfInn.jar updatebill 5 1001 '2017-05-10?' 2 "1234 blah st" 2
			case "updatebill":
				billingAccounts.updateBill(args);
				break;
			// java -jar WolfInn.jar deletebill 5 2
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
				// java -jar WolfInn.jar checktotalamount 5 2
				billingAccounts.checkTotalAmount(args);
				break;	
			case "checkitemizedtotalamount":
				// java -jar WolfInn.jar checkitemizedtotalamount 5 2
				billingAccounts.checkItemizedTotalAmount(args);
				break;
			case "checkoutcustomer":
				// java -jar WolfInn.jar checkoutcustomer 5 2
				billingAccounts.checkoutCustomer(args);
				break;
				
			
			case "occupancybyhotel":
				//java -jar WolfInn.jar  occupancybyhotel  "2017-05-10 23:59:59" "2017-05-13 09:59:59" 2
				//java -jar WolfInn.jar  occupancybyhotel  "2017-05-11" "2017-05-13" 2
				configReports.occHotel(args);
				break;
			case "occupancybycity":
				//java -jar WolfInn.jar  occupancybycity  "2017-05-10 23:59:59" "2017-05-13 09:59:59" 2
				//java -jar WolfInn.jar  occupancybycity  "2017-05-11" "2017-05-13" 2
				configReports.occCity(args);
				break;	
			case "occupancybyroomtype":
				//java -jar WolfInn.jar  occupancybyroomtype  "2017-05-10 23:59:59" "2017-05-13 09:59:59" 2
				//java -jar WolfInn.jar  occupancybyroomtype  "2017-05-11" "2017-05-13" 2
				configReports.occRoom(args);
				break;	
			case "occupancybydaterange":
				//java -jar WolfInn.jar  occupancybydaterange  "2017-05-10" "2017-05-13" 2
				configReports.occDateRng(args);
				break;	
			case "staffinfobyrole":
				//java -jar WolfInn.jar staffinfobyrole 2
				configReports.showStaff(args);
				break;
			case "staffpercustomer":
				//java -jar WolfInn.jar staffpercustomer "2017-05-10 16:10:00" 1002 2
				configReports.custStaff(args);
				break;
			case "getrevenue":
				//java -jar WolfInn.jar getrevenue "2017-01-10 16:10:00" "2018-01-10 16:10:00" 2
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
		System.out.println("java -jar WolfInn.jar addManager [<HOTEL_ID> <MANAGER_ID> <DBflag=2 for MariaDB>]");
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

		System.out.println("java -jar WolfInn.jar occupancybyhotel  [<CHECKIN> <CHECKOUT> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar occupancybycity  [<CHECKIN> <CHECKOUT> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar occupancybyroomtype  [<CHECKIN> <CHECKOUT> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar occupancybydaterange  [<CHECKIN> <CHECKOUT> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar staffinfobyrole [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar staffpercustomer [<CHECKIN> <CUSTOMER ID> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar getrevenue [<CHECKIN> <CHECKOUT> <DBflag=2 for MariaDB>]");
	}
}
