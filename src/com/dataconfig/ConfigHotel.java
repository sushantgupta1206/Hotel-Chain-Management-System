package com.dataconfig;

import com.databaserepo.InformationProcessingDAO;
import com.dataobject.Hotel;

public class ConfigHotel {
	
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
		informationProcessingDAO.updateHotelDB(hotelData, oldHotelId, dbFlag);
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
		System.out.println("Added hotel: "+informationProcessingDAO.addHotelDB(hotelData, dbFlag));
	}

	public void deleteHotel(String[] args) {
		Hotel hotelData = new Hotel();
		hotelData.setId(Integer.parseInt(args[1]));
		int dbFlag = Integer.parseInt(args[2]);
		InformationProcessingDAO informationProcessingDAO=new InformationProcessingDAO();
		informationProcessingDAO.deleteHotels(hotelData, dbFlag);
	}
}
