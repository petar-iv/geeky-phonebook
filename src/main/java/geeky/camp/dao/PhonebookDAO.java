package geeky.camp.dao;

import geeky.camp.structs.PhonebookRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class PhonebookDAO {

	public static List<PhonebookRecord> getAll() {
		List<PhonebookRecord> list = new LinkedList<PhonebookRecord>();

		try {
			Connection conn = ConnectionManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TEST.PHONEBOOK");
			while(rs.next()) {
				PhonebookRecord record = new PhonebookRecord();
				record.setId(rs.getInt("ID"));
				record.setFirstName(rs.getString("FIRST_NAME"));
				record.setLastName(rs.getString("LAST_NAME"));
				record.setPhone(rs.getString("PHONE"));
				list.add(record);
			};
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return list;
	}

	public static void create(PhonebookRecord record) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO TEST.PHONEBOOK VALUES (NULL, ?, ?, ?)");
			stmt.setString(1, record.getFirstName());
			stmt.setString(2, record.getLastName());
			stmt.setString(3, record.getPhone());
			int rowsAffected = stmt.executeUpdate();
			stmt.close();
			conn.close();
			if (rowsAffected != 1) {
				throw new RuntimeException("Saving a new entry affected " + rowsAffected + " rows");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void update(PhonebookRecord record) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE TEST.PHONEBOOK SET FIRST_NAME=?,LAST_NAME=?,PHONE=? WHERE ID=?");
			stmt.setString(1, record.getFirstName());
			stmt.setString(2, record.getLastName());
			stmt.setString(3, record.getPhone());
			stmt.setInt(4, record.getId());
			int rowsAffected = stmt.executeUpdate();
			stmt.close();
			conn.close();
			if (rowsAffected != 1) {
				throw new RuntimeException("Updating an existing entry affected " + rowsAffected + " rows");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void delete(int id) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM TEST.PHONEBOOK WHERE ID=?");
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			stmt.close();
			conn.close();
			if (rowsAffected != 1) {
				throw new RuntimeException("Deleting an existing entry affected " + rowsAffected + " rows");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
