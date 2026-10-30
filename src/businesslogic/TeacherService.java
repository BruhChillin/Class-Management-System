package businesslogic;

import java.sql.Connection;
import java.util.List;
import dataaccess.TeacherDAO;
import model.Teacher;

public class TeacherService implements GenericService<Teacher, String> {
	private final Connection conn;
	private final TeacherDAO teacherDAO;
	
	public TeacherService(Connection conn) {
		this.conn = conn;
		this.teacherDAO = new TeacherDAO(conn);
	}
	
	@Override
    public List<Teacher> retrieveAll() { // list All Student
		return teacherDAO.getAll();
	}
    
	@Override
    public boolean add(Teacher teacher) { // insert
		if(teacher.getTeacherID() == null || teacher.getTeacherID().trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
		
		String cleanName = teacher.getFullName().trim().replaceAll("\\s+", " ");
		
		if(cleanName == null || cleanName.isEmpty()) {
			throw new IllegalArgumentException("Full name must not be empty!");
		}
		
		if(teacherDAO.getByID(teacher.getTeacherID()) != null) {
			throw new DuplicateKeyException("Teacher ID already exists!");
		}
		
		teacher.setFullName(cleanName);
		
		return teacherDAO.insert(teacher);
	}
    
	@Override
    public boolean redesign(Teacher teacher) { // update
    	if(teacher.getTeacherID() == null || teacher.getTeacherID().trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
		
    	Teacher checkExisting = teacherDAO.getByID(teacher.getTeacherID());
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Teacher ID does not exist!");
    	}

    	String cleanName = teacher.getFullName().trim().replaceAll("\\s+", " ");
		
		if(cleanName == null || cleanName.isEmpty()) {
			throw new IllegalArgumentException("Full name must not be empty!");
		}
		
		teacher.setFullName(cleanName);
		
		return teacherDAO.update(teacher);
    	
    }
    
	@Override
    public boolean remove(String teacherID) { // delete
    	if(teacherID == null || teacherID.trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
    	Teacher checkExisting = teacherDAO.getByID(teacherID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Teacher ID does not exist!");
    	}
    	
    	return teacherDAO.delete(teacherID);
    }
    
	@Override
    public Teacher findByID(String teacherID) {
    	if(teacherID == null || teacherID.trim().isEmpty()) {
			throw new IllegalArgumentException("Teacher ID must not be empty!");
		}
    	
    	Teacher checkExisting = teacherDAO.getByID(teacherID);
    	if (checkExisting == null) {
    		throw new IllegalArgumentException("Teacher ID does not exist!");
    	}
    	
    	return checkExisting;
	}
}
