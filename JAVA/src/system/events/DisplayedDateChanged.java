package system.events;

import java.time.LocalDate;
import java.util.List;

import dane.Event;

public class DisplayedDateChanged implements InternalEvent {
	
	private LocalDate newDate;
	private List<Event> events; 
	
	public DisplayedDateChanged(LocalDate newDate, List<Event> events) {
		super();
		this.newDate = newDate;
		this.events = events;
	}
	public LocalDate getNewDate() {
		return newDate;
	}
	public void setNewDate(LocalDate newDate) {
		this.newDate = newDate;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	
	
}
