package system;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import dane.Alarm;
import dane.Contact;
import dane.Event;

public class Meneger {

	static ArrayList<Event> eventy = new ArrayList<Event>();
	ArrayList<Contact> kontakty = new ArrayList<Contact>();
	static Duration whenToRemove = Duration.ofDays(10);
	
	public static void main(String[] args) {
		
		
		LocalDateTime date = LocalDateTime.now();
	    System.out.println(date);
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	    
	    String fDate = date.format(dateFormat);
	    System.out.println(fDate);
	    
	    LocalDateTime sDate = LocalDateTime.of(2019, 5, 8, 1, 57);
	    System.out.println(sDate.toString());
	    
	    fDate = sDate.format(dateFormat);
	    System.out.println(fDate);
	    System.out.println("--------------------");
	    
	    LocalTime t = LocalTime.of(0, 30);
	    Event e1 = new Event("1", sDate, date);
	    eventy.add(e1);
	    
	    Event e2 = new Event("2", sDate, date);
	    eventy.add(e2);
	    
	    Event e3 = e1; //new Event(e1.getTittle(), e1.getStart(), e1.getEnd());
	    //eventy.remove(e3);
	    System.out.println(eventy);
	    
	    oldEventsGo();
	    
	    System.out.println(eventy);
	 }
	
	// 'DANE' bedzie sie wprowadzac w jakims formularzu wiec jesli POLE != '' to ustawiamy to pole
	
	//EVENTS
	public void addEvent(String tittle, LocalDateTime start, LocalDateTime end, 
							String note, String place, Alarm notification, Contact person)
	{
		Event temp = new Event(tittle, start, end);
		if(note != "") temp.setNote(note);
		if(place != "") temp.setPlace(place);
		if(notification != null) temp.setNotification(notification);
		if(person != null) temp.setPerson(person);
	
		eventy.add(temp);
	}
	
	public void deleteEvent(int numberOfEvent)
	{
		eventy.remove(numberOfEvent);
	}
	
	//CONTACTS	
	public void addContact( String name, String company, String email, String phone)
	{
		Contact temp = new Contact(name);	
		
		if(company != "") temp.setCompany(company);
		if(email != "") temp.setEmail(email);
		if(phone != "") temp.setPhone(phone);
		
		kontakty.add(temp);
	}
	
	public void deleteContact(int numberOfContact)
	{
		kontakty.remove(numberOfContact);
	}
	
	//FUNCTIONS
	static public void oldEventsGo() 
	{
		Iterator<Event> it = eventy.iterator();
		
		while(it.hasNext())
		{
			Event e = it.next();
			Duration duration = Duration.between(e.getStart(), LocalDateTime.now());
			
			System.out.println(duration);
			if(duration.compareTo(whenToRemove) > 0)
				it.remove();
			
				
		}
	
	}
	
}
