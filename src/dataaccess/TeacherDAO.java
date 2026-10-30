package dataaccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Teacher;

public class TeacherDAO implements GenericDAO<Teacher, String> {
	private final Connection conn;

	private static final String SELECT_ALL = "SELECT * FROM Teachers";
	private static final String SELECT_TUPLE = "SELECT * FROM Teachers WHERE TeacherID = ?";
	private static final String INSERT = "INSERT INTO Teachers (TeacherID, FullName, DateOfBirth, Gender, Phone, Email) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE Teachers SET FullName = ?, DateOfBirth = ?, gender = ?, Phone = ?, Email = ? WHERE TeacherID = ?";
	private static final String DELETE = "DELETE FROM Teachers WHERE TeacherID = ?";
	
	// --------------GET DATA FROM DATABASE-------------//
	
	public TeacherDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL)){ 

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherID(rs.getString("TeacherID"));
                teacher.setFullName(rs.getString("FullName"));
                teacher.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                teacher.setGender(rs.getString("Gender"));
                teacher.setPhone(rs.getString("Phone"));
                teacher.setEmail(rs.getString("Email"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
        	System.err.println("Error when getting list of teachers: " + e.getMessage());
        }
        return teachers;
    }
	
	// -----------FIND IN DATABASE BY ID -------------- //
	
	@Override
	public Teacher getByID(String teacherID) {
        Teacher teacher = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_TUPLE)){
             
             pstmt.setString(1, teacherID);
             try (ResultSet rs = pstmt.executeQuery()){ 

	            if (rs.next()) {
	            	teacher = new Teacher();
	                teacher.setTeacherID(rs.getString("TeacherID"));
	                teacher.setFullName(rs.getString("FullName"));
	                teacher.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
	                teacher.setGender(rs.getString("Gender"));
	                teacher.setPhone(rs.getString("Phone"));
	                teacher.setEmail(rs.getString("Email"));
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting a teacher by ID: " + e.getMessage());
        }
        return teacher;
	}
	
	
	// ---------INSERT DATA INTO DATABASE----------- //
	
	@Override
	public boolean insert(Teacher teacher) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT)){;
            
            pstmt.setString(1, teacher.getTeacherID());
            pstmt.setString(2, teacher.getFullName());
            pstmt.setDate(3, java.sql.Date.valueOf(teacher.getDateOfBirth()));
            pstmt.setString(4, teacher.getGender());
            pstmt.setString(5, teacher.getPhone());
            pstmt.setString(6, teacher.getEmail());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when adding teacher: " + e.getMessage());
        }
        return success;
    }
	
	// --------UPDATE DATA IN DATABASE----------- //
	
	@Override
	public boolean update(Teacher teacher) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE)){
            
            pstmt.setString(1, teacher.getFullName());
            pstmt.setDate(2, java.sql.Date.valueOf(teacher.getDateOfBirth()));
            pstmt.setString(3, teacher.getGender());
            pstmt.setString(4, teacher.getPhone());
            pstmt.setString(5, teacher.getEmail());
            pstmt.setString(6, teacher.getTeacherID());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when updating teacher: " + e.getMessage());
        }
        return success;
    }
	
	// --------DELETE DATA IN DATABASE----------- //
	
	@Override
	public boolean delete(String teacherID) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE)){
            
            pstmt.setString(1, teacherID);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when deleting teacher: " + e.getMessage());
        }
        return success;
	}
	
}
