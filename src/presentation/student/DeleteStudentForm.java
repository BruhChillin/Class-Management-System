package presentation.student;

import javax.swing.*;

import businesslogic.StudentService;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteStudentForm extends JFrame {

    private JTextField txtStudentID;
    
    private StudentPanel classmanagementApp;
    
    private StudentService studentService;

    public DeleteStudentForm(StudentPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	studentService = new StudentService(conn);
    	
        setTitle("Delete Student");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Student ID:"));
        txtStudentID = new JTextField();
        add(txtStudentID);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFocusable(false);
        add(btnDelete);

        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        add(btnReset);

        btnDelete.addActionListener(e -> deleteStudent());
        btnReset.addActionListener(e -> txtStudentID.setText(""));

        setVisible(true);
    }

    private void deleteStudent() {
        try {
            String id = txtStudentID.getText().trim();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete student ID: " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            studentService.remove(id);

            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            
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

