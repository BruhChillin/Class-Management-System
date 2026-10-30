package businesslogic;

import java.sql.Connection;
import java.util.List;
import dataaccess.AssignmentDAO;
import dataaccess.TeacherDAO;
import dataaccess.CourseDAO;
import model.Assignment;

public class AssignmentService {
	private final Connection conn;
	private final AssignmentDAO assignmentDAO;
	private final TeacherDAO teacherDAO;
	private final CourseDAO courseDAO;
	
	
	public AssignmentService(Connection conn) {
		this.conn = conn;
		this.assignmentDAO = new AssignmentDAO(conn);
		this.teacherDAO = new TeacherDAO(conn);
		this.courseDAO = new CourseDAO(conn);
	}
	
    public List<Assignment> retrieveAllAssignments() { // list All
		return assignmentDAO.getAllAssignments();
	}
    
    public boolean addAssignment(Assignment assignment) { // insert
		if(assignment.getTeacherID() == null || assignment.getTeacherID().trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
		
		if(assignment.getCourseID() == null || assignment.getCourseID().trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
		
		if(teacherDAO.getByID(assignment.getTeacherID()) == null || courseDAO.getCourseByID(assignment.getCourseID()) == null) {
			throw new IllegalArgumentException("Referential integrity violation!");
		}
		
		Assignment checkExisting = assignmentDAO.getAssignmentByTeacherCourseID(assignment.getTeacherID(), assignment.getCourseID());
    	if (checkExisting != null) {
    		throw new DuplicateKeyException("Assignment already exists!");
    	}
		
		return assignmentDAO.insertAssignment(assignment);
	}
    
    public boolean reDesignAssignment(Assignment assignment) { // update
    	if(assignment.getTeacherID() == null || assignment.getTeacherID().trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
		if(assignment.getCourseID() == null || assignment.getCourseID().trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
		
		if(courseDAO.getCourseByID(assignment.getCourseID()) == null || teacherDAO.getByID(assignment.getTeacherID()) == null) {
			throw new IllegalArgumentException("Referential integrity violation!");
		}
		
    	Assignment checkExisting = assignmentDAO.getAssignmentByTeacherCourseID(assignment.getTeacherID(), assignment.getCourseID());
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Assignment does not exist!");
    	}
		
		return assignmentDAO.updateAssignment(assignment);
    	
    }
    
    public boolean removeAssignment(String teacherID, String courseID) { // delete
    	if(teacherID == null || teacherID.trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
    	if(courseID == null || courseID.trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
    	
    	Assignment checkExisting = assignmentDAO.getAssignmentByTeacherCourseID(teacherID, courseID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Assignment does not exist!");
    	}
    	
    	return assignmentDAO.deleteAssignment(teacherID, courseID);
    }
    
    public Assignment findAssignmentByTeacherCourseID(String teacherID, String courseID) {
    	if(teacherID == null || teacherID.trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
    	if(courseID == null || courseID.trim().isEmpty()) {
			throw new IllegalArgumentException("Course ID must not be empty!");
		}
    	
    	Assignment checkExisting = assignmentDAO.getAssignmentByTeacherCourseID(teacherID, courseID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Assignment does not exist!");
    	}
		
    	return assignmentDAO.getAssignmentByTeacherCourseID(teacherID, courseID);
	}
    
    public List<Assignment> findAssignmentsByTeacherID(String teacherID) {
    	if(teacherID == null || teacherID.trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
    	List<Assignment> checkExisting = assignmentDAO.getAssignmentsByTeacherID(teacherID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Teacher ID does not exist!");
    	}
    	
		return assignmentDAO.getAssignmentsByTeacherID(teacherID);
	}
    
    public List<Assignment> findAssignmentsByCourseID(String courseID) {
    	if(courseID == null || courseID.trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
    	List<Assignment> checkExisting = assignmentDAO.getAssignmentsByCourseID(courseID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Teacher ID does not exist!");
    	}
    	
		return checkExisting;
	}
    
}
