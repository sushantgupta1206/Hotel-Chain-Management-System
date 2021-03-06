package com.databaserepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dataobject.Customer;
import com.dataobject.Hotel;
import com.dataobject.Room;
import com.dataobject.Staff;

/**
 * @author Kartik Shah
 *
 */

/*
 * This class performs all the core application task and operations for processing basic information of Hotel and its related entities.
   This class calls DBConnectUtils to get the database connection.
 */
public class InformationProcessingDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	/*
	 * High level design decision
	 * We check in every insert, update and delete operation whether the record first exists or not. 
	 * For example to insert Hotel we check to see if the hotel id already exists. 
	 * If it exists then we show output to user that object found if not then the record gets inserted. 
	 * Reason to do this is to prevent errors before hand for the records which are already committed. 
	 * If we don�t do this then we will get duplicate keys exception so, this is a preventive check.
	 * Even after this check if still two transactions try to make inserts on same record then the first transaction which committed will execute 
	 * without errors but, the second transaction will fail and we catch it and display proper error message to user as well as to developer. 
	 * We close all the connections in the finally block.
	 
	 * For select queries we check whether the resultset actually has any data by keeping the pointer before first record.
	 * We used parameterized query to avoid sql injections and also check data types before database interaction.
	 * 
	 * Wherever there are more than one serial inserts or updates we have introduced transactions. For instance, Insert Room record.
	 * 
	 * We open the database connection and close the connection after each operation. 
	 * This is because we aren�t doing connection pooling or centralized connection here. 
	 * One of the reason why we are not doing connection pooling here is to keep code simple and avoid connection close errors 
	 * while other transactions are working on it.

	 */
	
	/**Insert Hotel Record.
	 * @param hotelId
	 * @param phone
	 * @param name
	 * @param address
	 * @param city
	 * @param dbFlag
	 */
	
	public void addHotel(int hotelId, String phone,String name,String address,String city, int dbFlag){
		String sourceMethod = "addHotel";	
		//If the hotel doesn't exist in db then only it should attempt to insert. showhotel method returns true if exists.
		if(!showHotel(hotelId, dbFlag)){
			String insertHotelDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".HOTELS ( HOTEL_ID, PHONE, NAME, ADDRESS, CITY ) VALUES (?,?,?,?,?)";
			PreparedStatement preparedStatement = null;
			Connection dbConn = null;
			ResultSet rs = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, hotelId);
				preparedStatement.setString(2, phone);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, address);
				preparedStatement.setString(5, city);
				preparedStatement.execute();
				System.out.println("Hotel inserted with Id: "+ hotelId+" Query executed correctly.");
			} catch (Exception e) {
				System.out.println("Hotel record: "+ hotelId+" didn't inserted. query didn't executed correctly.");
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
			log.exiting(sourceClass, sourceMethod);
		}else{
			System.out.println("Hotel already found so cannot insert again.");
		}
		
	}

	/**Show all Hotel records.
	 * @param dbFlag
	 */
	
	public void showHotels(int dbFlag) {
		String sourceMethod = "showHotels";
		List<Hotel> hotelDetails = new ArrayList<Hotel>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".HOTELS";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			selectQueryRS = stmt.executeQuery();
			//This is used to check whether the resultset actually has any data
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No hotels found");
			}else{
				while (selectQueryRS.next()) {
					Hotel hotel = new Hotel();
					hotel.setId(selectQueryRS.getInt("HOTEL_ID"));
					hotel.setPhone(selectQueryRS.getString("PHONE"));
					hotel.setName(selectQueryRS.getString("NAME"));
					hotel.setAddress(selectQueryRS.getString("ADDRESS"));
					hotel.setCity(selectQueryRS.getString("CITY"));
					hotelDetails.add(hotel);
					System.out.println(hotel);
				}
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

	/**Show Hotel record By Id. showhotel method returns true if exists and false if doesn't exist in database.
	 * @param hotelId
	 * @param dbFlag
	 */
	
	public boolean showHotel(int hotelId, int dbFlag) {
		String sourceMethod = "showHotel";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".HOTELS WHERE HOTEL_ID=?";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, hotelId);
			selectQueryRS=stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No hotel found with this Id.");
				return false;
			}else{
				System.out.println("Hotel found.");
				while (selectQueryRS.next()) {
					Hotel hotel = new Hotel();
					hotel.setId(selectQueryRS.getInt("HOTEL_ID"));
					hotel.setPhone(selectQueryRS.getString("PHONE"));
					hotel.setName(selectQueryRS.getString("NAME"));
					hotel.setAddress(selectQueryRS.getString("ADDRESS"));
					hotel.setCity(selectQueryRS.getString("CITY"));
					System.out.println(hotel);
				}
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
		return true;
	}

	/**Update Hotel Record by ID
	 * @param hotelId
	 * @param phone
	 * @param name
	 * @param address
	 * @param city
	 * @param oldHotelId
	 * @param dbFlag
	 */
	
	public void updateHotel(int hotelId, String phone,String name,String address,String city, int oldHotelId, int dbFlag) {
		String sourceMethod = "updateHotel";
		if(showHotel(hotelId, dbFlag)){
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".HOTELS SET HOTEL_ID = ?, PHONE = ?, NAME = ?, ADDRESS = ?, CITY = ? WHERE HOTEL_ID = ?";
			PreparedStatement preparedStatement = null;
			int numberOfUpdatedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, hotelId);
				preparedStatement.setString(2, phone);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, address);
				preparedStatement.setString(5, city);
				preparedStatement.setInt(6, oldHotelId);
				numberOfUpdatedRows = preparedStatement.executeUpdate();
				if(numberOfUpdatedRows>0){
					System.out.println("Updated successfully numberOfUpdatedRows: "+numberOfUpdatedRows);
				}else{
					System.out.println("No records found numberOfUpdatedRows: "+numberOfUpdatedRows);
				}
			} catch (Exception e) {
				System.out.println("Updated unsuccessfully numberOfUpdatedRows: "+numberOfUpdatedRows);
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
		}
	}

