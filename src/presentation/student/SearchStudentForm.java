package presentation.student;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import businesslogic.StudentService;
import model.Student;

public class SearchStudentForm extends JFrame {

    private JTextField txtSearchID;
    private JTextArea txtResult;
    private StudentService studentService;

    public SearchStudentForm(Connection conn) {
    	studentService = new StudentService(conn);
    	
        setTitle("Search Student");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Student ID:"));

        txtSearchID = new JTextField(15);
        topPanel.add(txtSearchID);

        JButton btnFind = new JButton("Find");
        topPanel.add(btnFind);

        add(topPanel, BorderLayout.NORTH);

        txtResult = new JTextArea();
        txtResult.setEditable(false);
        add(new JScrollPane(txtResult), BorderLayout.CENTER);

        btnFind.addActionListener(e -> findStudent());

        setVisible(true);
    }

    private void findStudent() {
        try {
            String id = txtSearchID.getText().trim();

            Student student = studentService.findByID(id);

            txtResult.setText(student.showInfo());

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

