package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dataobject.Hotel;

public class InformationProcessingDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	//Insert Hotel Record.
	public int addHotelDB(Hotel hotelData, int dbFlag){
		String sourceMethod = "addHotelDB";
		String insertHotelDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".HOTELS ( HOTEL_ID, PHONE, NAME, ADDRESS, CITY ) VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int generatedKey = 0;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, hotelData.getId());
			preparedStatement.setInt(2, hotelData.getPhone());
			preparedStatement.setString(3, hotelData.getName());
			preparedStatement.setString(4, hotelData.getAddress());
			preparedStatement.setString(5, hotelData.getCity());
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    generatedKey = hotelData.getId();
			}
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			}catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, generatedKey);
		return generatedKey;
	}


	//Show all Hotel records.
	public void showHotels(int dbFlag) {
		String sourceMethod = "showHotels";
		List<Hotel> hotelDetails = new ArrayList<Hotel>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".HOTELS";
			selectQueryRS = stmt.executeQuery(selectStatement);
			while (selectQueryRS.next()) {
				Hotel hotel = new Hotel();
				hotel.setId(selectQueryRS.getInt("HOTEL_ID"));
				hotel.setPhone(selectQueryRS.getInt("PHONE"));
				hotel.setName(selectQueryRS.getString("NAME"));
				hotel.setAddress(selectQueryRS.getString("ADDRESS"));
				hotel.setCity(selectQueryRS.getString("CITY"));
				hotelDetails.add(hotel);
				System.out.println(hotel);
			}
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (selectQueryRS != null) {
					selectQueryRS.close();
				}
				if (stmt != null) {
					stmt.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			} catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}

		
	}


	//Show Hotel record By Id.
	public void showHotel(Hotel hotelData, int dbFlag) {
		String sourceMethod = "showHotel";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".HOTELS WHERE HOTEL_ID=?";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, hotelData.getId());
			selectQueryRS = stmt.executeQuery();
			while (selectQueryRS.next()) {
				Hotel hotel = new Hotel();
				hotel.setId(selectQueryRS.getInt("HOTEL_ID"));
				hotel.setPhone(selectQueryRS.getInt("PHONE"));
				hotel.setName(selectQueryRS.getString("NAME"));
				hotel.setAddress(selectQueryRS.getString("ADDRESS"));
				hotel.setCity(selectQueryRS.getString("CITY"));
				System.out.println(hotel);
			}
			
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (selectQueryRS != null) {
					selectQueryRS.close();
				}
				if (stmt != null) {
					stmt.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			} catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		
	}


	//Update Hotel Record by ID
	public void updateHotelDB(Hotel hotelData, int oldHotelId, int dbFlag) {
		String sourceMethod = "updateHotelDB";
		String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".HOTELS SET HOTEL_ID = ?, PHONE = ?, NAME = ?, ADDRESS = ?, CITY = ? WHERE HOTEL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfUpdatedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, hotelData.getId());
			preparedStatement.setInt(2, hotelData.getPhone());
			preparedStatement.setString(3, hotelData.getName());
			preparedStatement.setString(4, hotelData.getAddress());
			preparedStatement.setString(5, hotelData.getCity());
			preparedStatement.setInt(6, oldHotelId);
			numberOfUpdatedRows = preparedStatement.executeUpdate();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			}catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, numberOfUpdatedRows);
		System.out.println("numberOfUpdatedRows: "+numberOfUpdatedRows);
	}


	//Delete Hotel Record by ID
	public void deleteHotels(Hotel hotelData, int dbFlag) {
		String sourceMethod = "deleteHotels";
		String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".HOTELS WHERE HOTEL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfDeletedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, hotelData.getId());
			numberOfDeletedRows = preparedStatement.executeUpdate();
		} catch (Exception e) {
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				} 
				if (dbConn != null) {
					dbConn.close();
				}
			}catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
		log.exiting(sourceClass, sourceMethod, numberOfDeletedRows);
		System.out.println("numberOfDeletedRows: "+numberOfDeletedRows);
	}
	
	
}
