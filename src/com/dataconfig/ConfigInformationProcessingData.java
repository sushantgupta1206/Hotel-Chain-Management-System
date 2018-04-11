package com.dataconfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.databaserepo.InformationProcessingDAO;
import com.dataobject.Customer;
import com.dataobject.Hotel;
import com.dataobject.Room;
import com.dataobject.Staff;
import com.dataobject.Assigns;

/**
 * @author Kartik Shah
 *
 */
public class ConfigInformationProcessingData {
	
	String regexEmail = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
	
	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
        /*
            input: Hotel ID, Hotel Phone, Hotel Name, Hotel Address, Hotel City, Hotel to be updated
            output: N/A (Updates an existing hotel)
        */	
	public void updateHotel(String[] args) {
		int hotelId = Integer.parseInt(args[1]);
		String phone = args[2];
		String name = args[3];
		String address = args[4];
		String city = args[5];
		int oldHotelId=Integer.parseInt(args[6]);
		int dbFlag = Integer.parseInt(args[7]);
				
		
		if(hotelId > 0 && phone.length() >0 && phone != null && name != null && name.length() <= 50 && oldHotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.updateHotel(hotelId, phone, name, address, city, oldHotelId, dbFlag);
		}else{
			System.out.println("Not valid data");
		}
	}
	
        /*
            input: N/A
            output: Display all existing hotels
        */        
	public void showHotels(String args[]){
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showHotels(dbFlag);
	}

        /*
            input: Hotel ID
            output: Show information on specific hotel
        */        
	public void showHotel(String args[]){
		int hotelId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		if(hotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.showHotel(hotelId,dbFlag);	
		}else{
			System.out.println("Not valid data");
		}
	}
	
        /*
            input: Hotel ID, Hotel Phone, Hotel Name, Hotel Address, Hotel City
            output: N/A (Adds a new hotel to table)
        */        
	public void addHotel(String args[]){
		int hotelId = Integer.parseInt(args[1]);
		String phone = args[2];
		String name = args[3];
		String address = args[4];
		String city = args[5];
		int dbFlag = Integer.parseInt(args[6]);
		if(hotelId > 0 && phone.length() > 1 && phone != null && name != null && name.length() <= 50 && address != null && address.length() <= 50 && city != null && city.length() <= 20){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.addHotel(hotelId, phone, name, address, city, dbFlag);	
		}else{
			System.out.println("Not valid data");
		}		
	}

        /*
            input: Hotel ID
            output: N/A (Deletes a specific hotel)
        */          
	public void deleteHotel(String[] args) {
		int hotelId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		
		if(hotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.deleteHotel(hotelId, dbFlag);
		}else{
			System.out.println("Not valid data");
		}
	}

        /*
            input: Room Number, Hotel ID, Max Occupancy, Nightly Rate
            output: N/A (Adds a new room to table)
        */          
	public void addRoom(String[] args) {
		int roomNo=Integer.parseInt(args[1]);
		int hotelId=Integer.parseInt(args[2]);
		int maxOccu=Integer.parseInt(args[3]); 
		int nightRate=Integer.parseInt(args[4]);
		int dbFlag = Integer.parseInt(args[5]);
		
		if(roomNo > 0 && hotelId > 0 && maxOccu > 0 && nightRate >= 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.addRoom(roomNo, hotelId, maxOccu, nightRate, dbFlag);
		}else{
			System.out.println("Not valid data");
		}
	}

        /*
            input: Room Number, Hotel ID, Max Occupancy, Nightly Rate, Availability, Number of room to be updated, ID of hotel of room to be updated
            output: N/A (Updates a specific room's information)
        */         
	public void updateRoom(String[] args) {
		int roomNo = Integer.parseInt(args[1]);
		int hotelId = Integer.parseInt(args[2]);
		int maxOccu = Integer.parseInt(args[3]);
		int nightRate = Integer.parseInt(args[4]);
		int setAvailability = Integer.parseInt(args[5]);
		int oldRoomNum=Integer.parseInt(args[6]);
		int oldHotelId=Integer.parseInt(args[7]);
		int dbFlag = Integer.parseInt(args[8]);
		
		if((setAvailability == 0 || setAvailability == 1) && roomNo > 0 && hotelId > 0 && maxOccu > 0 && nightRate >= 0 &&
				oldRoomNum > 0 && oldHotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.updateRoom(roomNo, hotelId, maxOccu, nightRate, setAvailability, oldRoomNum, oldHotelId, dbFlag);
		}else{
			System.out.println("Not valid data");
		}
	}

