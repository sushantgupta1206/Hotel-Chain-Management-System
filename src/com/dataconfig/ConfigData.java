package com.dataconfig;

import com.databaserepo.InformationProcessingDAO;
import com.dataobject.Hotel;
import com.dataobject.Room;

/**
 * @author Kartik Shah
 *
 */
public class ConfigData {
	
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
}
