package presentation.assignment;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import businesslogic.AssignmentService;
import businesslogic.CourseService;
import businesslogic.TeacherService;
import model.Assignment;

public class AssignmentPanel extends JPanel {

    private JTable table;
    private AssignmentService assignmentService;
    private CourseService courseService;
    private TeacherService teacherService;

    public AssignmentPanel(Connection conn) {
        assignmentService = new AssignmentService(conn);
        teacherService = new TeacherService(conn);
    	courseService = new CourseService(conn);

        setLayout(new BorderLayout());
        
     // ===================== NORTH =====================
        JLabel label = new JLabel("ASSIGNMENT SECTION");
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
        		"Assignment ID", "Teacher ID", "Teacher Name", "Course ID", "Course Name", "Semester", "Academic Year"
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
        	InsertAssignmentForm form = new InsertAssignmentForm(AssignmentPanel.this, conn);
        	form.setVisible(true);
        });
        
        btnUpdate.addActionListener(e -> {
            UpdateAssignmentForm form = new UpdateAssignmentForm(AssignmentPanel.this, conn);
            form.setVisible(true);
        });
        
        btnDelete.addActionListener(e -> {
            DeleteAssignmentForm form = new DeleteAssignmentForm(AssignmentPanel.this, conn);
            form.setVisible(true);
        });
        
        btnSearch.addActionListener(e -> {
            new SearchAssignmentForm(conn).setVisible(true);
        });

        loadTable();
    }
    
    public void loadTable() {
        List<Assignment> list = assignmentService.retrieveAllAssignments();
        
        String[] cols = { "Assignment ID", "Teacher ID", "Teacher Name", "Course ID", "Course Name", "Semester", "Academic Year" };
        String[][] data = new String[list.size()][7];

        for (int i = 0; i < list.size(); i++) {
            Assignment a = list.get(i);
            data[i][0] = String.valueOf(a.getAssignmentID());
            data[i][1] = a.getTeacherID();
            data[i][2] = teacherService.findByID(a.getTeacherID()).getFullName();
            data[i][3] = a.getCourseID();
            data[i][4] = courseService.findCourseByID(a.getCourseID()).getCourseName();
            data[i][5] = a.getSemester();
            data[i][6] = a.getAcademicYear();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
