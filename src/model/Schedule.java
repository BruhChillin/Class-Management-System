package model;

import java.time.LocalTime;

public class Schedule {
	private String scheduleID;
	private String courseID;
	private String dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;
	private String room;
	
	public Schedule(String scheduleID, String courseID, String dayOfWeek, LocalTime startTime, LocalTime endTime, String room) {
		this.scheduleID = scheduleID;
		this.courseID = courseID;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
	}
	
	public Schedule(){}
	
	public String getScheduleID() {
		return scheduleID;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public String getRoom() {
		return room;
	}
}
