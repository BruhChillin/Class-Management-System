package presentation.student;

import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import businesslogic.DuplicateKeyException;
import businesslogic.StudentService;
import model.Student;

public class InsertStudentForm extends JFrame {

    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtPhone;
    private JTextField txtStudentID;
    private JDateChooser dateChooser;
    private JTextField txtEmail;
    
    private StudentPanel classmanagementApp;
    
    private StudentService studentService;

    public InsertStudentForm(StudentPanel classmanagementApp, Connection conn) {
    	this.classmanagementApp = classmanagementApp;
    	studentService = new StudentService(conn);
    	
        setTitle("Insert Student");
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

        // ===== Student ID =====
        panel.add(new JLabel("Student ID:"));
        txtStudentID = new JTextField();
        panel.add(txtStudentID);
        
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
        
        btnInsert.addActionListener(e -> insertStudent());
        btnReset.addActionListener(e -> resetForm());
        
        panel.add(new JLabel()); // empty placeholder
        panel.add(btnPanel);

        setVisible(true);
    }
    
    private void insertStudent() {
        try {
        	String studentID = txtStudentID.getText().trim();
            String fullName = txtFullName.getText().trim();
            LocalDate dob = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String gender = cbGender.getSelectedItem().toString();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            Student student = new Student(studentID, fullName, dob, gender, phone, email);

            studentService.add(student);

            JOptionPane.showMessageDialog(this, "Student inserted successfully!");
            
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
    	txtStudentID.setText("");
        txtFullName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        cbGender.setSelectedIndex(0);
        dateChooser.setDate(new Date());
    }
}
