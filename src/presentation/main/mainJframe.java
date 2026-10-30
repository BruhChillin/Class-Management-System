package presentation.main;

import java.awt.*;
import java.sql.*;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dataaccess.DatabaseConnector;
import presentation.assignment.AssignmentPanel;
import presentation.course.CoursePanel;
import presentation.student.StudentPanel;
import presentation.takeAttendance.TakeAttendancePanel;
import presentation.teacher.TeacherPanel;

public class mainJframe extends JFrame {
	
	private JPanel contentPanel;
    private CardLayout cardLayout;
    
    public mainJframe(Connection conn) {
    	
        setTitle("Class Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setupCloseAction(conn);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // ===================== NORTH =====================
        
        
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JButton btnStudent = new JButton("Student");
        btnStudent.setPreferredSize(new Dimension(200, 30));
        btnStudent.setFocusable(false);
        JButton btnTeacher = new JButton("Teacher");
        btnTeacher.setPreferredSize(new Dimension(200, 30));
        btnTeacher.setFocusable(false);
        JButton btnCourse = new JButton("Course");
        btnCourse.setPreferredSize(new Dimension(200, 30));
        btnCourse.setFocusable(false);
        JButton btnAssignment = new JButton("Assignment");
        btnAssignment.setPreferredSize(new Dimension(200, 30));
        btnAssignment.setFocusable(false);
        JButton btnTakeAttendance = new JButton("Take Attendance");
        btnTakeAttendance.setPreferredSize(new Dimension(200, 30));
        btnTakeAttendance.setFocusable(false);
        
        menuPanel.add(btnStudent);
        menuPanel.add(btnTeacher);
        menuPanel.add(btnCourse);
        menuPanel.add(btnAssignment);
        menuPanel.add(btnTakeAttendance);
        
        add(menuPanel, BorderLayout.NORTH);
        
        getContentPane().add(menuPanel, BorderLayout.NORTH);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        StudentPanel studentPanel = new StudentPanel(conn);
        TeacherPanel teacherPanel = new TeacherPanel(conn);
        CoursePanel coursePanel = new CoursePanel(conn);
        AssignmentPanel assignmentPanel = new AssignmentPanel(conn);
        TakeAttendancePanel takeAttendancePanel = new TakeAttendancePanel(conn);

        contentPanel.add(studentPanel, "student");
        contentPanel.add(teacherPanel, "teacher");
        contentPanel.add(coursePanel, "course");
        contentPanel.add(assignmentPanel, "assignment");
        contentPanel.add(takeAttendancePanel, "attendance");
        

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // ===== Events =====
        btnStudent.addActionListener(e ->
            cardLayout.show(contentPanel, "student"));

        btnTeacher.addActionListener(e ->
            cardLayout.show(contentPanel, "teacher"));

        btnCourse.addActionListener(e ->
            cardLayout.show(contentPanel, "course"));

        btnAssignment.addActionListener(e ->
            cardLayout.show(contentPanel, "assignment"));
        
        btnTakeAttendance.addActionListener(e ->
            cardLayout.show(contentPanel, "attendance"));
        
        // ===================== VISIBLE =====================
        setVisible(true);
    }
    
    private void setupCloseAction(Connection conn) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	DatabaseConnector.closeConnection(conn);
                dispose();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
    	try {
    		Connection conn = DatabaseConnector.getConnection();
			new mainJframe(conn);
		} catch (SQLException e) {
			System.err.println("Error when connecting database: " + e.getMessage());
		}
    	
    }
}
