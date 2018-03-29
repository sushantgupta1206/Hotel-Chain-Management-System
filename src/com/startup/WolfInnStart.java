package com.startup;

import com.databaserepo.InformationProcessingDAO;
import com.dataobject.Hotel;

public class WolfInnStart {
	public static void main(String args[]) {
		WolfInnStart innStart = new WolfInnStart();
		if (args.length > 0) {
			switch (args[0]) {
			case "addhotel":
				innStart.addHotel(args);
				break;

			default:
				System.out.println("Invalid command.  Available options:");
				printHelp();
			}
		} else { // without arguments, just print out the resources being used.
			System.out.println("Invalid command ");
		}
	}

	private static void printHelp() {
		System.out.println("java -jar WolfInn.jar addhotel [<Id> <Phone> <Name> <Address> <City> <DBflag>]");
	}
	
	public void addHotel(String args[]){
		Hotel hotelData = new Hotel();
		hotelData.setId(Integer.parseInt(args[1]));
		hotelData.setPhone(Integer.parseInt(args[2]));
		hotelData.setName(args[3]);
		hotelData.setAddress(args[4]);
		hotelData.setCity(args[5]);
		int dbFlag = Integer.parseInt(args[6]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.addHotelDB(hotelData, dbFlag);
		
	}
}
