package dataaccess;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

import model.Schedule;

public class ScheduleDAO {
	private final Connection conn;
	
	public ScheduleDAO(Connection conn) {
		this.conn = conn;
	}
	
	public Schedule getCurrentSchedule() {
	    LocalDateTime now = LocalDateTime.now();

	    String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	    LocalTime currentTime = now.toLocalTime();

	    String sql = "SELECT * FROM Schedule WHERE DayOfWeek = ? AND StartTime <= ? AND EndTime >= ?";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, dayOfWeek);
	        ps.setTime(2, Time.valueOf(currentTime));
	        ps.setTime(3, Time.valueOf(currentTime));

	        ResultSet rs = ps.executeQuery();

	        if(rs.next()) {
	            return new Schedule(
	            	rs.getString("ScheduleID"),
	                rs.getString("CourseID"),
	                rs.getString("DayOfWeek"),
	                rs.getTime("StartTime").toLocalTime(),
	                rs.getTime("EndTime").toLocalTime(),
	                rs.getString("Room")
	            );
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
