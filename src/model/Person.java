package model;

import java.time.LocalDate;

public abstract class Person {
	private String fullName;
	private LocalDate dateOfBirth;
	private String gender;
	private String phone;
	private String email;
    
    public Person(String fullName, LocalDate dateOfBirth, String gender, String phone,  String email) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }

    public Person(){}
    
    public abstract String showInfo();
        
    public String getFullName() {
        return fullName;
    }
       
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }    
    
    public String getGender() {
        return gender;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getEmail() {
        return email;
    }

    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
