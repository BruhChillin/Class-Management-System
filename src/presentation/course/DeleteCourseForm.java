package presentation.course;

import javax.swing.*;

import businesslogic.CourseService;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteCourseForm extends JFrame {

    private JTextField txtCourseID;
    
    private CoursePanel classmanagementApp;
    
    private CourseService courseService;

    public DeleteCourseForm(CoursePanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	courseService = new CourseService(conn);
    	
        setTitle("Delete Course");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Course ID:"));
        txtCourseID = new JTextField();
        add(txtCourseID);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFocusable(false);
        add(btnDelete);

        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        add(btnReset);

        btnDelete.addActionListener(e -> deleteCourse());
        btnReset.addActionListener(e -> txtCourseID.setText(""));

        setVisible(true);
    }

    private void deleteCourse() {
        try {
            String id = txtCourseID.getText().trim();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete Course ID: " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            courseService.removeCourse(id);

            JOptionPane.showMessageDialog(this, "Course deleted successfully!");
            
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

}

