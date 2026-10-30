package presentation.teacher;

import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import businesslogic.TeacherService;
import model.Teacher;


public class TeacherPanel extends JPanel {

    private JTable table;
    private TeacherService teacherService;

    public TeacherPanel(Connection conn) {
        teacherService = new TeacherService(conn);

        setLayout(new BorderLayout());
     
     // ===================== NORTH =====================
        JLabel label = new JLabel("TEACHER SECTION");
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
        		"Teacher ID", "Full Name", "Date of Birth", "Gender", "Phone", "Email"
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
        	InsertTeacherForm form = new InsertTeacherForm(TeacherPanel.this, conn);
        	form.setVisible(true);
        });
        
        btnUpdate.addActionListener(e -> {
            UpdateTeacherForm form = new UpdateTeacherForm(TeacherPanel.this, conn);
            form.setVisible(true);
        });
        
        btnDelete.addActionListener(e -> {
            DeleteTeacherForm form = new DeleteTeacherForm(TeacherPanel.this, conn);
            form.setVisible(true);
        });
        
        btnSearch.addActionListener(e -> {
            new SearchTeacherForm(conn).setVisible(true);
        });

        loadTable();
    }
    
    public void loadTable() {
        List<Teacher> list = teacherService.retrieveAll();
        
        String[] cols = { "Teacher ID", "Full Name", "Date of Birth", "Gender", "Phone", "Email" };
        String[][] data = new String[list.size()][6];

        for (int i = 0; i < list.size(); i++) {
        	Teacher t = list.get(i);
            data[i][0] = t.getTeacherID();
            data[i][1] = t.getFullName();
            data[i][2] = t.getDateOfBirth().toString();
            data[i][3] = t.getGender();
            data[i][4] = t.getPhone();
            data[i][5] = t.getEmail();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
