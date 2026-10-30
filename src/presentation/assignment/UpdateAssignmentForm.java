package presentation.assignment;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import businesslogic.AssignmentService;
import businesslogic.CourseService;
import model.Assignment;
import model.Course;

public class UpdateAssignmentForm extends JFrame {

	private JTextField txtTeacherID;
    private JTextField txtCourseID;
    private JTextField txtSemester;
    private JTextField txtAcademicYear;
    
    private AssignmentPanel classmanagementApp;
    
    private CourseService courseService;
    private AssignmentService assignmentService;

    public UpdateAssignmentForm(AssignmentPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	courseService = new CourseService(conn);
    	assignmentService = new AssignmentService(conn);
    	
        setTitle("Update Assignment");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(15, 1, 5, 0));
        add(panel);

        // ===== Course Name And Teacher ID =====  
        panel.add(new JLabel("Teacher ID to update:"));
        txtTeacherID = new JTextField();
        panel.add(txtTeacherID);
        panel.add(new JLabel("Course ID to update:"));
        txtCourseID = new JTextField();
        panel.add(txtCourseID);
        
        JPanel loadPanel = new JPanel();
        JButton btnLoad = new JButton("Load");
        btnLoad.setFocusable(false);
        loadPanel.add(btnLoad);
        panel.add(loadPanel);
        
        // ===== Semester =====
        panel.add(new JLabel("Semester:"));
        txtSemester = new JTextField();
        panel.add(txtSemester);

        // ===== Academic Year =====
        panel.add(new JLabel("Academic Year:"));
        txtAcademicYear = new JTextField();
        panel.add(txtAcademicYear);
        
        // ===== BUTTON =====
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusable(false);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnUpdate);
        
        btnLoad.addActionListener(e -> loadAssignment(conn));
        btnUpdate.addActionListener(e -> updateAssignment(conn));

        panel.add(new JLabel()); // empty
        panel.add(btnPanel);

        setVisible(true);
    }

    private void loadAssignment(Connection conn) {
        try {
            String courseID = txtCourseID.getText().trim();
            
            String teacherID = txtTeacherID.getText().trim();

            Course course = courseService.findCourseByID(courseID);

            // Load data
            txtSemester.setText(assignmentService.findAssignmentByTeacherCourseID(teacherID, course.getCourseID()).getSemester());
            txtAcademicYear.setText(assignmentService.findAssignmentByTeacherCourseID(teacherID, course.getCourseID()).getAcademicYear());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateAssignment(Connection conn) {
        try {
        	String courseID = txtCourseID.getText().trim();
            String teacherID = txtTeacherID.getText().trim();
            String semester = txtSemester.getText().trim();
            String academicyear = txtAcademicYear.getText().trim();

            Assignment assignment = new Assignment(teacherID, courseService.findCourseByID(courseID).getCourseID(), semester, academicyear);

            assignmentService.reDesignAssignment(assignment);

            JOptionPane.showMessageDialog(this, "Assignment updated successfully!");
            
            classmanagementApp.loadTable();
            
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                                          "Unexpected error: " + ex.getMessage(), 
                                          "Error", 
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
