package gui;

import java.util.List;

import dane.Event;

public class CalendarDay {
	
	private String dayOfMonth; 
	private List<Event> events;

	public String getDayOfMonth() {
		return dayOfMonth;
	}
	
	public List<Event> getEvents(){
		return events;
	}
	
	public boolean isEmpty() {
		return "-".equals(dayOfMonth);
	}
	
	public CalendarDay(String dayOfMonth, List<Event> events) {
		this.dayOfMonth = dayOfMonth;
		this.events = events;
	}
	
}
