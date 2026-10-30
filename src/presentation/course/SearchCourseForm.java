package presentation.course;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import businesslogic.CourseService;
import model.Course;

public class SearchCourseForm extends JFrame {

    private JTextField txtSearchID;
    private JTextArea txtResult;
    private CourseService courseService;

    public SearchCourseForm(Connection conn) {
    	courseService = new CourseService(conn);
    	
        setTitle("Search Course");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Course ID:"));

        txtSearchID = new JTextField(15);
        topPanel.add(txtSearchID);

        JButton btnFind = new JButton("Find");
        topPanel.add(btnFind);

        add(topPanel, BorderLayout.NORTH);

        txtResult = new JTextArea();
        txtResult.setEditable(false);
        add(new JScrollPane(txtResult), BorderLayout.CENTER);

        btnFind.addActionListener(e -> findCourse());

        setVisible(true);
    }

    private void findCourse() {
        try {
            String id = txtSearchID.getText().trim();

            Course course = courseService.findCourseByID(id);

            txtResult.setText(
                "Course Found:\n" +
                "Course ID: " + course.getCourseID() + "\n" +
                "Course Name: " + course.getCourseName() + "\n" +
                "Credit: " + course.getCredit() + "\n"
            );

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

