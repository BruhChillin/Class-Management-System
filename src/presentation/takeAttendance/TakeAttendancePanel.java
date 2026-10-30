package presentation.takeAttendance;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

import javax.swing.*;

import businesslogic.AssignmentService;
import businesslogic.CourseService;
import businesslogic.ScheduleService;
import businesslogic.TeacherService;
import model.Schedule;

public class TakeAttendancePanel extends JPanel{
    private ScheduleService scheduleService;
    private CourseService courseService;
    private AssignmentService assignmentService;
    private TeacherService teacherService;
    
	public TakeAttendancePanel(Connection conn) {
		scheduleService = new ScheduleService(conn);
		courseService = new CourseService(conn);
		assignmentService = new AssignmentService(conn);
		teacherService = new TeacherService(conn);
		
		setLayout(new BorderLayout());
        
	     // ===================== NORTH =====================
        JLabel label = new JLabel("CHECK ATTENDANCE");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.NORTH);
        
        
        Schedule currentSchedule = scheduleService.retrieveCurrentSchedule();
        
        JButton btnCurrentClass = new JButton();
        
        if(currentSchedule == null) {

            btnCurrentClass.setText(
                "No class currently in session"
            );

            btnCurrentClass.setEnabled(false);
        }
        
        else {
        	btnCurrentClass.setText(
        	    "<html>" +
        	    "<center>" +
        	    courseService.findCourseByID(currentSchedule.getCourseID()).getCourseName()  + "<br>" +
        	    teacherService.findByID(assignmentService.findAssignmentsByCourseID(currentSchedule.getCourseID()).get(0).getTeacherID()).getFullName() + "<br>" +
        	    currentSchedule.getDayOfWeek() + " | " +
        	    LocalDate.now() + "<br>" +
        	    currentSchedule.getStartTime() + " - " +
        	    currentSchedule.getEndTime() +
        	    "</center>" +
        	    "</html>"
        	);
        }
        
        btnCurrentClass.setPreferredSize(
        	    new Dimension(500, 100)
        	);
        
        btnCurrentClass.setFont(
        	    new Font("SansSerif", Font.BOLD, 40)
        	);
        
        btnCurrentClass.setFocusable(false);
        
        add(btnCurrentClass);
        
	}
}
