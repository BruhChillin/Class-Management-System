package presentation.course;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import businesslogic.DuplicateKeyException;
import businesslogic.CourseService;
import model.Course;

public class InsertCourseForm extends JFrame {

    private JTextField txtCourseName;
    private JTextField txtCourseID;
    private JTextField txtCredit;
    
    private CoursePanel classmanagementApp;
    
    private CourseService courseService;

    public InsertCourseForm(CoursePanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	courseService = new CourseService(conn);
    	
        setTitle("Insert Course");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(14, 1, 5, 0));
        add(panel);
        
        // ===== Course Name =====
        panel.add(new JLabel("Course Name:"));
        txtCourseName = new JTextField();
        panel.add(txtCourseName);
        
     // ===== Course ID =====
        panel.add(new JLabel("Course ID:"));
        txtCourseID = new JTextField();
        panel.add(txtCourseID);
        
        // ===== Credit =====
        panel.add(new JLabel("Credit:"));
        txtCredit = new JTextField();
        panel.add(txtCredit);

        // ===== Insert Button =====
        JButton btnInsert = new JButton("Insert");
        btnInsert.setFocusable(false);
        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnInsert);
        btnPanel.add(btnReset);
        
        btnInsert.addActionListener(e -> insertCourse());
        btnReset.addActionListener(e -> resetForm());
        
        panel.add(new JLabel());
        panel.add(btnPanel);

        setVisible(true);
    }
    
    private void insertCourse() {
        try {
            String courseName = txtCourseName.getText().trim();
            String courseID = txtCourseID.getText().trim();
            int credit = Integer.parseInt(txtCredit.getText().trim());

            Course course = new Course(courseID, courseName, credit);

            courseService.addCourse(course);

            JOptionPane.showMessageDialog(this, "Course inserted successfully!");
            
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
    	txtCourseID.setText("");
        txtCourseName.setText("");
        txtCredit.setText("");
    }
}
