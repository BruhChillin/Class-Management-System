package presentation.student;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import businesslogic.StudentService;
import model.Student;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UpdateStudentForm extends JFrame {

    private JTextField txtStudentID;
    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtPhone;
    private JDateChooser dateChooser;
    private JTextField txtEmail;
    
    private StudentPanel classmanagementApp;
    
    private StudentService studentService;

    public UpdateStudentForm(StudentPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	studentService = new StudentService(conn);
    	
        setTitle("Update Student");
        setSize(420, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(15, 1, 5, 0));
        add(panel);

        // ===== Student ID =====  
        panel.add(new JLabel("Student ID to update:"));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtStudentID = new JTextField(29);
        JButton btnLoad = new JButton("Load");
        btnLoad.setFocusable(false);
        searchPanel.add(txtStudentID);
        searchPanel.add(btnLoad);
        panel.add(searchPanel);
        
        // ===== FULL NAME =====
        panel.add(new JLabel("Full name:"));
        txtFullName = new JTextField();
        panel.add(txtFullName);

        // ===== GENDER =====
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
        
        btnLoad.addActionListener(e -> loadStudent());
        btnUpdate.addActionListener(e -> updateStudent());

        panel.add(new JLabel());
        panel.add(btnPanel);

        setVisible(true);
    }

    private void loadStudent() {
        try {
            String id = txtStudentID.getText().trim();

            Student student = studentService.findByID(id);

            // Load data
            txtFullName.setText(student.getFullName());
            txtPhone.setText(student.getPhone());
            txtEmail.setText(student.getEmail());
            cbGender.setSelectedItem(student.getGender());
            dateChooser.setDate(java.sql.Date.valueOf(student.getDateOfBirth()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStudent() {
        try {
            String id = txtStudentID.getText().trim();
            String fullName = txtFullName.getText().trim();
            LocalDate dob = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String gender = cbGender.getSelectedItem().toString();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            Student student = new Student(id, fullName, dob, gender, phone, email);

            studentService.redesign(student);

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
