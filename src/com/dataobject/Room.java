package com.dataobject;

public class Room {
	private int roomNum;
	private int hotelId;
	private int maxOccu;
	private int nightRate;
	private int availability;
	
	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getMaxOccu() {
		return maxOccu;
	}

	public void setMaxOccu(int maxOccu) {
		this.maxOccu = maxOccu;
	}

	public int getNightRate() {
		return nightRate;
	}

	public void setNightRate(int nightRate) {
		this.nightRate = nightRate;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "Room [roomNum=" + roomNum + ", hotelId=" + hotelId + ", maxOccu=" + maxOccu + ", nightRate=" + nightRate
				+ ", availability=" + availability + "]";
	}

	

}
