package dataaccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Student;

public class StudentDAO implements GenericDAO<Student, String> {
	private final Connection conn;
	
	private static final String SELECT_ALL = "SELECT * FROM Students";
	private static final String SELECT_TUPLE = "SELECT * FROM Students WHERE StudentID = ?";
	private static final String INSERT = "INSERT INTO Students (StudentID, FullName, DateOfBirth, Gender, Phone, Email) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE Students SET FullName = ?, DateOfBirth = ?, Gender = ?, Phone = ?, Email = ? WHERE StudentID = ?";
	private static final String DELETE = "DELETE FROM Students WHERE StudentID = ?";
	
	// --------------GET DATA FROM DATABASE-------------//
	
	public StudentDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(SELECT_ALL)) {;
        	
	        while (rs.next()) {
	        	Student student = new Student();
	            student.setStudentID(rs.getString("StudentID"));
	            student.setFullName(rs.getString("FullName"));
	            student.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
	            student.setGender(rs.getString("Gender"));
	            student.setPhone(rs.getString("Phone"));
	            student.setEmail(rs.getString("Email"));
	            students.add(student);
	        }
        } catch (SQLException e) {
        	System.err.println("Error when getting list of students: " + e.getMessage());
        }
        return students;
    }
	
	// -----------FIND IN DATABASE BY ID -------------- //
	
	@Override
	public Student getByID(String studentID) {
        Student student = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_TUPLE)){
             pstmt.setString(1, studentID);
             try (ResultSet rs = pstmt.executeQuery()) { 

	            if (rs.next()) {
	            	student = new Student();
	                student.setStudentID(rs.getString("StudentID"));
	                student.setFullName(rs.getString("FullName"));
	                student.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
	                student.setGender(rs.getString("Gender"));
	                student.setPhone(rs.getString("Phone"));
	                student.setEmail(rs.getString("Email"));
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting a student by ID: " + e.getMessage());
        }
        return student;
	}
	
	// ---------INSERT DATA INTO DATABASE----------- //
	
	@Override
	public boolean insert(Student student) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT)){
            pstmt.setString(1, student.getStudentID());
            pstmt.setString(2, student.getFullName());
            pstmt.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));
            pstmt.setString(4, student.getGender());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getEmail());
            
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when adding student: " + e.getMessage());
        }
        return success;
    }
	
	// --------UPDATE DATA IN DATABASE----------- //
	
	@Override
	public boolean update(Student student) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE)){
            
            pstmt.setString(1, student.getFullName());
            pstmt.setDate(2, java.sql.Date.valueOf(student.getDateOfBirth()));
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getPhone());
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getStudentID());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when updating student: " + e.getMessage());
        }
        return success;
    }
	
	// --------DELETE DATA IN DATABASE----------- //
	
	@Override
	public boolean delete(String studentID) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE)){
            
            pstmt.setString(1, studentID);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when deleting student: " + e.getMessage());
        }
        return success;
	}
}
