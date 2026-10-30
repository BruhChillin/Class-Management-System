package dataaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Assignment;

public class AssignmentDAO {
	private final Connection conn;

	private static final String SELECT_ALL = "SELECT * FROM Assignments";
	private static final String SELECT_TUPLE = "SELECT * FROM Assignments WHERE TeacherID = ? AND CourseID = ?";
	private static final String SELECT_MANY_TUPLE_BY_COURSE_ID = "SELECT * FROM Assignments WHERE CourseID = ?";
	private static final String SELECT_MANY_TUPLE_BY_TEACHER_ID = "SELECT * FROM Assignments WHERE TeacherID = ?";
	private static final String INSERT = "INSERT INTO Assignments (TeacherID, CourseID, Semester, AcademicYear) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE Assignments SET Semester = ?, AcademicYear = ?  WHERE TeacherID = ? AND CourseID = ?";
	private static final String DELETE = "DELETE FROM Assignments WHERE TeacherID = ? AND CourseID = ?";
	
	// --------------GET DATA FROM DATABASE-------------//
	public AssignmentDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Assignment> getAllAssignments() {
        List<Assignment> assignments = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL)){

            while (rs.next()) {
            	Assignment assignment = new Assignment();
            	assignment.setAssignmentID(rs.getInt("AssignmentID"));
            	assignment.setTeacherID(rs.getString("TeacherID"));
            	assignment.setCourseID(rs.getString("CourseID"));
            	assignment.setSemester(rs.getString("Semester"));
            	assignment.setAcademicYear(rs.getString("AcademicYear"));
            	assignments.add(assignment);
            }
        } catch (SQLException e) {
        	System.err.println("Error when getting list of assignments: " + e.getMessage());
        }
        return assignments;
    }
	
	// -----------FIND IN DATABASE BY ID -------------- //
	
	public Assignment getAssignmentByTeacherCourseID(String teacherID, String courseID) {
        Assignment assignment = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_TUPLE)){
             pstmt.setString(1, teacherID);
             pstmt.setString(2, courseID);
             try (ResultSet rs = pstmt.executeQuery()){ 

	            if (rs.next()) {
	            	assignment = new Assignment();
	            	assignment.setAssignmentID(rs.getInt("AssignmentID"));
	                assignment.setTeacherID(rs.getString("TeacherID"));
	                assignment.setCourseID(rs.getString("CourseID"));
	                assignment.setSemester(rs.getString("Semester"));
	                assignment.setAcademicYear(rs.getString("AcademicYear"));
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting an assignment by ID: " + e.getMessage());
        }
        return assignment;
	}
	
	public List<Assignment> getAssignmentsByTeacherID(String teacherID) {
		List<Assignment> assignments = new ArrayList<>();
        Assignment assignment = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_MANY_TUPLE_BY_TEACHER_ID)){
             pstmt.setString(1, teacherID);
             try (ResultSet rs = pstmt.executeQuery()){ 

	            while (rs.next()) {
	            	assignment = new Assignment();
	            	assignment.setAssignmentID(rs.getInt("AssignmentID"));
	                assignment.setTeacherID(rs.getString("TeacherID"));
	                assignment.setCourseID(rs.getString("CourseID"));
	                assignment.setSemester(rs.getString("Semester"));
	                assignment.setAcademicYear(rs.getString("AcademicYear"));
	                assignments.add(assignment);
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting an assignment by ID: " + e.getMessage());
        }
        return assignments;
	}
	
	public List<Assignment> getAssignmentsByCourseID(String courseID) {
		List<Assignment> assignments = new ArrayList<>();
        Assignment assignment = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_MANY_TUPLE_BY_COURSE_ID)){;
             pstmt.setString(1, courseID);
             try (ResultSet rs = pstmt.executeQuery()){ 

	            while (rs.next()) {
	            	assignment = new Assignment();
	            	assignment.setAssignmentID(rs.getInt("AssignmentID"));
	                assignment.setTeacherID(rs.getString("TeacherID"));
	                assignment.setCourseID(rs.getString("CourseID"));
	                assignment.setSemester(rs.getString("Semester"));
	                assignment.setAcademicYear(rs.getString("AcademicYear"));
	                assignments.add(assignment);
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting an assignment by ID: " + e.getMessage());
        }
        return assignments;
	}
	
	// ---------INSERT DATA INTO DATABASE----------- //
	
	public boolean insertAssignment(Assignment assignment) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT)){
            
            pstmt.setString(1, assignment.getTeacherID());
            pstmt.setString(2, assignment.getCourseID());
            pstmt.setString(3, assignment.getSemester());
            pstmt.setString(4, assignment.getAcademicYear());
            
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when adding assignment: " + e.getMessage());
        }
        return success;
    }
	
	// --------UPDATE DATA IN DATABASE----------- //
	
	public boolean updateAssignment(Assignment assignment) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE)){
            
            pstmt.setString(1, assignment.getSemester());
            pstmt.setString(2, assignment.getAcademicYear());
            pstmt.setString(3, assignment.getTeacherID());
            pstmt.setString(4, assignment.getCourseID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when updating assignment: " + e.getMessage());
        }
        return success;
    }
	
	// --------DELETE DATA IN DATABASE----------- //
	
	public boolean deleteAssignment(String teacherID, String courseID) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE)){
            
            pstmt.setString(1, teacherID);
            pstmt.setString(2, courseID);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when deleting assignment: " + e.getMessage());
        }
        return success;
	}
	
	
}
