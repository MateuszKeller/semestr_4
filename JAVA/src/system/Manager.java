package system;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import dane.*;
import system.Transmiter;


public class Manager {

	private ArrayList<Event> eventy;// = new ArrayList<Event>();
	private ArrayList<Contact> kontakty;// = new ArrayList<Contact>();

	private Transmiter xPort;// = new Transmiter();

	private Duration whenToRemove;// = Duration.ofDays(10);

	public ArrayList<Event> getEventy() { return eventy; }
	public ArrayList<Contact> getContacts() { return kontakty; }
	public Transmiter getTransmiter() { return xPort; }
	public Duration getWhenToRemove() { return whenToRemove; }
	public void setWhenToRemove(Duration whenToRemove) { this.whenToRemove = whenToRemove; }

	public void testMain(String[] args) {

System.out.println("--------------------DATA--------------------");
		
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime sDate = LocalDateTime.parse("2019-05-31T01:56:00");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
		
		// ------------------------------
//		String fDate = date.format(dateFormat);
//			System.out.println("date: " + date);
//			System.out.println("fDate: " + fDate);
//			System.out.println("sDate: " + sDate.toString());
//
//			fDate = sDate.format(dateFormat);
//			System.out.println("fDate z sDate: " + fDate);
		// ------------------------------	
		System.out.println("--------------------+EVENTY+--------------------");

		LocalDateTime t = LocalDateTime.parse("2019-06-17T00:30");
		Event e1 = new Event("1", sDate, date);
		e1.setNotification(new Alarm(t));
		Event e2 = new Event("2", sDate, date);
		
		eventy.add(e1);
		eventy.add(e2);
			//Event e3 = e1;
			//eventy.remove(e3);	
		// ------------------------------
//			System.out.println("EVENTY:" + eventy);
//			
//			System.out.println("--------------------");
//			removeExpiredEvents();
//			System.out.println("--------------------");
//			System.out.println("EVENTY PO R_EXP_EV:" + eventy);
			// ------------------------------
		System.out.println("--------------------+KONTAKTY+--------------------");
		// ------------------------------
//			System.out.println(kontakty);
//			System.out.println("IMPORT:");
		// ------------------------------

		xPort.bdImportKontakty(kontakty);
			System.out.println(kontakty);
		// System.out.println(kontakty); System.out.println("--------------------");

		System.out.println("--------------------+CZAS+--------------------");
//		LocalDateTime test = LocalDateTime.now();
//		test = test.minusHours(0);
//		test = test.minusMinutes(t.getMinute());
		
		// System.out.println("T: " + test);
		playAlarm();

		//e3.setNotification(new Alarm(t));
		//e3.playAlarmSound();

		sDate = LocalDateTime.parse("2019-06-01T00:00:00");
		LocalDateTime eDate = LocalDateTime.parse("2019-06-02T12:00:00");
		Event e4_f = new Event("TEST e4_f", sDate, eDate);
		e4_f.setPlace("MSC");
		e4_f.setNote("NOTKA");
		e4_f.setPerson(kontakty.get(0));
		e4_f.setNotification(new Alarm(t));

		eventy.add(e4_f);
		
		System.out.println("--------------------+XML+--------------------");
		
		String xmlFile = "test.xml";
		xPort.xmlExport(xmlFile, eventy);
//		eventy.clear();
//		eventy = xPort.xmlImport(xmlFile, eventy);
		
//			System.out.println("EVENTY PO xmlImport: " + eventy);
		
		System.out.println("--------------------+BD+--------------------");

//			System.out.println(kontakty);
		//xPort.bdExportKontakty(kontakty);
		
		xPort.bdImportEventy(eventy, kontakty);
//			System.out.println(eventy);
		
		//xPort.bdExportEventy(eventy, kontakty);
		
//		eventy.clear();
//		System.out.println(eventy);
//		
//		xPort.bdImportEventy(eventy, kontakty);
		System.out.println(eventy);
	}

	public Manager() {
		eventy = new ArrayList<Event>();
		kontakty = new ArrayList<Contact>();

		xPort = new Transmiter();

		whenToRemove = Duration.ofDays(10);
	}
	
