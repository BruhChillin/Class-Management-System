package presentation.assignment;

import javax.swing.*;

import businesslogic.AssignmentService;
import businesslogic.CourseService;
import businesslogic.TeacherService;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteAssignmentForm extends JFrame {
	private TeacherService teacherService;
	private CourseService courseService;
    private AssignmentService assignmentService;

    private JTextField txtTeacherID;
    
    private JTextField txtCourseID;
    
    private AssignmentPanel classmanagementApp;
    

    public DeleteAssignmentForm(AssignmentPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	teacherService = new TeacherService(conn);
    	courseService = new CourseService(conn);
    	assignmentService = new AssignmentService(conn);
    	
        setTitle("Delete Assignment");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Teacher ID:"));
        txtTeacherID = new JTextField();
        add(txtTeacherID);
        
        add(new JLabel("Course ID:"));
        txtCourseID = new JTextField();
        add(txtCourseID);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFocusable(false);
        add(btnDelete);
        
        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        add(btnReset);

        btnDelete.addActionListener(e -> deleteAssignment());
        btnReset.addActionListener(e -> resetForm());
        
        setVisible(true);
    }

    private void deleteAssignment() {
        try {
            String teacherID = txtTeacherID.getText().trim();
            String courseID = txtCourseID.getText().trim();
            
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete Assignment: " + courseService.findCourseByID(courseID).getCourseName() + " by " + teacherService.findByID(teacherID).getFullName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            assignmentService.removeAssignment(teacherID, courseID);

            JOptionPane.showMessageDialog(this, "Assignment deleted successfully!");
            
            classmanagementApp.loadTable();
            
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Unexpected error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetForm() {
    	txtTeacherID.setText("");
        txtCourseID.setText("");
    }

}

