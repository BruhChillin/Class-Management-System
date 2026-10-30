package presentation.teacher;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import businesslogic.TeacherService;
import model.Teacher;

public class SearchTeacherForm extends JFrame {

    private JTextField txtSearchID;
    private JTextArea txtResult;
    private TeacherService teacherService;

    public SearchTeacherForm(Connection conn) {
    	teacherService = new TeacherService(conn);
    	
        setTitle("Search Teacher");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Teacher ID:"));

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

            Teacher teacher = teacherService.findByID(id);

            txtResult.setText(teacher.showInfo());

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

