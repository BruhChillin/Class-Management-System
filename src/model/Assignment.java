package model;

public class Assignment {
	private int assignmentID;
	private String teacherID;
	private String courseID;
	private String semester;
	private String academicYear;
    
    public Assignment(String teacherID, String courseID,  String semester, String academicYear) {
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public Assignment(){}
    
    public int getAssignmentID() {
        return assignmentID;
    }

    public String getTeacherID() {
        return teacherID;
    } 
    
    public String getCourseID() {
        return courseID;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }
    
    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
    
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
}
