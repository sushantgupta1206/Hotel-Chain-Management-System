package com.dataconfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.databaserepo.InformationProcessingDAO;
import com.dataobject.Customer;
import com.dataobject.Hotel;
import com.dataobject.Room;
import com.dataobject.Staff;

/**
 * @author Kartik Shah
 *
 */
public class ConfigInformationProcessingData {
	
	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	public void updateHotel(String[] args) {
		Hotel hotelData = new Hotel();
		hotelData.setId(Integer.parseInt(args[1]));
		hotelData.setPhone(Integer.parseInt(args[2]));
		hotelData.setName(args[3]);
		hotelData.setAddress(args[4]);
		hotelData.setCity(args[5]);
		int oldHotelId=Integer.parseInt(args[6]);
		int dbFlag = Integer.parseInt(args[7]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.updateHotel(hotelData, oldHotelId, dbFlag);
	}
	
	public void showHotels(String args[]){
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showHotels(dbFlag);
	}

	public void showHotel(String args[]){
		Hotel hotelData = new Hotel();
		hotelData.setId(Integer.parseInt(args[1]));
		int dbFlag = Integer.parseInt(args[2]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showHotel(hotelData,dbFlag);
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
		System.out.println("Added hotel: "+informationProcessingDAO.addHotel(hotelData, dbFlag));
	}

	public void deleteHotel(String[] args) {
		Hotel hotelData = new Hotel();
		hotelData.setId(Integer.parseInt(args[1]));
		int dbFlag = Integer.parseInt(args[2]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.deleteHotel(hotelData, dbFlag);
	}

	public void addRoom(String[] args) {
		Room room = new Room();
		room.setRoomNum(Integer.parseInt(args[1]));
		room.setHotelId(Integer.parseInt(args[2]));
		room.setMaxOccu(Integer.parseInt(args[3]));
		room.setNightRate(Integer.parseInt(args[4]));
		int dbFlag = Integer.parseInt(args[5]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		System.out.println("Added Room: "+informationProcessingDAO.addRoom(room, dbFlag));
	}

	public void updateRoom(String[] args) {
		Room room = new Room();
		room.setRoomNum(Integer.parseInt(args[1]));
		room.setHotelId(Integer.parseInt(args[2]));
		room.setMaxOccu(Integer.parseInt(args[3]));
		room.setNightRate(Integer.parseInt(args[4]));
		room.setAvailability(Integer.parseInt(args[5]));
		
		int oldRoomNum=Integer.parseInt(args[6]);
		int oldHotelId=Integer.parseInt(args[7]);
		int dbFlag = Integer.parseInt(args[8]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.updateRoom(room, oldRoomNum, oldHotelId, dbFlag);
		
	}

	public void showRooms(String[] args) {
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showRooms(dbFlag);
	}

	public void deleteRoom(String[] args) {
		Room room = new Room();
		room.setRoomNum(Integer.parseInt(args[1]));
		room.setHotelId(Integer.parseInt(args[2]));
		int dbFlag = Integer.parseInt(args[3]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.deleteRoom(room, dbFlag);
	}

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
		int dbFlag = Integer.parseInt(args[9]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		System.out.println("Added hotel: "+informationProcessingDAO.addStaff(staff, dbFlag));
		
	}

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
		
		int oldStaffId=Integer.parseInt(args[9]);
		int dbFlag = Integer.parseInt(args[10]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.updateStaff(staff, oldStaffId, dbFlag);
	}

	public void showStaffs(String[] args) {
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showStaffs(dbFlag);
	}

	public void deleteStaff(String[] args) {
		Staff staff = new Staff();
		staff.setStaffId(Integer.parseInt(args[1]));
		int dbFlag = Integer.parseInt(args[2]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.deleteStaff(staff, dbFlag);
	}

	public void addCustomer(String[] args) throws ParseException {
		Customer customer =  new Customer();
		customer.setCustomerId(Integer.parseInt(args[1]));
		customer.setPhone(args[2]);
		customer.setName(args[3]);
		customer.setEmail(args[4]);
		String date=args[5];
		java.util.Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(date);
		customer.setDob(convertUtilToSql(date1));
		int dbFlag = Integer.parseInt(args[6]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		System.out.println("Added hotel: "+informationProcessingDAO.addCustomer(customer, dbFlag));
	}

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
		int dbFlag = Integer.parseInt(args[7]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.updateCustomer(customer, oldCustomerId, dbFlag);
	}

	public void showCustomers(String[] args) {
		int dbFlag = Integer.parseInt(args[1]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.showCustomers(dbFlag);
	}

	public void deleteCustomer(String[] args) {
		Customer customer =  new Customer();
		customer.setCustomerId(Integer.parseInt(args[1]));
		int dbFlag = Integer.parseInt(args[2]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.deleteCustomer(customer, dbFlag);
	}

	public void checkRoomAvailability(String[] args) {
		int roomNo = Integer.parseInt(args[1]);
		int hotelId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.checkRoomAvailability(roomNo, hotelId, dbFlag);
	}

	public void checkRoomTypeAvailability(String[] args) {
		String roomType = args[1];
		int hotelId = Integer.parseInt(args[2]);
		int dbFlag = Integer.parseInt(args[3]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.checkRoomTypeAvailability(roomType, hotelId, dbFlag);
	}

	public void setRoomAvailability(String[] args) {
		int availability = Integer.parseInt(args[1]);
		int roomNo = Integer.parseInt(args[2]);
		int hotelId = Integer.parseInt(args[3]);
		int dbFlag = Integer.parseInt(args[4]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.setRoomAvailability(availability, roomNo, hotelId, dbFlag);
	}

	public void assignRoom(String[] args) {
		// TODO Auto-generated method stub
		//Shushant will write the code
		int staffId = Integer.parseInt(args[1]);
		int customerId = Integer.parseInt(args[2]);
		int noOfGuests = Integer.parseInt(args[3]);
		int roomNo = Integer.parseInt(args[4]);
		int hotelId = Integer.parseInt(args[5]);
		int dbFlag = Integer.parseInt(args[6]);
		InformationProcessingDAO informationProcessingDAO = new InformationProcessingDAO();
		informationProcessingDAO.assignRoom(staffId, customerId, noOfGuests, roomNo, hotelId, dbFlag);
	}
}
