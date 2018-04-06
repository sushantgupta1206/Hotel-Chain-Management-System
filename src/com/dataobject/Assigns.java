package com.dataobject;
//import java.sql.Date;

public class Assigns {
	private int staffId;
	private int customerId;
	private int noOfGuests; 
	private int roomNo;
	private int hotelId;
	
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getNoOfGuests() {
		return noOfGuests;
	}
	public void setNoOfGuests(int noOfGuests) {
		this.noOfGuests = noOfGuests;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	@Override
	public String toString() {
		return "Assigns [staffId = " + staffId + ",customerId = " + customerId + ", no of guests =" + noOfGuests + ", roomNo = " + roomNo + ", hotelId = " + hotelId + "]";
	}
	
}