        /*
            input: N/A
            output: Display all existing rooms
        */         
	public void showRooms(String[] args) {
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showRooms(dbFlag);
	}

        /*
            input: Room Number, Hotel ID
            output: N/A (Delets a specific room)
        */         
	public void deleteRoom(String[] args) {
		int roomNo = Integer.parseInt(args[1]);
		int hotelId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
		
		if(roomNo > 0 && hotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.deleteRoom(roomNo, hotelId, dbFlag);
		}else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: Staff ID, Staff Phone, Staff Name, Staff Address, Staff Department, Staff Title, Staff Age
            output: N/A (Adds a new staff member to table)
        */         
	public void addStaff(String[] args) throws ParseException {
		Staff staff = new Staff();
		staff.setStaffId(Integer.parseInt(args[1]));
		staff.setPhone(args[2]);
		staff.setName(args[3]);
		staff.setAddress(args[4]);
		String date=args[5];
		java.util.Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(date);
		staff.setDob(convertUtilToSql(date1));
		staff.setDepartment(args[6]);
		staff.setTitle(args[7]);
		staff.setAge(Integer.parseInt(args[8]));
		
		int staffId = Integer.parseInt(args[1]);
		String phone = args[2];
		String name = args[3];
		String address = args[4];
		String department = args[6];
		String title = args[7];
		int age = Integer.parseInt(args[8]);
		int hotelId=Integer.parseInt(args[9]);
		int dbFlag = Integer.parseInt(args[10]);
		
		if(staffId > 0 && phone.length()>0 && phone != null && name != null && name.length() <= 50 && address != null && address.length() <= 50 && 
				department != null && department.length() <= 20 && title != null && title.length() <= 20 && age > 0 && date != null && hotelId>0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.addStaffToHotel(staff,hotelId, dbFlag);
		}else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: Staff ID, Staff Phone, Staff Name, Staff Address, Date, Staff Department, Staff Title, Staff Age, ID of staff to update
            output: N/A (Updates information of a specific staff)
        */          
	public void updateStaff(String[] args) throws ParseException {
		Staff staff = new Staff();
		staff.setStaffId(Integer.parseInt(args[1]));
		staff.setPhone(args[2]);
		staff.setName(args[3]);
		staff.setAddress(args[4]);
		
		String date=args[5];
		
		java.util.Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(date);
		staff.setDob(convertUtilToSql(date1));
		staff.setDepartment(args[6]);
		staff.setTitle(args[7]);
		staff.setAge(Integer.parseInt(args[8]));
		
		int staffId = Integer.parseInt(args[1]);
		String phone = args[2];
		String name = args[3];
		String address = args[4];
		String department = args[6];
		String title = args[7];
		int age = Integer.parseInt(args[8]);
		
		int oldStaffId=Integer.parseInt(args[9]);
		int dbFlag = Integer.parseInt(args[10]);
		
		if(staffId > 0 && phone.length()>0 && phone != null && name != null && name.length() <= 50 && address != null && address.length() <= 50 && 
				department != null && department.length() <= 20 && title != null && title.length() <= 20 && age > 0 && date != null){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.updateStaff(staff, oldStaffId, dbFlag);
		}else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: N/A
            output: Displays information on existing staff
        */          
	public void showStaffs(String[] args) {
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showStaffs(dbFlag);
	}

        /*
            input: Staff ID
            output: N/A (Deletes a specific staff)
        */          
	public void deleteStaff(String[] args) {
		Staff staff = new Staff();
		staff.setStaffId(Integer.parseInt(args[1]));
		int staffId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		if(staffId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.deleteStaff(staff, dbFlag);
		}else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: Customer ID, Customer Phone, Customer Name, Customer Email, Date
            output: N/A (Adds a new customer to table)
        */          
	public void addCustomer(String[] args) throws ParseException {
		Customer customer =  new Customer();
		customer.setCustomerId(Integer.parseInt(args[1]));
		customer.setPhone(args[2]);
		customer.setName(args[3]);
		customer.setEmail(args[4]);
		
		int customerId = Integer.parseInt(args[1]);
		String phone = args[2];
		String name = args[3];
		String email = args[4];
		String date=args[5];
		
		java.util.Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(date);
		customer.setDob(convertUtilToSql(date1));
		int dbFlag = Integer.parseInt(args[6]);
		
		if(customerId > 0 && phone.length() == 10 && phone != null && name != null && name.length() <= 50 && email != null && email.matches(regexEmail) && email.length() <= 50){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			System.out.println("Added hotel: "+informationProcessingDAO.addCustomer(customer, dbFlag));
		}else {
			System.out.println("Not valid data");
		}	
	}

        /*
            input: Customer ID, Customer Phone, Customer Name, Customer Email, Date, ID of customer to update
            output: N/A (Updates a customer's information)
        */           
	public void updateCustomer(String[] args) throws ParseException {
		Customer customer =  new Customer();
		customer.setCustomerId(Integer.parseInt(args[1]));
		customer.setPhone(args[2]);
		customer.setName(args[3]);
		customer.setEmail(args[4]);
		String date=args[5];
		java.util.Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(date);
		customer.setDob(convertUtilToSql(date1));
		
		int oldCustomerId=Integer.parseInt(args[6]);
		int customerId = Integer.parseInt(args[1]);
		String phone = args[2];
		String name = args[3];
		String email = args[4];
		int dbFlag = Integer.parseInt(args[7]);
		
		if(customerId > 0 && phone.length() == 10 && phone != null && name != null && name.length() <= 50 && email != null && email.matches(regexEmail) && email.length() <= 50){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.updateCustomer(customer, oldCustomerId, dbFlag);
		}else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: N/A
            output: Shows all customers
        */           
	public void showCustomers(String[] args) {
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showCustomers(dbFlag);
	}

        /*
            input: Customer ID
            output: N/A (Deletes a specific customer)
        */           
	public void deleteCustomer(String[] args) {
		Customer customer =  new Customer();
		customer.setCustomerId(Integer.parseInt(args[1]));
		int customerId = Integer.parseInt(args[1]);
		int dbFlag = Integer.parseInt(args[2]);
		if(customerId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.deleteCustomer(customer, dbFlag);
		}else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: Room Number, Hotel ID
            output: Displays availability of specific room
        */           
	public void checkRoomAvailability(String[] args) {
		int roomNo = Integer.parseInt(args[1]);
		int hotelId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
		if(roomNo > 0 && hotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.checkRoomAvailability(roomNo, hotelId, dbFlag);
		}
		else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: Room Type, Hotel ID
            output: Displays available rooms of a certain type in hotel
        */           
	public void checkRoomTypeAvailability(String[] args) {
		String roomType = args[1];
		int hotelId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
		if(hotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.checkRoomTypeAvailability(roomType, hotelId, dbFlag);
		}
		else {
			System.out.println("Not valid data");
		}
	}

        /*
            input: Updated availability, Room Number, Hotel ID
            output: N/A (Changes availability of a specific room)
        */           
	public void setRoomAvailability(String[] args) {
		int availability = Integer.parseInt(args[1]);
		int roomNo = Integer.parseInt(args[2]);
		int hotelId = Integer.parseInt(args[3]);
		int dbFlag = Integer.parseInt(args[4]);
		if(availability > 0 && roomNo > 0 && hotelId > 0){
			InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
			informationProcessingDAO.setRoomAvailability(availability, roomNo, hotelId, dbFlag);
		}else {
			System.out.println("Not valid data");
		}	
	}

        /*
            input: Staff ID, Customer ID, Number of Guests, Hotel ID, Room Number
            output: N/A (Assigns room to customer)
        */           
	public void assignRoom(String[] args) {
		int staffId = Integer.parseInt(args[1]);
		int customerId = Integer.parseInt(args[2]);
		int noOfGuests = Integer.parseInt(args[3]);
		int hotelId = Integer.parseInt(args[4]);
		int roomNo = Integer.parseInt(args[5]);
		int dbFlag = Integer.parseInt(args[6]);
		if(staffId > 0 && customerId > 0 && noOfGuests > 0 && roomNo > 0 && hotelId > 0){
			InformationProcessingDAO informationProcessingDAO = new InformationProcessingDAO();
			informationProcessingDAO.assignRoom(staffId, customerId, noOfGuests, roomNo, hotelId, dbFlag);		
		}else {
			System.out.println("Not valid data");
		}
		}
}
