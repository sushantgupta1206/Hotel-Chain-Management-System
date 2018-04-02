package com.startup;

import com.dataconfig.ConfigHotel;

public class WolfInnStart {
	public static void main(String args[]) {
		ConfigHotel configHotel = new ConfigHotel();
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
			default:
				System.out.println("Invalid command.  Available options:");
				printHelp();
			}
		} else { // without arguments, just print out the resources being used.
			System.out.println("Invalid command ");
		}
	}

	private static void printHelp() {
		System.out.println("java -jar WolfInn.jar addhotel [<Id> <Phone> <Name> <Address> <City> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showhotel [<For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar showhotels [<DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar updatehotel [<Id> <Phone> <Name> <Address> <City> <For which Hotel Id> <DBflag=2 for MariaDB>]");
		System.out.println("java -jar WolfInn.jar deletehotel [<For which Hotel Id> <DBflag=2 for MariaDB>]");
	}
}
