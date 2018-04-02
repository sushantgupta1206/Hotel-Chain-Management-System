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
	 * @param hotelData
	 * @param dbFlag
	 * @return
	 */
	public int addHotel(Hotel hotelData, int dbFlag){
		String sourceMethod = "addHotel";
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


	
	/**Show all Hotel records.
	 * @param dbFlag
	 */
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


	
	/**Show Hotel record By Id.
	 * @param hotelData
	 * @param dbFlag
	 */
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


	
	/**Update Hotel Record by ID
	 * @param hotelData
	 * @param oldHotelId
	 * @param dbFlag
	 */
	public void updateHotel(Hotel hotelData, int oldHotelId, int dbFlag) {
		String sourceMethod = "updateHotel";
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


	
	/**Delete Hotel Record by ID
	 * @param hotelData
	 * @param dbFlag
	 */
	public void deleteHotel(Hotel hotelData, int dbFlag) {
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

	
	/**Insert Room record
	 * @param room
	 * @param dbFlag
	 * @return
	 */
	public int addRoom(Room room, int dbFlag) {
		String sourceMethod = "addRoom";
		String insertHotelDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".ROOMS ( ROOM_NO, HOTEL_ID, MAX_OCCUPANCY, NIGHTLY_RATE ) VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int generatedKey = 0;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertHotelDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, room.getRoomNum());
			preparedStatement.setInt(2, room.getHotelId());
			preparedStatement.setInt(3, room.getMaxOccu());
			preparedStatement.setInt(4, room.getNightRate());
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    generatedKey = room.getRoomNum();
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

	
	/**Update Room record by Room Num and Hotel Id
	 * @param room
	 * @param oldRoomNum
	 * @param oldHotelId
	 * @param dbFlag
	 */
	public void updateRoom(Room room, int oldRoomNum, int oldHotelId, int dbFlag) {
		String sourceMethod = "updateRoom";
		String updateDeleteRequestStatement = "UPDATE "+DBConnectUtils.DBSCHEMA+".ROOMS SET ROOM_NO = ?, HOTEL_ID = ?,MAX_OCCUPANCY=?, NIGHTLY_RATE=?, AVAILABILITY=? WHERE ROOM_NO = ? AND HOTEL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfUpdatedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, room.getRoomNum());
			preparedStatement.setInt(2, room.getHotelId());
			preparedStatement.setInt(3, room.getMaxOccu());
			preparedStatement.setInt(4, room.getNightRate());
			preparedStatement.setInt(5, room.getAvailability());
			preparedStatement.setInt(6, oldRoomNum);
			preparedStatement.setInt(7, oldHotelId);
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

	
	/**Show All Rooms
	 * @param dbFlag
	 */
	public void showRooms(int dbFlag) {
		String sourceMethod = "showRooms";
		List<Room> roomDetails = new ArrayList<Room>();
		ResultSet selectQueryRS = null;
		Connection dbConn = null;
		Statement stmt = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			stmt = dbConn.createStatement();
			String selectStatement = "SELECT * FROM "+DBConnectUtils.DBSCHEMA+".ROOMS";
			selectQueryRS = stmt.executeQuery(selectStatement);
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

	
	
	/**Delete room by room num and hotel id
	 * @param room
	 * @param dbFlag
	 */
	public void deleteRoom(Room room, int dbFlag) {
		String sourceMethod = "deleteRoom";
		String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".ROOMS WHERE ROOM_NO = ? AND HOTEL_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfDeletedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, room.getRoomNum());
			preparedStatement.setInt(2, room.getHotelId());
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

	
	/**Insert staff
	 * @param staff
	 * @param dbFlag
	 * @return
	 */
	public int addStaff(Staff staff, int dbFlag) {
		String sourceMethod = "addStaff";
		String insertDataQuery = " INSERT INTO "+DBConnectUtils.DBSCHEMA+".STAFF (STAFF_ID, PHONE, NAME, ADDRESS, DOB, DEPARTMENT, TITLE, AGE) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int generatedKey = 0;
		Connection dbConn = null;
		ResultSet rs = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(insertDataQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, staff.getStaffId());
			preparedStatement.setString(2, staff.getPhone());
			preparedStatement.setString(3, staff.getName());
			preparedStatement.setString(4, staff.getAddress());
			preparedStatement.setDate(5, staff.getDob());
			preparedStatement.setString(6, staff.getDepartment());
			preparedStatement.setString(7, staff.getTitle());
			preparedStatement.setInt(8, staff.getAge());
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    generatedKey = staff.getStaffId();
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



	/**Delete staff record by staff id 
	 * @param staff
	 * @param dbFlag
	 */
	public void deleteStaff(Staff staff, int dbFlag) {
		String sourceMethod = "deleteStaff";
		String updateDeleteRequestStatement = "DELETE FROM "+DBConnectUtils.DBSCHEMA+".STAFF WHERE STAFF_ID = ?";
		PreparedStatement preparedStatement = null;
		int numberOfDeletedRows = 0;
		Connection dbConn = null;
		try {
			dbConn = dbUtil.getConnection(dbFlag);
			preparedStatement = dbConn.prepareStatement(updateDeleteRequestStatement);
			preparedStatement.setInt(1, staff.getStaffId());
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



	public void updateStaff(Staff staff, int oldStaffId, int dbFlag) {
		String sourceMethod = "updateRoom";
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
