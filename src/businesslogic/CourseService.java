package businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import dataaccess.CourseDAO;
import model.Course;

public class CourseService {
	private final Connection conn;
	private final CourseDAO courseDAO;
	
	public CourseService(Connection conn) {
		this.conn = conn;
		this.courseDAO = new CourseDAO(conn);
	}
	
    public List<Course> retrieveAllCourses() { // list All Student
		return courseDAO.getAllCourses();
	}
    
    public boolean addCourse(Course course) { // insert
		if(course.getCourseID() == null || course.getCourseID().trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
		
		String cleanName = course.getCourseName().trim().replaceAll("\\s+", " ");
		
		if(cleanName == null || cleanName.isEmpty()) {
			throw new IllegalArgumentException("Course name must not be empty!");
		}
//		
		if(courseDAO.getCourseByID(course.getCourseID()) != null) {
			throw new DuplicateKeyException("Course ID already exists!");
		}
		
		course.setCourseName(cleanName);
		
		return courseDAO.insertCourse(course);
	}
    
    public boolean reDesignCourse(Course course) { // update
    	if(course.getCourseID() == null || course.getCourseID().trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
		
    	Course checkExisting = courseDAO.getCourseByID(course.getCourseID());
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Course ID does not exist!");
    	}

    	String cleanName = course.getCourseName().trim().replaceAll("\\s+", " ");
		
		if(cleanName == null || cleanName.isEmpty()) {
			throw new IllegalArgumentException("Course name must not be empty!");
		}
		
		course.setCourseName(cleanName);
		
		return courseDAO.updateCourse(course);
    	
    }
    
    public boolean removeCourse(String courseID) { // delete
    	if(courseID == null || courseID.trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
    	
    	Course checkExisting = courseDAO.getCourseByID(courseID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Course ID does not exist!");
    	}
    	
    	return courseDAO.deleteCourse(courseID);
    }
    
    public Course findCourseByID(String courseID) {
    	if(courseID == null || courseID.trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
    	
    	Course checkExisting = courseDAO.getCourseByID(courseID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Course ID does not exist!");
    	}
    	
    	return courseDAO.getCourseByID(courseID);		
	}
    
    public Course findCourseByCourseName(String courseName) {
    	if(courseName == null || courseName.trim().isEmpty()) {
			throw new IllegalArgumentException("Course Name must not be empty!");
		}
    	
    	Course checkExisting = courseDAO.getCourseByName(courseName);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Course Name does not exist!"
    										+ "\nPlease check spelling!");
    	}
    	
    	return checkExisting;
	}
}
