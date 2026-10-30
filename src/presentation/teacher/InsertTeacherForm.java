package presentation.teacher;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import businesslogic.DuplicateKeyException;
import businesslogic.TeacherService;
import model.Teacher;

public class InsertTeacherForm extends JFrame {

    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtPhone;
    private JTextField txtTeacherID;
    private JDateChooser dateChooser;
    private JTextField txtEmail;
    
    private TeacherPanel classmanagementApp;
    
    private TeacherService teacherService;

    public InsertTeacherForm(TeacherPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	teacherService = new TeacherService(conn);
    	
        setTitle("Insert Teacher");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(14, 1, 5, 0));
        add(panel);

        // ===== Full Name =====
        panel.add(new JLabel("Full name:"));
        txtFullName = new JTextField();
        panel.add(txtFullName);
        
        // ===== Teacher ID =====
        panel.add(new JLabel("Teacher ID:"));
        txtTeacherID = new JTextField();
        panel.add(txtTeacherID);
        
        // ===== Gender =====
        panel.add(new JLabel("Gender:"));
        cbGender = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        panel.add(cbGender);

        // ===== Date of Birth =====
        panel.add(new JLabel("Date of Birth:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("MM-dd-yyyy");
        dateChooser.setDate(new Date());
        panel.add(dateChooser);
        
        // ===== Phone =====
        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        // ===== Email =====
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        // ===== Insert Button =====
        JButton btnInsert = new JButton("Insert");
        btnInsert.setFocusable(false);
        JButton btnReset = new JButton("Reset");
        btnReset.setFocusable(false);
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnInsert);
        btnPanel.add(btnReset);
        
        btnInsert.addActionListener(e -> insertTeacher());
        btnReset.addActionListener(e -> resetForm());
        
        panel.add(new JLabel()); // empty placeholder
        panel.add(btnPanel);

        setVisible(true);
    }
    
    private void insertTeacher() {
        try {
        	String teacherID = txtTeacherID.getText().trim();
            String fullName = txtFullName.getText().trim();
            LocalDate dob = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String gender = cbGender.getSelectedItem().toString();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            Teacher teacher = new Teacher(teacherID, fullName, dob, gender, phone, email);

            teacherService.add(teacher);

            JOptionPane.showMessageDialog(this, "Teacher inserted successfully!");
            
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
        txtFullName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        dateChooser.setDate(new Date());
    }
}
