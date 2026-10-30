package model;

import java.time.LocalDate;

public class Teacher extends Person {
    private String teacherID;

    public Teacher(String teacherID, String fullName, LocalDate dateOfBirth,String gender, String phone,  String email) {
        super(fullName, dateOfBirth, gender, phone, email);
    	this.teacherID = teacherID;
    }

    public Teacher(String teacherID, String fullName) {
        super(fullName, null, null, null, null);
    	this.teacherID = teacherID;
    }
    
    public Teacher(){
    	super();
    }
    
    @Override
    public String showInfo() {
    	return "Teacher's Information:" 
    		+ "\nFull name: " + getFullName()
    		+ "\nTeacher ID: " + getTeacherID()
    		+ "\nDate of birth: " + getDateOfBirth()
    		+ "\nGender: " + getGender()
    		+ "\nPhone: " + getPhone()
    		+ "\nEmail: " + getEmail();
    }
    
    public String getTeacherID() {
        return teacherID;
    }
    
    
    public void setTeacherID(String teacherID) {
    	this.teacherID = teacherID;
    }
}

