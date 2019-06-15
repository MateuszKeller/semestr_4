package system.events;

import java.util.List;

import dane.Event;

public class DisplayedEventsChanged implements InternalEvent{
	private List<Event> events;
	
	public DisplayedEventsChanged (List<Event> ev) {
		super();
		events = ev;
	}
	
	public List<Event> getEvents () {
		return events;
	}
	
	public void setEvents(List<Event> ev) {
		events = ev;
	}

}