	public List<Event> getEventsInMonth(LocalDate yearAndMonth) {
		return getEventsInMonth(yearAndMonth.getYear(), yearAndMonth.getMonthValue());
	}
	
//	public ArrayList<Event> getEventsInMonth(int year, int month){
	public List<Event> getEventsInMonth(int year, int month){
		ArrayList<Event> events = new ArrayList<Event>(); 
		for(int i = 0; i < eventy.size(); i++) {
			if(eventy.get(i).getStart().getYear() == year || eventy.get(i).getEnd().getYear() == year) {
				if(eventy.get(i).getStart().getMonthValue() == month || eventy.get(i).getEnd().getMonthValue() == month ) {
					events.add(eventy.get(i));
				}
		    }
		}
		return events; 
	}

	// EVENTS
	public void addEvent(String tittle, LocalDateTime start, LocalDateTime end, String note, String place,
			Alarm notification, Contact person) {
		Event temp = new Event(tittle, start, end);
		if (note != "")
			temp.setNote(note);
		if (place != "")
			temp.setPlace(place);
		if (notification != null)
			temp.setNotification(notification);
		if (person != null)
			temp.setPerson(person);

		eventy.add(temp);
	}
	
	public void addEvent(Event e) {
		if(e != null) {
		eventy.add(e);
//		System.out.println("event dodany");
		}
	}

	public void deleteEvent(int numberOfEvent) {
		eventy.remove(numberOfEvent);
	}
	
	public void deleteEvent(Event e) {
		eventy.remove(e);
	}

	// CONTACTS
	public void addContact(String name, String company, String email, String phone) {
		Contact temp = new Contact(name);

		if (company != "")
			temp.setCompany(company);
		if (email != "")
			temp.setEmail(email);
		if (phone != "")
			temp.setPhone(phone);

		kontakty.add(temp);
	}
	
	public void addContact(Contact c) {
		kontakty.add(c);
	}

	public void removeContact(int numberOfContact) {
		kontakty.remove(numberOfContact);
	}
	public void removeContact(Contact c) {
		kontakty.remove(c);
	}

	// FUNCTIONS
	public void removeExpiredEvents() {
		Iterator<Event> it = eventy.iterator();

		while (it.hasNext()) {
			Event e = it.next();
			// Duration duration = Duration.between(e.getStart(), LocalDateTime.now());
			Duration duration = Duration.between(LocalDateTime.now(), e.getStart());

			System.out.println("duration" + duration);
			if (duration.compareTo(whenToRemove) > 0)
				it.remove();

		}

	}
	
	public void removeOldEvents(LocalDateTime dueDate) {
//		System.out.println(dueDate);
		for(int i = 0; i < eventy.size(); i ++) {
			if(eventy.get(i).getEnd().isBefore(dueDate)) {
				eventy.remove(i);
			}
		}
	}

	public void playAlarm() {
		Iterator<Event> it = eventy.iterator();

		while (it.hasNext()) {
			Event e = it.next();

			if (e.getNotification() != null) {
				LocalDateTime dateOfAlarm = e.getStart();
				dateOfAlarm = dateOfAlarm.minusHours(e.getNotification().getBefore().getHour());
				dateOfAlarm = dateOfAlarm.minusMinutes(e.getNotification().getBefore().getMinute());

				if (dateOfAlarm.isBefore(LocalDateTime.now()))
					e.playAlarmSound();
			}

		}
	}
	
	public void importFromDatabase(String table) {
		if(table.equals("Kontakty"))
			xPort.bdImportKontakty(kontakty);
		if(table.equals("Wydarzenia"))
			xPort.bdImportEventy(eventy, kontakty);
	}
	
	public void exportFromDatabase(String table) {
		if(table.equals("Kontakty"))
			xPort.bdExportKontakty(kontakty);
		if(table.equals("Wydarzenia"))
			xPort.bdExportEventy(eventy, kontakty);
	}
	
	public void importFromXML(String file) {
		eventy = xPort.xmlImport(file, eventy);
	}

	public void exportFromXML(String file) {
		xPort.xmlExport(file, eventy);
	}

}
