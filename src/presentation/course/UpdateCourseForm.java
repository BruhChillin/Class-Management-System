package presentation.course;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import businesslogic.CourseService;
import model.Course;

public class UpdateCourseForm extends JFrame {

    private JTextField txtCourseID;
    private JTextField txtCourseName;
    private JTextField txtCredit;
    
    private CoursePanel classmanagementApp;
    
    private CourseService courseService;

    public UpdateCourseForm(CoursePanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	courseService = new CourseService(conn);
    	
        setTitle("Update Course");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(15, 1, 5, 0));
        add(panel);

        // ===== Course ID =====  
        panel.add(new JLabel("Course ID to update:"));
        txtCourseID = new JTextField();
        panel.add(txtCourseID);
        
        // ========= OR=========
        JLabel label = new JLabel("OR");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        panel.add(label);
        
        // ===== Course NAME =====
        panel.add(new JLabel("Course Name to update:"));
        txtCourseName = new JTextField();
        panel.add(txtCourseName);
        
        JPanel loadPanel = new JPanel();
        JButton btnLoad = new JButton("Load");
        btnLoad.setFocusable(false);
        loadPanel.add(btnLoad);
        panel.add(loadPanel);
        
        // ===== CREDIT =====
        panel.add(new JLabel("Credit:"));
        txtCredit = new JTextField();
        panel.add(txtCredit);

        // ===== BUTTON =====
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusable(false);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnUpdate);
        
        btnLoad.addActionListener(e -> loadCourse());
        btnUpdate.addActionListener(e -> updateCourse());

        panel.add(new JLabel()); // empty
        panel.add(btnPanel);

        setVisible(true);
    }

    private void loadCourse() {
        try {
            String id = txtCourseID.getText().trim();
            String name = txtCourseName.getText().trim();
            
            if(id != null && !id.isEmpty()) {
            	Course course = courseService.findCourseByID(id);
            	txtCourseName.setText(course.getCourseName());
            	txtCredit.setText(String.valueOf(course.getCredit()));
            }
            else if (name != null && !name.isEmpty()) {
            	Course course = courseService.findCourseByCourseName(name);
            	txtCourseID.setText(course.getCourseID());
            	txtCredit.setText(String.valueOf(course.getCredit()));
			}
            else {
            	throw new IllegalArgumentException("Both Course ID and Course Name must not be empty!");
			}

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCourse() {
        try {
            String id = txtCourseID.getText().trim();
            String courseName = txtCourseName.getText().trim();
            int credit = Integer.parseInt(txtCredit.getText().trim());

            Course course = new Course(id, courseName, credit);

            courseService.reDesignCourse(course);

            JOptionPane.showMessageDialog(this, "Course updated successfully!");
            
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
