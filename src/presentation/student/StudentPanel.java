package presentation.student;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import businesslogic.StudentService;
import model.Student;

public class StudentPanel extends JPanel {

    private JTable table;
    private StudentService studentService;

    public StudentPanel(Connection conn) {
        studentService = new StudentService(conn);

        setLayout(new BorderLayout());
        
     // ===================== NORTH =====================
        JLabel label = new JLabel("STUDENT SECTION");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.NORTH);
        
     // ===================== CENTER =====================
        table = new JTable(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Student ID", "Full Name", "Date of Birth", "Gender", "Phone", "Email"
        	}
        ));
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // ===================== SOUTH =====================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnInsert = new JButton("Insert");
        btnInsert.setFocusable(false);
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusable(false);
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFocusable(false);
        JButton btnSearch = new JButton("Search");
        btnSearch.setFocusable(false);

        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);

        add(buttonPanel, BorderLayout.SOUTH);
        
        btnInsert.addActionListener(e ->{
        	InsertStudentForm form = new InsertStudentForm(StudentPanel.this, conn);
        	form.setVisible(true);
        });
        
        btnUpdate.addActionListener(e -> {
            UpdateStudentForm form = new UpdateStudentForm(StudentPanel.this, conn);
            form.setVisible(true);
        });
        
        btnDelete.addActionListener(e -> {
            DeleteStudentForm form = new DeleteStudentForm(StudentPanel.this, conn);
            form.setVisible(true);
        });
        
        btnSearch.addActionListener(e -> {
            new SearchStudentForm(conn).setVisible(true);
        });

        loadTable();
    }
    
    public void loadTable() {
        List<Student> list = studentService.retrieveAll();
        
        String[] cols = { "Student ID", "Full Name", "Date of Birth", "Gender", "Phone", "Email" };
        String[][] data = new String[list.size()][6];

        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            data[i][0] = s.getStudentID();
            data[i][1] = s.getFullName();
            data[i][2] = s.getDateOfBirth().toString();
            data[i][3] = s.getGender();
            data[i][4] = s.getPhone();
            data[i][5] = s.getEmail();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
