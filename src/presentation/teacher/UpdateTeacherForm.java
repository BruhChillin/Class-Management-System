package presentation.teacher;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import businesslogic.TeacherService;
import model.Teacher;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UpdateTeacherForm extends JFrame {

    private JTextField txtTeacherID;
    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtPhone;
    private JDateChooser dateChooser;
    private JTextField txtEmail;
    
    private TeacherPanel classmanagementApp;
    
    private TeacherService teacherService;

    public UpdateTeacherForm(TeacherPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	teacherService = new TeacherService(conn);
    	
        setTitle("Update Teacher");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(15, 1, 5, 0));
        add(panel);

        // ===== Student ID =====  
        panel.add(new JLabel("Teacher ID to update:"));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtTeacherID = new JTextField(29);
        JButton btnLoad = new JButton("Load");
        btnLoad.setFocusable(false);
        searchPanel.add(txtTeacherID);
        searchPanel.add(btnLoad);
        panel.add(searchPanel);
        
        // ===== FULL NAME =====
        panel.add(new JLabel("Full name:"));
        txtFullName = new JTextField();
        panel.add(txtFullName);
        
        // ===== GENDER  =====
        panel.add(new JLabel("Gender:"));
        cbGender = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        panel.add(cbGender);

        // ===== DATE OF BIRTH =====
        panel.add(new JLabel("Date of Birth:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("MM-dd-yyyy");
        dateChooser.setDate(new Date());
        panel.add(dateChooser);
        
        // ===== PHONE =====
        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        // ===== EMAIL =====
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        // ===== BUTTON =====
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusable(false);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnUpdate);
        
        btnLoad.addActionListener(e -> loadTeacher());
        btnUpdate.addActionListener(e -> updateTeacher());

        panel.add(new JLabel()); // empty
        panel.add(btnPanel);

        setVisible(true);
    }

    private void loadTeacher() {
        try {
            String id = txtTeacherID.getText().trim();

            Teacher teacher = teacherService.findByID(id);

            if (teacher == null) {
                JOptionPane.showMessageDialog(this, 
                    "Teacher ID not found!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Load data
            txtFullName.setText(teacher.getFullName());
            txtPhone.setText(teacher.getPhone());
            txtEmail.setText(teacher.getEmail());
            cbGender.setSelectedItem(teacher.getGender());
            dateChooser.setDate(java.sql.Date.valueOf(teacher.getDateOfBirth()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTeacher() {
        try {
            String id = txtTeacherID.getText().trim();
            String fullName = txtFullName.getText().trim();
            LocalDate dob = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String gender = cbGender.getSelectedItem().toString();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            Teacher teacher = new Teacher(id, fullName, dob, gender, phone, email);

            teacherService.redesign(teacher);

            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            
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
