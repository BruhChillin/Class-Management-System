package businesslogic;

import java.sql.Connection;

import dataaccess.CourseDAO;
import dataaccess.ScheduleDAO;
import model.Schedule;

public class ScheduleService {
	private final Connection conn;
	private final CourseDAO courseDAO;
	private final ScheduleDAO scheduleDAO;
	
	public ScheduleService(Connection conn) {
		this.conn = conn;
		this.courseDAO = new CourseDAO(conn);
		this.scheduleDAO = new ScheduleDAO(conn);
	}
	
	public Schedule retrieveCurrentSchedule() {
		return scheduleDAO.getCurrentSchedule();
	}
}
