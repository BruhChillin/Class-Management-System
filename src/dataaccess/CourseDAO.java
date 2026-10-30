package dataaccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Course;

public class CourseDAO {
	private final Connection conn;

	private static final String SELECT_ALL = "SELECT * FROM Courses";
	private static final String SELECT_TUPLE_BY_ID = "SELECT * FROM Courses WHERE CourseID = ?";
	private static final String SELECT_TUPLE_BY_COURSE_NAME = "SELECT * FROM Courses WHERE CourseName = ?";
	private static final String INSERT = "INSERT INTO Courses (CourseID, CourseName, Credits) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE Courses SET CourseName = ?, Credits = ? WHERE CourseID = ?";
	private static final String DELETE = "DELETE FROM Courses WHERE CourseID = ?";
	
	// --------------GET DATA FROM DATABASE-------------//
	
	public CourseDAO(Connection conn) {
		this.conn = conn;

	}
	
	public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL)){

            while (rs.next()) {
            	Course course = new Course();
                course.setCourseID(rs.getString("CourseID"));
                course.setCourseName(rs.getString("CourseName"));
                course.setCredit(rs.getInt("Credits"));
                courses.add(course);
            }
        } catch (SQLException e) {
        	System.err.println("Error when getting list of courses: " + e.getMessage());
        }
        return courses;
    }
	
	// -----------FIND IN DATABASE BY ID, Course Name -------------- //
	
	public Course getCourseByID(String courseID) {
        Course course = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_TUPLE_BY_ID)){
             pstmt.setString(1, courseID);
             try (ResultSet rs = pstmt.executeQuery()){ 

	            if (rs.next()) {
	            	course = new Course();
	                course.setCourseID(rs.getString("CourseID"));
	                course.setCourseName(rs.getString("CourseName"));
	                course.setCredit(rs.getInt("Credits"));
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting a course by ID: " + e.getMessage());
        }
        return course;
	}
	
	public Course getCourseByName(String courseName) {
        Course course = null;
        
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_TUPLE_BY_COURSE_NAME)){
             pstmt.setString(1, courseName);
             try (ResultSet rs = pstmt.executeQuery()){ 

	            if (rs.next()) {
	            	course = new Course();
	                course.setCourseID(rs.getString("CourseID"));
	                course.setCourseName(rs.getString("CourseName"));
	                course.setCredit(rs.getInt("Credits"));
	            }
             }
        } catch (SQLException e) {
        	System.err.println("Error when getting a course by ID: " + e.getMessage());
        }
        return course;
	}
	
	
	// ---------INSERT DATA INTO DATABASE----------- //
	
	public boolean insertCourse(Course course) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT)){            
            pstmt.setString(1, course.getCourseID());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredit());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when adding course: " + e.getMessage());
        }
        return success;
    }
	
	// --------UPDATE DATA IN DATABASE----------- //
	
	public boolean updateCourse(Course course) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE)){
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getCredit());
            pstmt.setString(3, course.getCourseID());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when updating course: " + e.getMessage());
        }
        return success;
    }
	
	// --------DELETE DATA IN DATABASE----------- //
	
	public boolean deleteCourse(String courseID) {
        boolean success = false;
        
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE)){
            
            pstmt.setString(1, courseID);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("Error when deleting course: " + e.getMessage());
        }
        return success;
	}
	
	
}
