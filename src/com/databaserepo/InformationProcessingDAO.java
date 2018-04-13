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

import com.dataobject.Assigns;
import com.dataobject.Customer;
import com.dataobject.Hotel;
import com.dataobject.Room;
import com.dataobject.Staff;

/**
 * @author Kartik Shah
 *
 */
public class InformationProcessingDAO {
	private DBConnectUtils dbUtil = new DBConnectUtils();
	private static String sourceClass = InformationProcessingDAO.class.getName();
	private static Logger log = Logger.getLogger(sourceClass);
	
	
	
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
				System.out.println("Hotel record: "+ hotelId+" inserted. . Query executed :"+insertHotelDataQuery);
			} catch (Exception e) {
				System.out.println("Hotel record: "+ hotelId+" didn't inserted. query executed :"+insertHotelDataQuery);
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


	
	/**Show Hotel record By Id.
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
				System.out.println("No hotel found");
				return false;
			}else{
				System.out.println("Hotel found");
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

	
	/**Insert Room record
	 * @param room
	 * @param dbFlag
	 * @return
	 */
	@SuppressWarnings("resource")
	public void addRoom(int roomNo, int hotelId, int maxOccu, int nightRate, int roomCat, int dbFlag) {
		String sourceMethod = "addRoom";
		if(!showRoom(roomNo, hotelId, dbFlag) && showRoomCatgeory(roomCat, dbFlag) && showHotel(hotelId, dbFlag)){
				String insertRoomDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".ROOMS ( ROOM_NO, HOTEL_ID, MAX_OCCUPANCY, NIGHTLY_RATE,AVAILABILITY ) VALUES (?,?,?,?,1)";
				PreparedStatement preparedStatement = null;
				Connection dbConn = null;
				ResultSet rs = null;
			try {
				dbConn = dbUtil.getConnection(dbFlag);
				dbConn.setAutoCommit(false);
				preparedStatement = dbConn.prepareStatement(insertRoomDataQuery);
				preparedStatement.setInt(1, roomNo);
				preparedStatement.setInt(2, hotelId);
				preparedStatement.setInt(3, maxOccu);
				preparedStatement.setInt(4, nightRate);
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

	
	/**Update Room record by Room Num and Hotel Id
	 * @param roomNo
	 * @param hotelId
	 * @param maxOccu
	 * @param nightRate
	 * @param setAvailability
	 * @param oldRoomNum
	 * @param oldHotelId
	 * @param dbFlag
	 */
	public void updateRoom(int roomNo,int hotelId,int maxOccu,int nightRate,int setAvailability, int oldRoomNum, int oldHotelId, int dbFlag) {
		String sourceMethod = "updateRoom";
		String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOMS SET ROOM_NO = ?, HOTEL_ID = ?,MAX_OCCUPANCY=?, NIGHTLY_RATE=?, AVAILABILITY=? WHERE ROOM_NO = ? AND HOTEL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfUpdatedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, roomNo);
			preparedStatement.setInt(2, hotelId);
			preparedStatement.setInt(3, maxOccu);
			preparedStatement.setInt(4, nightRate);
			preparedStatement.setInt(5, setAvailability);
			preparedStatement.setInt(6, oldRoomNum);
			preparedStatement.setInt(7, oldHotelId);
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

	
	/**Insert staff
	 * @param staff
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	@SuppressWarnings("resource")
	public void addStaffToHotel(Staff staff, int hotelId, int dbFlag) {
		String sourceMethod = "addStaff";
		if(!showStaff(staff.getStaffId(), dbFlag) && showHotel(hotelId, dbFlag)){
			PreparedStatement preparedStatement = null;
			Connection dbConn = null;
			ResultSet rs = null;
			try {
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
	public int addCustomer(Customer customer, int dbFlag) {
		String sourceMethod = "addCustomer";
		String insertDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".CUSTOMER (CUSTOMER_ID, PHONE, NAME, EMAIL_ADDRESS, DOB) VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int generatedKey = 0;
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
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    generatedKey = customer.getCustomerId();
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



	/**Delete customer by customer id
	 * @param customer
	 * @param dbFlag
	 */
	public void deleteCustomer(Customer customer, int dbFlag) {
		String sourceMethod = "deleteCustomer";
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



	/**Update Customer record by customer id
	 * @param customer
	 * @param oldCustomerId
	 * @param dbFlag
	 */
	public void updateCustomer(Customer customer, int oldCustomerId, int dbFlag) {
		String sourceMethod = "updateCustomer";
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
	
	/**Pass roomNo and hotelId as argument and get response as room type is available or not
	 * @param roomNo
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	public String checkRoomAvailability(int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "checkRoomAvailability";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		String response = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			String selectStatement = "SELECT (CASE WHEN AVAILABILITY=0 THEN 'FALSE' ELSE 'TRUE' END) AS AVAILABILITY FROM "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ROOM_NO=? AND HOTEL_ID=?";
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, roomNo);
			stmt.setInt(2, hotelId);
			selectQueryRS = stmt.executeQuery();
			while (selectQueryRS.next()) {
				response = selectQueryRS.getString("AVAILABILITY");
				System.out.println(response);
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
		return response;
		
	}



	/**Pass roomType and hotelId as argument and get response as room type is available or not
	 * @param roomType
	 * @param hotelId
	 * @param dbFlag
	 * @return
	 */
	public String checkRoomTypeAvailability(String roomType, int hotelId, int dbFlag) {
		String sourceMethod = "checkRoomTypeAvailability";
		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		String response = null;
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
			while (selectQueryRS.next()) {
				response = selectQueryRS.getString("ROOM_NO");
				System.out.println(response);
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
		return response;
	}



	public String setRoomAvailability(int availability, int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "setRoomAvailability";
		String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOMS SET AVAILABILITY=? WHERE ROOM_NO=? AND HOTEL_ID=?";
		PreparedStatement preparedStatement = null;
		int numberOfUpdatedRows = 0;
		String responsenumberOfUpdatedRows = null;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
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
	
	@SuppressWarnings("resource")
	public void assignRoomAndSetAvailability(int staffId, int customerId, int noOfGuests, int roomNo, int hotelId, int dbFlag){
		String sourceMethod = "assignRoomAndSetAvailability";

		PreparedStatement stmt = null;
		Connection dbConn = null;
		ResultSet selectQueryRS = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			final int previousIsolationLevel = dbConn.getTransactionIsolation();
			if (previousIsolationLevel != 2) {
				dbConn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			dbConn.setAutoCommit(false);
			String selectStatement = "SELECT * FROM " + DBConnectUtils.DBSCHEMA
					+ ".ROOMS WHERE ROOM_NO=? AND HOTEL_ID=? AND MAX_OCCUPANCY>=? AND AVAILABILITY=0";
			System.out.println(selectStatement);
			stmt = dbConn.prepareStatement(selectStatement);
			stmt.setInt(1, roomNo);
			stmt.setInt(2, hotelId);
			stmt.setInt(3, noOfGuests);
			selectQueryRS = stmt.executeQuery();
			
			while (selectQueryRS.next()) {
				String response = selectQueryRS.getString("ROOM_NO");
				System.out.println(response);
			}

			String insertDataQuery = "INSERT INTO " + DBConnectUtils.DBSCHEMA
					+ ".ASSIGNS(STAFF_ID, CUSTOMER_ID,CHECK_IN,CHECK_OUT, NO_OF_GUESTS, HOTEL_ID, ROOM_NO) "
					+ "SELECT ?,?,'2017-09-15 02:15:00','2017-09-15 02:15:00', ?,?,? FROM  " + DBConnectUtils.DBSCHEMA
					+ ".ROOMS AS R WHERE R.ROOM_NO=? AND HOTEL_ID=? AND " + "MAX_OCCUPANCY>=? AND AVAILABILITY=0";

			int generatedKey = 0;

			preparedStatement = dbConn.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, staffId);
			preparedStatement.setInt(2, customerId);
			preparedStatement.setInt(3, noOfGuests);
			preparedStatement.setInt(4, hotelId);
			preparedStatement.setInt(5, roomNo);
			preparedStatement.setInt(6, roomNo);
			preparedStatement.setInt(7, hotelId);
			preparedStatement.setInt(8, noOfGuests);
			System.out.println(insertDataQuery);
			int insertExecuted = preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				System.out.println(generatedKey);
				//insertExecuted = true;
			}

				String updateDeleteRequestStatement = "UPDATE " + DBConnectUtils.DBSCHEMA
						+ ".ROOMS SET AVAILABILITY=1 WHERE ROOM_NO=? AND HOTEL_ID=?";
				preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
				preparedStatement.setInt(1, roomNo);
				preparedStatement.setInt(2, hotelId);
				int numberOfUpdatedRows = preparedStatement.executeUpdate();
				System.out.println(numberOfUpdatedRows);
				log.exiting(sourceClass, sourceMethod, generatedKey);
				dbConn.commit();
				
		} catch (SQLException e) {
			try {
				dbConn.rollback();
			} catch (SQLException e1) {
				System.out.println("The transaction will be rolled back because :" + e.getMessage());
			}
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



	public void assignRoom(int staffId, int customerId, int noOfGuests, int roomNo, int hotelId, int dbFlag) {
		String sourceMethod = "assignRoom";	
		assignRoomAndSetAvailability(staffId, customerId, noOfGuests, roomNo, hotelId, dbFlag);
	}
	
}