/*	*//**Insert Manager Record.
	 * @param hotelId
	 * @param managerId
	 *//*
	
	public void addManager(int hotelId, int managerId, int dbFlag){
		String sourceMethod = "addManager";	
		String insertManagerQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".MANAGER (STAFF_ID, HOTEL_ID) VALUES (?,?)";
		PreparedStatement preparedStatement = null;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertManagerQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, hotelId);
			preparedStatement.setInt(2, managerId);
			preparedStatement.execute();
			System.out.println("Manager record: "+ hotelId+" inserted. . Query executed :"+insertManagerQuery);
		} catch (Exception e) {
			System.out.println("Manager record: "+ hotelId+" didn't inserted. query executed :"+insertManagerQuery);
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
		log.exiting(sourceClass, sourceMethod);
	}*/
	
	/**Delete Hotel Record by ID
	 * @param hotelId
	 * @param dbFlag
	 */
	
	public void deleteHotel(int hotelId, int dbFlag) {
		String sourceMethod = "deleteHotels";
		if(showHotel(hotelId, dbFlag)){
			String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".HOTELS WHERE HOTEL_ID = ?";
			PreparedStatement preparedStatement = null;
			int numberOfDeletedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, hotelId);
				numberOfDeletedRows = preparedStatement.executeUpdate();
				if(numberOfDeletedRows>0){
					System.out.println("Deleted successfully numberOfUpdatedRows: "+numberOfDeletedRows);
				}else{
					System.out.println("No records found numberOfUpdatedRows: "+numberOfDeletedRows);
				}
			} catch (Exception e) {
				System.out.println("Deleted unsuccessfully numberOfUpdatedRows: "+numberOfDeletedRows);
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
		}
	}

	
	/**Insert Room record is a transaction because it adds the entry in Rooms table as well as associates which room category the room belongs to.
	 * @param room
	 * @param dbFlag
	 * @return
	 */
	
	@SuppressWarnings("resource")
	public void addRoom(int roomNo, int hotelId, int maxOccu, int nightRate, int roomCat, int availability, int dbFlag) {
		String sourceMethod = "addRoom";
		if(!showRoom(roomNo, hotelId, dbFlag) && showRoomCatgeory(roomCat, dbFlag) && showHotel(hotelId, dbFlag)){
				String insertRoomDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".ROOMS ( ROOM_NO, HOTEL_ID, MAX_OCCUPANCY, NIGHTLY_RATE,AVAILABILITY ) VALUES (?,?,?,?,?)";
				PreparedStatement preparedStatement = null;
				Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				dbConn.setAutoCommit(false);
				preparedStatement = dbConn.prepareStatement(insertRoomDataQuery);
				preparedStatement.setInt(1, roomNo);
				preparedStatement.setInt(2, hotelId);
				preparedStatement.setInt(3, maxOccu);
				preparedStatement.setInt(4, nightRate);
				preparedStatement.setInt(5, availability);
				preparedStatement.execute();
				System.out.println("Room inserted: "+ roomNo+" . Query executed :"+insertRoomDataQuery);
				String insertSHDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".ROOM_HAS (ROOM_NO, HOTEL_ID, ROOM_CATEGORY_ID) VALUES (?,?,?)";
				preparedStatement = dbConn.prepareStatement(insertSHDataQuery);
				preparedStatement.setInt(1, roomNo);
				preparedStatement.setInt(2, hotelId);
				preparedStatement.setInt(3, roomCat);
				preparedStatement.execute();
				System.out.println("Room inserted with Room category: "+roomCat+" . Query executed: " + insertSHDataQuery);
				dbConn.commit();
			} catch (SQLException e) {
				try {
					dbConn.rollback();
				} catch (SQLException e1) {
					System.out.println("The transaction will be rolled back because :");
					log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				}
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				// Prints the error message if something goes wrong when updating.
			} finally {
				try {
					dbConn.setAutoCommit(true);
					if (preparedStatement != null) {
						preparedStatement.close();
					}
					if (dbConn != null) {
						/*
						 * Since we are using a connection.commit() or
						 * connection.rollback() prior to the close so, the
						 * connection remains in progress when we try to close. This
						 * step will help to close the transactions. It is in final
						 * block so that it always executes and the program doesn't
						 * throw any exception while closing connection.
						 */
						dbConn.close();
					}
				} catch (Exception e) {
					log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				}
			}
		}
		log.exiting(sourceClass, sourceMethod);	
	}

	/**Update Room record by Room Num and Hotel Id. It has transactional updates.
	 * @param roomNo
	 * @param hotelId
	 * @param maxOccu
	 * @param nightRate
	 * @param setAvailability
	 * @param oldRoomNum
	 * @param oldHotelId
	 * @param dbFlag
	 */
	
	public void updateRoom(int roomNo,int hotelId,int maxOccu,int nightRate,int setAvailability, int roomCategory, int oldRoomNum, int oldHotelId, int dbFlag) {
		String sourceMethod = "updateRoom";
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		int numberOfUpdatedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			dbConn.setAutoCommit(false);
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOMS SET ROOM_NO = ?, HOTEL_ID = ?,MAX_OCCUPANCY=?, NIGHTLY_RATE=?, AVAILABILITY=? WHERE ROOM_NO = ? AND HOTEL_ID = ?";
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, roomNo);
			preparedStatement.setInt(2, hotelId);
			preparedStatement.setInt(3, maxOccu);
			preparedStatement.setInt(4, nightRate);
			preparedStatement.setInt(5, setAvailability);
			preparedStatement.setInt(6, oldRoomNum);
			preparedStatement.setInt(7, oldHotelId);
			numberOfUpdatedRows = preparedStatement.executeUpdate();
			String updateRoomCategory = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOM_HAS SET ROOM_CATEGORY_ID=? WHERE ROOM_NO = ? AND HOTEL_ID = ?";
			preparedStatement1 = dbConn.prepareStatement(updateRoomCategory);
			preparedStatement1.setInt(1, roomCategory);
			preparedStatement1.setInt(2, roomNo);
			preparedStatement1.setInt(3, hotelId);
			numberOfUpdatedRows = numberOfUpdatedRows + preparedStatement1.executeUpdate();
			dbConn.commit();
			if(numberOfUpdatedRows>0){
				System.out.println("Updated successfully numberOfUpdatedRows: "+numberOfUpdatedRows);
			}else{
				System.out.println("No records found numberOfUpdatedRows: "+numberOfUpdatedRows);
			}
			
		} catch (SQLException e) {
			try {
				dbConn.rollback();
			} catch (SQLException e1) {
				System.out.println("The transaction will be rolled back because :");
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
			log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			// Prints the error message if something goes wrong when updating.
		} finally {
			try {
				dbConn.setAutoCommit(true);
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (dbConn != null) {
					/*
					 * Since we are using a connection.commit() or
					 * connection.rollback() prior to the close so, the
					 * connection remains in progress when we try to close. This
					 * step will help to close the transactions. It is in final
					 * block so that it always executes and the program doesn't
					 * throw any exception while closing connection.
					 */
					dbConn.close();
				}
			} catch (Exception e) {
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
			}
		}
	}
	
	/**Show All Rooms
	 * @param dbFlag
	 */
	
	public void showRooms(int dbFlag) {
		String sourceMethod = "showRooms";
		List<Room> roomDetails = new ArrayList<Room>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".ROOMS";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			selectQueryRS = stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No rooms found");
			}else{
			while (selectQueryRS.next()) {
				Room room=new Room();
				room.setRoomNum(selectQueryRS.getInt("ROOM_NO"));
				room.setHotelId(selectQueryRS.getInt("HOTEL_ID"));
				room.setMaxOccu(selectQueryRS.getInt("MAX_OCCUPANCY"));
				room.setNightRate(selectQueryRS.getInt("NIGHTLY_RATE"));
				room.setAvailability(selectQueryRS.getInt("AVAILABILITY"));
				roomDetails.add(room);
				System.out.println(room);
				}
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

	/**Show Hotel record By Id.
	 * @param hotelId
	 * @param dbFlag
	 */
	
	public boolean showRoom(int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "showRoom";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		List<Room> roomDetails = new ArrayList<Room>();
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ROOM_NO=? AND HOTEL_ID=?";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, roomNo);
			stmt.setInt(2, hotelId);
			selectQueryRS=stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No room found");
				return false;
			}else{
				System.out.println("Room found");
				while (selectQueryRS.next()) {
					Room room=new Room();
					room.setRoomNum(selectQueryRS.getInt("ROOM_NO"));
					room.setHotelId(selectQueryRS.getInt("HOTEL_ID"));
					room.setMaxOccu(selectQueryRS.getInt("MAX_OCCUPANCY"));
					room.setNightRate(selectQueryRS.getInt("NIGHTLY_RATE"));
					room.setAvailability(selectQueryRS.getInt("AVAILABILITY"));
					roomDetails.add(room);
					System.out.println(room);
				}
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
		return true;
	}

	public boolean showRoomCatgeory(int roomCat, int dbFlag){
		String sourceMethod = "showRoomCatgeory";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".ROOM_CATEGORY WHERE ID=?";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, roomCat);
			selectQueryRS=stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No room category found");
				return false;
			}else{
				System.out.println("Room category found");
				while (selectQueryRS.next()) {
					int id = selectQueryRS.getInt("ID");
					String type= selectQueryRS.getString("TYPE");
					System.out.println("ID: "+id+" Type: "+type );
				}
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
		return true;
	}
	
	
	public boolean showAllRoomCatgeory(int dbFlag){
		String sourceMethod = "showAllRoomCatgeory";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".ROOM_CATEGORY";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			selectQueryRS=stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No room category found");
				return false;
			}else{
				System.out.println("Id 	Type" );
				while (selectQueryRS.next()) {
					int id = selectQueryRS.getInt("ID");
					String type= selectQueryRS.getString("TYPE");
					System.out.println(id+"	 "+type );
				}
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
		return true;
	}
	
	/**Delete room by room num and hotel id
	 * @param roomNo
	 * @param hotelId
	 * @param dbFlag
	 */
	
	public void deleteRoom(int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "deleteRoom";
		String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ROOM_NO = ? AND HOTEL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfDeletedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, roomNo);
			preparedStatement.setInt(2, hotelId);
			numberOfDeletedRows = preparedStatement.executeUpdate();
			if(numberOfDeletedRows>0){
				System.out.println("Deleted successfully numberOfUpdatedRows: "+numberOfDeletedRows);
			}else{
				System.out.println("No records found numberOfUpdatedRows: "+numberOfDeletedRows);
			}
		} catch (Exception e) {
			System.out.println("Deleted unsuccessfully numberOfUpdatedRows: "+numberOfDeletedRows);
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

	
	/**Insert staff. It has transaction in it. 
	 * @param staff
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	
	@SuppressWarnings("resource")
	public void addStaffToHotel(Staff staff, int hotelId, int dbFlag) {
		String sourceMethod = "addStaff";
		if(showHotel(hotelId, dbFlag)){
			PreparedStatement preparedStatement = null;
			Connection dbConn = null;
			try {
				System.out.println("TRY 1");
				dbConn = dbUtil.getConnection(dbFlag);
				dbConn.setAutoCommit(false);
				String insertDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".STAFF (STAFF_ID, PHONE, NAME, ADDRESS, DOB, DEPARTMENT, TITLE, AGE) VALUES (?,?,?,?,?,?,?,?)";
				preparedStatement = dbConn.prepareStatement(insertDataQuery);
				preparedStatement.setInt(1, staff.getStaffId());
				preparedStatement.setString(2, staff.getPhone());
				preparedStatement.setString(3, staff.getName());
				preparedStatement.setString(4, staff.getAddress());
				preparedStatement.setDate(5, staff.getDob());
				preparedStatement.setString(6, staff.getDepartment());
				preparedStatement.setString(7, staff.getTitle());
				preparedStatement.setInt(8, staff.getAge());
				preparedStatement.execute();
				System.out.println("Staff inserted: " + staff.getStaffId() + " . Query executed :" + insertDataQuery);
				
				String insertSHDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".SHWORKSFOR (STAFF_ID, HOTEL_ID) VALUES (?,?)";
				preparedStatement = dbConn.prepareStatement(insertSHDataQuery);
				preparedStatement.setInt(1, staff.getStaffId());
				preparedStatement.setInt(2, hotelId);
				preparedStatement.execute();
				System.out.println("Staff inserted to hotel: "+ hotelId +" staff: " + staff.getStaffId() + " . Query executed :" + insertSHDataQuery);
				
				if(staff.getTitle().trim().equals("Manager")){
					String insertDataQuery1 = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".MANAGER (STAFF_ID, HOTEL_ID) VALUES (?,?)";
					preparedStatement = dbConn.prepareStatement(insertDataQuery1);
					preparedStatement.setInt(1, staff.getStaffId());
					preparedStatement.setInt(2, hotelId);
					preparedStatement.execute();
					System.out.println("Manager inserted: " + staff.getStaffId() + " . Query executed :" + insertDataQuery1);
					
				}else if(staff.getTitle().trim().equals("Front Desk Staff")){
					String insertDataQuery1 = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".FRONTDESKREP (STAFF_ID) VALUES (?)";
					preparedStatement = dbConn.prepareStatement(insertDataQuery1);
					preparedStatement.setInt(1, staff.getStaffId());
					preparedStatement.execute();
					System.out.println("Front Desk Staff inserted: " + staff.getStaffId() + " . Query executed :" + insertDataQuery1);
				}else{
					String insertDataQuery1 = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".SERVICE_STAFF (STAFF_ID) VALUES (?)";
					preparedStatement = dbConn.prepareStatement(insertDataQuery1);
					preparedStatement.setInt(1, staff.getStaffId());
					preparedStatement.execute();
					System.out.println("Service staff inserted: " + staff.getStaffId() + " . Query executed :" + insertDataQuery1);
				}
				
				dbConn.commit();
	
			} catch (SQLException e) {
				try {
					dbConn.rollback();
				} catch (SQLException e1) {
					System.out.println("TRY 3");
					System.out.println("The transaction will be rolled back because :");
					log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				}
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				// Prints the error message if something goes wrong when updating.
			} finally {
				try {
					dbConn.setAutoCommit(true);
					if (preparedStatement != null) {
						preparedStatement.close();
					}
					if (dbConn != null) {
						/*
						 * Since we are using a connection.commit() or
						 * connection.rollback() prior to the close so, the
						 * connection remains in progress when we try to close. This
						 * step will help to close the transactions. It is in final
						 * block so that it always executes and the program doesn't
						 * throw any exception while closing connection.
						 */
						dbConn.close();
					}
				} catch (Exception e) {
					log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				}
			}
		}
		log.exiting(sourceClass, sourceMethod);
	}

	/**Show all staffs
	 * @param dbFlag
	 */
	
	public void showStaffs(int dbFlag) {
		String sourceMethod = "showStaffs";
		List<Staff> details = new ArrayList<Staff>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".STAFF";
			selectQueryRS = stmt.executeQuery(selectStatement);
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No staff found");
			}else{
				while (selectQueryRS.next()) {
					Staff detail=new Staff();
					detail.setStaffId(selectQueryRS.getInt("STAFF_ID"));
					detail.setPhone(selectQueryRS.getString("PHONE"));
					detail.setName(selectQueryRS.getString("NAME"));
					detail.setAddress(selectQueryRS.getString("ADDRESS"));
					detail.setDob(selectQueryRS.getDate("DOB"));
					detail.setDepartment(selectQueryRS.getString("DEPARTMENT"));
					detail.setTitle(selectQueryRS.getString("TITLE"));
					detail.setAge(selectQueryRS.getInt("AGE"));
					details.add(detail);
					System.out.println(detail);
				}
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
	
	/**Show all staffs
	 * @param dbFlag
	 */
	
	public boolean showStaff(int staffId, int dbFlag) {
		String sourceMethod = "showStaff";
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		PreparedStatement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".STAFF WHERE STAFF_ID=?";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, staffId);
			selectQueryRS = stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No staff found");
				return false;
			}
			while (selectQueryRS.next()) {
				Staff detail=new Staff();
				detail.setStaffId(selectQueryRS.getInt("STAFF_ID"));
				detail.setPhone(selectQueryRS.getString("PHONE"));
				detail.setName(selectQueryRS.getString("NAME"));
				detail.setAddress(selectQueryRS.getString("ADDRESS"));
				detail.setDob(selectQueryRS.getDate("DOB"));
				detail.setDepartment(selectQueryRS.getString("DEPARTMENT"));
				detail.setTitle(selectQueryRS.getString("TITLE"));
				detail.setAge(selectQueryRS.getInt("AGE"));
				System.out.println(detail);
				System.out.println("Staff Already found");
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
		return true;	
	}

	/**Delete staff record by staff id 
	 * @param staff
	 * @param dbFlag
	 */
	
	public void deleteStaff(Staff staff, int dbFlag) {
		String sourceMethod = "deleteStaff";
		if(showStaff(staff.getStaffId(), dbFlag)){
			String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".STAFF WHERE STAFF_ID = ?";
			PreparedStatement preparedStatement = null;
			int numberOfDeletedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, staff.getStaffId());
				numberOfDeletedRows = preparedStatement.executeUpdate();
				if(numberOfDeletedRows>0){
					System.out.println("Deleted successfully numberOfUpdatedRows: "+numberOfDeletedRows);
				}else{
					System.out.println("No records found numberOfUpdatedRows: "+numberOfDeletedRows);
				}
			} catch (Exception e) {
				System.out.println("Deleted unsuccessfully numberOfUpdatedRows: "+numberOfDeletedRows);
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

	/**Update staff record by staff id
	 * @param staff
	 * @param oldStaffId
	 * @param dbFlag
	 */
	
	public void updateStaff(Staff staff, int oldStaffId, int dbFlag) {
		String sourceMethod = "updateRoom";
		if(showStaff(staff.getStaffId(), dbFlag)){
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".STAFF SET STAFF_ID = ?, PHONE = ?, NAME = ?, ADDRESS =?, DOB = ?, DEPARTMENT = ?, TITLE =?, AGE = ? WHERE STAFF_ID = ?";
			PreparedStatement preparedStatement = null;
			int numberOfUpdatedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, staff.getStaffId());
				preparedStatement.setString(2, staff.getPhone());
				preparedStatement.setString(3, staff.getName());
				preparedStatement.setString(4, staff.getAddress());
				preparedStatement.setDate(5, staff.getDob());
				preparedStatement.setString(6, staff.getDepartment());
				preparedStatement.setString(7, staff.getTitle());
				preparedStatement.setInt(8, staff.getAge());
				preparedStatement.setInt(9, oldStaffId);
				numberOfUpdatedRows = preparedStatement.executeUpdate();
				if(numberOfUpdatedRows>0){
					System.out.println("Updated successfully numberOfUpdatedRows: "+numberOfUpdatedRows);
				}else{
					System.out.println("No records found numberOfUpdatedRows: "+numberOfUpdatedRows);
				}
			} catch (Exception e) {
				System.out.println("Updated unsuccessfully numberOfUpdatedRows: "+numberOfUpdatedRows);
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
	}

	/**Add customer record
	 * @param customer
	 * @param dbFlag
	 * @return
	 */
	
	public void addCustomer(Customer customer, int dbFlag) {
		String sourceMethod = "addCustomer";
		if(!showCustomer(customer.getCustomerId(), dbFlag)){
			String insertDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".CUSTOMER (CUSTOMER_ID, PHONE, NAME, EMAIL_ADDRESS, DOB) VALUES (?,?,?,?,?)";
			PreparedStatement preparedStatement = null;
			Connection dbConn = null;
			ResultSet rs = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(insertDataQuery,Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, customer.getCustomerId());
				preparedStatement.setString(2, customer.getPhone());
				preparedStatement.setString(3, customer.getName());
				preparedStatement.setString(4, customer.getEmail());
				preparedStatement.setDate(5, customer.getDob());
				preparedStatement.execute();
				
				System.out.println("Customer record: "+ customer.getCustomerId()+" inserted. . Query executed :"+insertDataQuery);
				} catch (Exception e) {
					System.out.println("Customer record: "+ customer.getCustomerId()+" didn't inserted. query executed :"+insertDataQuery);
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
		}
		log.exiting(sourceClass, sourceMethod);
	}

	/**Show all customers
	 * @param dbFlag
	 */
	
	public void showCustomers(int dbFlag) {
		String sourceMethod = "showCustomers";
		List<Customer> details = new ArrayList<Customer>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".CUSTOMER";
			selectQueryRS = stmt.executeQuery(selectStatement);
			while (selectQueryRS.next()) {
				Customer detail=new Customer();
				detail.setCustomerId(selectQueryRS.getInt("CUSTOMER_ID"));
				detail.setPhone(selectQueryRS.getString("PHONE"));
				detail.setName(selectQueryRS.getString("NAME"));
				detail.setEmail(selectQueryRS.getString("EMAIL_ADDRESS"));
				detail.setDob(selectQueryRS.getDate("DOB"));
				details.add(detail);
				System.out.println(detail);
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

	
	public boolean checkCustomerByTime(int customerId,String serviceTime, int dbFlag) {
		String sourceMethod = "showCustomer";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".CUSTOMER INNER JOIN  "+DBConnectUtils.DBSCHEMA+".ASSIGNS "
					+ "WHERE CUSTOMER.CUSTOMER_ID=ASSIGNS.CUSTOMER_ID AND CUSTOMER.CUSTOMER_ID=? AND ? BETWEEN ASSIGNS.CHECK_IN AND ASSIGNS.CHECK_OUT";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, customerId);
			stmt.setString(2, serviceTime);
			selectQueryRS=stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("The Customer has not yet checked in. ");
				return false;
			}else{
				System.out.println("Customer found");
				while (selectQueryRS.next()) {
					Customer detail=new Customer();
					detail.setCustomerId(selectQueryRS.getInt("CUSTOMER_ID"));
					detail.setPhone(selectQueryRS.getString("PHONE"));
					detail.setName(selectQueryRS.getString("NAME"));
					detail.setEmail(selectQueryRS.getString("EMAIL_ADDRESS"));
					detail.setDob(selectQueryRS.getDate("DOB"));
					System.out.println(detail);
				}
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
		return true;
	}

	public boolean showCustomer(int customerId, int dbFlag) {
		String sourceMethod = "showCustomer";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".CUSTOMER WHERE CUSTOMER_ID=?";
			stmt = dbConn.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, customerId);
			selectQueryRS=stmt.executeQuery();
			if (!selectQueryRS.isBeforeFirst()) {
				System.out.println("No Customer found");
				return false;
			}else{
				System.out.println("Customer found");
				while (selectQueryRS.next()) {
					Customer detail=new Customer();
					detail.setCustomerId(selectQueryRS.getInt("CUSTOMER_ID"));
					detail.setPhone(selectQueryRS.getString("PHONE"));
					detail.setName(selectQueryRS.getString("NAME"));
					detail.setEmail(selectQueryRS.getString("EMAIL_ADDRESS"));
					detail.setDob(selectQueryRS.getDate("DOB"));
					System.out.println(detail);
				}
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
		return true;
	}

	/**Delete customer by customer id
	 * @param customer
	 * @param dbFlag
	 */
	
	public void deleteCustomer(Customer customer, int dbFlag) {
		String sourceMethod = "deleteCustomer";
		if(showCustomer(customer.getCustomerId(), dbFlag)){
			String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".CUSTOMER WHERE CUSTOMER_ID = ?";
			PreparedStatement preparedStatement = null;
			int numberOfDeletedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, customer.getCustomerId());
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

	/**Update Customer record by customer id
	 * @param customer
	 * @param oldCustomerId
	 * @param dbFlag
	 */
	
	public void updateCustomer(Customer customer, int oldCustomerId, int dbFlag) {
		String sourceMethod = "updateCustomer";
		if(showCustomer(customer.getCustomerId(), dbFlag)){
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".CUSTOMER SET CUSTOMER_ID = ?, PHONE = ?, NAME = ?, EMAIL_ADDRESS = ?, DOB = ? WHERE CUSTOMER_ID = ?";
			PreparedStatement preparedStatement = null;
			int numberOfUpdatedRows = 0;
			Connection dbConn = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, customer.getCustomerId());
				preparedStatement.setString(2, customer.getPhone());
				preparedStatement.setString(3, customer.getName());
				preparedStatement.setString(4, customer.getEmail());
				preparedStatement.setDate(5, customer.getDob());
				preparedStatement.setInt(6, oldCustomerId);
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
	}
	
	/**Pass roomNo and hotelId as argument and get response as room type is available or not
	 * @param roomNo
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	
	public void checkRoomAvailability(int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "checkRoomAvailability";
		if(showRoom(roomNo, hotelId, dbFlag)){
			PreparedStatement stmt = null;
			Connection dbConn = null;
			ResultSet selectQueryRS = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				String selectStatement = "SELECT (CASE WHEN AVAILABILITY=0 THEN 'FALSE' ELSE 'TRUE' END) AS AVAILABILITY FROM "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ROOM_NO=? AND HOTEL_ID=?";
				stmt = dbConn.prepareStatement(selectStatement);
				stmt.setInt(1, roomNo);
				stmt.setInt(2, hotelId);
				selectQueryRS = stmt.executeQuery();
				if (!selectQueryRS.isBeforeFirst()) {
					System.out.println("No list found");
				}else{
					System.out.println("List found");
					System.out.println("AVAILABILITY");
					while (selectQueryRS.next()) {
						String avail = selectQueryRS.getString("AVAILABILITY");
						System.out.println(avail);
					}
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
	}

	/**Pass roomNo and hotelId as argument and get response as room type is available or not
	 * @param roomNo
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	
	public void checkRoomsAvailability(String roomNos, int hotelId, int dbFlag) {
		String sourceMethod = "checkRoomAvailability";
		String roomsarr[] =roomNos.split(",");
		
		for (int i=0; i<roomsarr.length; i++){
			if(showRoom(Integer.parseInt(roomsarr[i]), hotelId, dbFlag)){
				PreparedStatement stmt = null;
				Connection dbConn = null;
				ResultSet selectQueryRS = null;
				try {
					dbConn = dbUtil.getConnection(dbFlag);
					String selectStatement = "SELECT (CASE WHEN AVAILABILITY=0 THEN 'FALSE' ELSE 'TRUE' END) AS AVAILABILITY FROM "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ROOM_NO=? AND HOTEL_ID=?";
					stmt = dbConn.prepareStatement(selectStatement);
					stmt.setInt(1, Integer.parseInt(roomsarr[i]));
					stmt.setInt(2, hotelId);
					selectQueryRS = stmt.executeQuery();
					if (!selectQueryRS.isBeforeFirst()) {
						System.out.println("No list found");
					}else{
						System.out.println("List found");
						System.out.println("AVAILABILITY");
						while (selectQueryRS.next()) {
							String avail = selectQueryRS.getString("AVAILABILITY");
							System.out.println(avail);
						}
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
		}
	}


	/**Pass roomType and hotelId as argument and get response as room type is available or not
	 * @param roomType
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	
	public void checkRoomTypeAvailability(String roomType, int hotelId, int dbFlag) {
		String sourceMethod = "checkRoomTypeAvailability";
		if(showHotel(hotelId, dbFlag)){
			PreparedStatement stmt = null;
			Connection dbConn = null;
			ResultSet selectQueryRS = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				String selectStatement = "SELECT R.ROOM_NO, R.HOTEL_ID, RC.TYPE, (CASE WHEN AVAILABILITY=0 THEN 'FALSE' ELSE "+
										"'TRUE' END) AS AVAILABILITY FROM "+DBConnectUtils.DBSCHEMA+".ROOMS AS R, "+DBConnectUtils.DBSCHEMA+".ROOM_HAS AS RH, "+DBConnectUtils.DBSCHEMA+". ROOM_CATEGORY AS RC "+
										"WHERE R.ROOM_NO=RH.ROOM_NO AND R.HOTEL_ID=RH.HOTEL_ID AND "+
										"RH.ROOM_CATEGORY_ID=RC.ID AND RC.TYPE=? AND R.HOTEL_ID=?";
				stmt = dbConn.prepareStatement(selectStatement);
				stmt.setString(1, roomType);
				stmt.setInt(2, hotelId);
				selectQueryRS = stmt.executeQuery();
				if (!selectQueryRS.isBeforeFirst()) {
					System.out.println("No list found");
				}else{
					System.out.println("List found");
					System.out.println("ROOM_NO		HOTEL_ID  	AVAILABILITY");
					while (selectQueryRS.next()) {
						int roomNo = selectQueryRS.getInt("ROOM_NO");
						int hId = selectQueryRS.getInt("HOTEL_ID");
						String avail = selectQueryRS.getString("AVAILABILITY");
						System.out.println(roomNo+"		"+hId+ "	"+avail);
					}
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
	}

	public String setRoomAvailability(int availability, int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "setRoomAvailability";
		PreparedStatement preparedStatement = null;
		int numberOfUpdatedRows = 0;
		String responsenumberOfUpdatedRows = null;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOMS SET AVAILABILITY=? WHERE ROOM_NO=? AND HOTEL_ID=?";
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, availability);
			preparedStatement.setInt(2, roomNo);
			preparedStatement.setInt(3, hotelId);
			numberOfUpdatedRows = preparedStatement.executeUpdate();
			responsenumberOfUpdatedRows = ""+numberOfUpdatedRows;
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
		return responsenumberOfUpdatedRows;
	}
	
	//Design Decision and what does assignRoomAndSetAvailability method does:
	/*
	 * 
	   Input parameters are 1) Staff Id
		 2) Customer Id
		 3) Number of guests going to stay including the customer for that stay.
		 4) The room number that customer has selected
		 5) The hotel id that customer has selected
		 6) The checkin time of customer
		 7) The checkout time of the customer
		 8) Database flag
		 
		 At Step 1: 
		 we check that the customer actually exists in the database. We also check that the staff exists or not. We also check room in that hotel exists or not.
		 
		 At Step 2:
		 Java default gives isolation as read committed but, we check that if the isolation level is not transaction read committed then change it to transaction read committed. 
		 This is because we just want to read the committed data before the start of assign room transaction. We don't want to get dirty data during this process.
		 
		 At Step 3:
		 We change the auto commit to false because we want to rollback a transaction if something goes wrong or execute everything if everything goes right. 
		 So, we want to commit it manually and not automatically.
		 We do a select query to check whether the room is available and the max occupancy of room is more than number of guests. 
		 Also, we check whether that staff works for that hotel. 
		 Select query can be outside of auto commit false because select query is independent of commits. 
		 We have kept select query within auto commit false just because it doesn't hurt to keep there and also it is for 
		 our understanding that Select is a part of transaction. 
		  
		 At Step 4:
		 The real transaction starts from here. We insert in assigns table the entry of the customer stay.
		 We update the Rooms table with availability = 0 meaning the room is no longer available. 
		 If both these executes properly then in the end we commit both the transaction.
		 
		 At Step 5:
		 If there is an exception, we rollback the whole transaction and nothing persists.
		 
		 At Step 6:
		 We set the auto commit to true always in the finally block because we want other transactions to behave in original manner like execute and persist way automatically.
		 We close all the connection.  
	 */ 
	@SuppressWarnings("resource")
	public void assignRoomAndSetAvailability(int staffId, int customerId, int noOfGuests, int roomNo, int hotelId,String checkInDate,String checkOutDate, int dbFlag){
		String sourceMethod = "assignRoomAndSetAvailability";
		//Step 1
		if(showCustomer(customerId, dbFlag) && showStaff(staffId, dbFlag) && showRoom(roomNo, hotelId, dbFlag)){
			PreparedStatement stmt = null;
			Connection dbConn = null;
			ResultSet selectQueryRS = null;
			PreparedStatement preparedStatement = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				//Step 2:
				final int previousIsolationLevel = dbConn.getTransactionIsolation();
				if (previousIsolationLevel != 2) {
					dbConn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				}
				
				//Step 3:
				dbConn.setAutoCommit(false);
				String selectStatement = "SELECT * FROM  "+DBConnectUtils.DBSCHEMA+".ROOMS AS R INNER JOIN  "+DBConnectUtils.DBSCHEMA+".SHWORKSFOR AS SWF WHERE R.HOTEL_ID=SWF.HOTEL_ID AND "
						+ "R.ROOM_NO=? AND R.HOTEL_ID=? AND MAX_OCCUPANCY>=? AND AVAILABILITY=1 AND STAFF_ID=?";
				System.out.println(selectStatement);
				stmt = dbConn.prepareStatement(selectStatement); //Prepared statement is used to avoid sql injection and to validate the data type
				stmt.setInt(1, roomNo);
				stmt.setInt(2, hotelId);
				stmt.setInt(3, noOfGuests);
				stmt.setInt(4, staffId);
				selectQueryRS = stmt.executeQuery(); //Execute query is used for select query.
				//Below condition is checked to see if the result set has any record.
				if (!selectQueryRS.isBeforeFirst()) {
					System.out.println("Room not available or guests more than occupancy");
				}else{
					System.out.println("Room available and guests not more than occupancy");
					System.out.println("ROOM_NO		HOTEL_ID");
					while (selectQueryRS.next()) {
						int roomN = selectQueryRS.getInt("ROOM_NO");
						int hotelI = selectQueryRS.getInt("HOTEL_ID");
						System.out.println(roomN+"		"+hotelI);
					}

				//Step 4:	
					String insertDataQuery = "INSERT INTO " + DBConnectUtils.DBSCHEMA
							+ ".ASSIGNS(STAFF_ID, CUSTOMER_ID,CHECK_IN,CHECK_OUT, NO_OF_GUESTS, HOTEL_ID, ROOM_NO) "
							+ "SELECT ?,?,?,?,?,?,? FROM  " + DBConnectUtils.DBSCHEMA
							+ ".ROOMS AS R INNER JOIN  "+DBConnectUtils.DBSCHEMA+".SHWORKSFOR AS SWF WHERE R.HOTEL_ID=SWF.HOTEL_ID AND "
									+ " R.ROOM_NO=? AND R.HOTEL_ID=? AND " + "MAX_OCCUPANCY>=? AND AVAILABILITY=1 AND STAFF_ID=?";
		
					preparedStatement = dbConn.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setInt(1, staffId);
					preparedStatement.setInt(2, customerId);
					preparedStatement.setString(3, checkInDate);
					preparedStatement.setString(4, checkOutDate);
					preparedStatement.setInt(5, noOfGuests);
					preparedStatement.setInt(6, hotelId);
					preparedStatement.setInt(7, roomNo);
					preparedStatement.setInt(8, roomNo);
					preparedStatement.setInt(9, hotelId);
					preparedStatement.setInt(10, noOfGuests);
					preparedStatement.setInt(11, staffId);
					preparedStatement.execute(); //Execute is used to insert query data.
					System.out.println("Assigns record in process of inserted.");
		
						String updateDeleteRequestStatement = "UPDATE " + DBConnectUtils.DBSCHEMA
								+ ".ROOMS SET AVAILABILITY=0 WHERE ROOM_NO=? AND HOTEL_ID=?";
						preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
						preparedStatement.setInt(1, roomNo);
						preparedStatement.setInt(2, hotelId);
						int numberOfUpdatedRows = preparedStatement.executeUpdate(); //Execute update is used for update query
						if(numberOfUpdatedRows>0){
							System.out.println("Room availability changed to not available."+numberOfUpdatedRows);
						}else{
							System.out.println("No records found numberOfUpdatedRows: "+numberOfUpdatedRows);
						}
						log.exiting(sourceClass, sourceMethod);
						dbConn.commit();
				}
			} catch (SQLException e) {
				// Prints the error message if something goes wrong when updating.
				log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				try {
					//Step 5:
					dbConn.rollback();
				} catch (SQLException e1) {
					System.out.println("The transaction will be rolled back because :" + e.getMessage());
				}

			} finally {
				try {
					//Step 6:
					dbConn.setAutoCommit(true);
					if (preparedStatement != null) {
						preparedStatement.close();
					}
					if (dbConn != null) {
						/*
						 * Since we are using a dbConn.commit() or
						 * dbConn.rollback() prior to the close so, the
						 * connection remains in progress when we try to close. This
						 * step will help to close the connection. It is in final
						 * block so that it always executes and the program doesn't
						 * throw any exception while closing connection.
						 */
						dbConn.close();
					}
				} catch (Exception e) {
					log.logp(Level.SEVERE, sourceClass, sourceMethod, e.getMessage(), e);
				}
			}
		}
	}
	
}
