package presentation.course;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import businesslogic.CourseService;
import model.Course;

public class CoursePanel extends JPanel {

    private JTable table;
    private CourseService courseService;

    public CoursePanel(Connection conn) {
        courseService = new CourseService(conn);

        setLayout(new BorderLayout());
        
     // ===================== NORTH =====================
        JLabel label = new JLabel("COURSE SECTION");
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
        		"Course ID", "Course Name", "Credits"
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
        	InsertCourseForm form = new InsertCourseForm(CoursePanel.this, conn);
        	form.setVisible(true);
        });
        
        btnUpdate.addActionListener(e -> {
            UpdateCourseForm form = new UpdateCourseForm(CoursePanel.this, conn);
            form.setVisible(true);
        });
        
        btnDelete.addActionListener(e -> {
            DeleteCourseForm form = new DeleteCourseForm(CoursePanel.this, conn);
            form.setVisible(true);
        });
        
        btnSearch.addActionListener(e -> {
            new SearchCourseForm(conn).setVisible(true);
        });

        loadTable();
    }
    
    public void loadTable() {
        List<Course> list = courseService.retrieveAllCourses();
        
        String[] cols = { "Course ID", "Course Name", "Credits"};
        String[][] data = new String[list.size()][3];

        for (int i = 0; i < list.size(); i++) {
            Course c = list.get(i);
            data[i][0] = c.getCourseID();
            data[i][1] = c.getCourseName();
            data[i][2] = String.valueOf(c.getCredit());
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
