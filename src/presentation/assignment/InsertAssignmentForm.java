package presentation.assignment;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import businesslogic.DuplicateKeyException;
import businesslogic.AssignmentService;
import businesslogic.CourseService;
import model.Assignment;

public class InsertAssignmentForm extends JFrame {

    private JTextField txtTeacherID;
    private JTextField txtCourseID;
    private JTextField txtSemester;
    private JTextField txtAcademicYear;
    
    private AssignmentPanel classmanagementApp;
    
    private AssignmentService assignmentService;
    private CourseService courseService;

    public InsertAssignmentForm(AssignmentPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	assignmentService = new AssignmentService(conn);
    	courseService = new CourseService(conn);
    	
        setTitle("Insert Assignment");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(14, 1, 5, 0));
        add(panel);
        
        // ===== Teacher ID =====
        panel.add(new JLabel("Teacher ID:"));
        txtTeacherID = new JTextField();
        panel.add(txtTeacherID);
        
        // ===== Course ID =====
        panel.add(new JLabel("Course ID:"));
        txtCourseID = new JTextField();
        panel.add(txtCourseID);
        
        // ===== Semester =====
        panel.add(new JLabel("Semester:"));
        txtSemester = new JTextField();
        panel.add(txtSemester);
        
        // ===== Academic Year =====
        panel.add(new JLabel("Academic Year:"));
        txtAcademicYear = new JTextField();
        panel.add(txtAcademicYear);

        // ===== Insert Button =====
        JButton btnInsert = new JButton("Insert");
        btnInsert.setFocusable(false);
        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnInsert);
        btnPanel.add(btnReset);
        
        btnInsert.addActionListener(e -> insertAssignment());
        btnReset.addActionListener(e -> resetForm());
        
        panel.add(new JLabel()); // empty placeholder
        panel.add(btnPanel);

        setVisible(true);
    }
    
    private void insertAssignment() {
        try {
            String teacherID = txtTeacherID.getText().trim();
            String courseID = txtCourseID.getText().trim();
            String semester = txtSemester.getText().trim();
            String academicYear = txtAcademicYear.getText().trim();

            Assignment assignment = new Assignment(teacherID, courseID, semester, academicYear);

            assignmentService.addAssignment(assignment);

            JOptionPane.showMessageDialog(this, "Assignment inserted successfully!");
            
            classmanagementApp.loadTable();
            
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (DuplicateKeyException ex) {
        	JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetForm() {
    	txtTeacherID.setText("");
        txtCourseID.setText("");
        txtSemester.setText("");
        txtAcademicYear.setText("");
    }
}
