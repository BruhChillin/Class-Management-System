package businesslogic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import dataaccess.StudentDAO;
import model.Student;

public class StudentService implements GenericService<Student, String> {
	private final Connection conn;
	private final StudentDAO studentDAO;
	
	public StudentService(Connection conn) {
		this.conn = conn;
		this.studentDAO = new StudentDAO(conn);
	}
	
    @Override
    public List<Student> retrieveAll() { // list All Student
		return studentDAO.getAll();
	}
    
    @Override
    public boolean add(Student student) { // insert
		if(student.getStudentID() == null || student.getStudentID().trim().isEmpty()) {
			throw new IllegalArgumentException("Student ID must not be empty!");
		}
		
		String cleanName = student.getFullName().trim().replaceAll("\\s+", " ");
		
		if(cleanName == null || cleanName.isEmpty()) {
			throw new IllegalArgumentException("Full name must not be empty!");
		}
		
		if(studentDAO.getByID(student.getStudentID()) != null) {
			throw new DuplicateKeyException("Student ID already exists!");
		}
		
		student.setFullName(cleanName);
		
		return studentDAO.insert(student);
	}
    
    @Override
    public boolean redesign(Student student) { // update
    	if(student.getStudentID() == null || student.getStudentID().trim().isEmpty()) {
			throw new IllegalArgumentException("Student ID must not be empty!");
		}
    	
    	Student checkExisting = studentDAO.getByID(student.getStudentID());
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Student ID does not exist!");
    	}
    	
    	String cleanName = student.getFullName().trim().replaceAll("\\s+", " ");
		
		if(cleanName == null || cleanName.isEmpty()) {
			throw new IllegalArgumentException("Full name must not be empty!");
		}
		
		student.setFullName(cleanName);
		
		return studentDAO.update(student);
    	
    }
    
    @Override
    public boolean remove(String studentID) { // delete
    	if(studentID == null || studentID.trim().isEmpty()) {
			throw new IllegalArgumentException("Student ID must not be empty!");
		}
    	
    	Student checkExisting = studentDAO.getByID(studentID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Student ID does not exist!");
    	}
    	
    	return studentDAO.delete(studentID);
    }
    
    @Override
    public Student findByID(String studentID) {
    	if(studentID == null || studentID.trim().isEmpty()) {
			throw new IllegalArgumentException("Student ID must not be empty!");
		}
    	
    	Student checkExisting = studentDAO.getByID(studentID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Student ID does not exist!");
    	}
    	return checkExisting;
	}
	
}
