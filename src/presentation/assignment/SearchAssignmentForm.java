package presentation.assignment;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import businesslogic.AssignmentService;
import businesslogic.CourseService;
import businesslogic.TeacherService;
import model.Assignment;

public class SearchAssignmentForm extends JFrame {
	
	private JTable table;
    private JTextField txtSearchTeacherID;
    private JTextField txtSearchCourseID;
    private TeacherService teacherService;
    private CourseService courseService;
    private AssignmentService assignmentService;

    public SearchAssignmentForm(Connection conn) {
    	teacherService = new TeacherService(conn);
    	courseService = new CourseService(conn);
    	assignmentService = new AssignmentService(conn);
    	
        setTitle("Search Assignment");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        
        topPanel.add(new JLabel("Enter Teacher ID:"));
        txtSearchTeacherID = new JTextField(15);
        topPanel.add(txtSearchTeacherID);
        
        topPanel.add(new JLabel("Enter Course ID:"));
        txtSearchCourseID = new JTextField(15);
        topPanel.add(txtSearchCourseID);
        
        JButton btnFind = new JButton("Find");
        topPanel.add(btnFind);

        add(topPanel, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Assignment ID", "Teacher ID", "Teacher Name", "Course Name", "Semester", "Academic Year"
        	}
        ));
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        
        btnFind.addActionListener(e -> findAssignment());

        setVisible(true);
    }

    private void findAssignment() {
        try {
            String teacherID = txtSearchTeacherID.getText().trim();
            String courseID = txtSearchCourseID.getText().trim();
            
            if ((teacherID != null && !teacherID.isEmpty()) && (courseID != null && !courseID.isEmpty())) {
            	Assignment assignment = assignmentService.findAssignmentByTeacherCourseID(teacherID, courseID);

                String[] cols = { "Assignment ID", "Teacher ID", "Teacher Name", "Course Name", "Semester", "Academic Year" };
                String[][] data = new String[1][6];

                
                data[0][0] = String.valueOf(assignment.getAssignmentID());
                data[0][1] = assignment.getTeacherID();
                data[0][2] = teacherService.findByID(assignment.getTeacherID()).getFullName();
                data[0][3] = courseService.findCourseByID(assignment.getCourseID()).getCourseName();
                data[0][4] = assignment.getSemester();
                data[0][5] = assignment.getAcademicYear();
                
                table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
                	@Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
            }
            else if (teacherID != null && !teacherID.isEmpty()) {
            	List<Assignment> list = assignmentService.findAssignmentsByTeacherID(teacherID);

                String[] cols = { "Assignment ID", "Teacher ID", "Teacher Name", "Course Name", "Semester", "Academic Year" };
                String[][] data = new String[list.size()][6];

                for (int i = 0; i < list.size(); i++) {
                    Assignment a = list.get(i);
                    data[i][0] = String.valueOf(a.getAssignmentID());
                    data[i][1] = a.getTeacherID();
                    data[i][2] = teacherService.findByID(a.getTeacherID()).getFullName();
                    data[i][3] = courseService.findCourseByID(a.getCourseID()).getCourseName();
                    data[i][4] = a.getSemester();
                    data[i][5] = a.getAcademicYear();
                }
                table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
                	@Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
			}
            else if (courseID != null && !courseID.isEmpty()) {
            	List<Assignment> list = assignmentService.findAssignmentsByCourseID(courseID);

                String[] cols = { "Assignment ID", "Teacher ID", "Teacher Name", "Course Name", "Semester", "Academic Year" };
                String[][] data = new String[list.size()][6];

                for (int i = 0; i < list.size(); i++) {
                    Assignment a = list.get(i);
                    data[i][0] = String.valueOf(a.getAssignmentID());
                    data[i][1] = a.getTeacherID();
                    data[i][2] = teacherService.findByID(a.getTeacherID()).getFullName();
                    data[i][3] = courseService.findCourseByID(a.getCourseID()).getCourseName();
                    data[i][4] = a.getSemester();
                    data[i][5] = a.getAcademicYear();
                }
                table.setModel(new javax.swing.table.DefaultTableModel(data, cols){
                	@Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
			}
            else {
            	throw new IllegalArgumentException("Both Teacher ID and Course ID must not be empty!");
			}
            

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

