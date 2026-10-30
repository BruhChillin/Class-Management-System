package presentation.teacher;

import javax.swing.*;

import businesslogic.TeacherService;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteTeacherForm extends JFrame {

    private JTextField txtTeacherID;
    
    private TeacherPanel classmanagementApp;
    
    private TeacherService teacherService;

    public DeleteTeacherForm(TeacherPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	teacherService = new TeacherService(conn);
    	
        setTitle("Delete Teacher");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Teacher ID:"));
        txtTeacherID = new JTextField();
        add(txtTeacherID);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFocusable(false);
        add(btnDelete);

        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        add(btnReset);

        btnDelete.addActionListener(e -> deleteTeacher());
        btnReset.addActionListener(e -> txtTeacherID.setText(""));

        setVisible(true);
    }

    private void deleteTeacher() {
        try {
            String id = txtTeacherID.getText().trim();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete teacher ID: " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            teacherService.remove(id);

            JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
            
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

