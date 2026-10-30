package model;

import java.time.LocalDate;

public class Student extends Person {
    private String studentID;

    public Student(String studentID, String fullName, LocalDate dateOfBirth, String gender, String phone,  String email) {
        super(fullName, dateOfBirth, gender, phone, email);
    	this.studentID = studentID;        
    }
    
    public Student(String studentID, String fullName) {
        super(fullName, null, null, null, null);
    	this.studentID = studentID;        
    }
    
    public Student() {
    	super();
    }
    
    @Override
    public String showInfo() {
    	return "Student's Information:" 
    	+ "\nFull name: " + getFullName()
    	+ "\nStudent ID: " + getStudentID()
    	+ "\nDate of birth: " + getDateOfBirth()
    	+ "\nGender: " + getGender()
    	+ "\nPhone: " + getPhone()
    	+ "\nEmail: " + getEmail();
    }
    
    public String getStudentID() {
        return studentID;
    }
    
    public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
    
}
