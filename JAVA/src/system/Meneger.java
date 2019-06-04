package system;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import dane.*;
import system.Transmiter;


public class Meneger {

	static ArrayList<Event> eventy = new ArrayList<Event>();
	static ArrayList<Contact> kontakty = new ArrayList<Contact>();
	
	static Transmiter xPort = new Transmiter();
	
	static Duration whenToRemove = Duration.ofDays(10);

	static String sound;

	public static void main(String[] args) {

		sound = args[0];
		LocalDateTime date = LocalDateTime.now();
		System.out.println(date);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");

		String fDate = date.format(dateFormat);
		System.out.println(fDate);

		// LocalDateTime sDate = LocalDateTime.of(2019, 5, 8, 1, 57);
		// 2007-12-03T10:15:30
		LocalDateTime sDate = LocalDateTime.parse("2019-05-31T01:56:00");
		System.out.println(sDate.toString());

		fDate = sDate.format(dateFormat);
		System.out.println(fDate);
		System.out.println("--------------------");

		LocalTime t = LocalTime.parse("00:30");
		Event e1 = new Event("1", sDate, date);
		eventy.add(e1);

		Event e2 = new Event("2", sDate, date);
		eventy.add(e2);
		e1.setNotification(new Alarm(t, sound));
		Event e3 = e1; // new Event(e1.getTittle(), e1.getStart(), e1.getEnd());
		// eventy.remove(e3);
		System.out.println(eventy);
		oldEventsGo();

		System.out.println(eventy);
		System.out.println("--------------------");
		System.out.println(kontakty);
		xPort.bdImportKontakty(kontakty);
		
		System.out.println("---------IMPORT-----------");
		System.out.println(kontakty);
		// System.out.println(kontakty); System.out.println("--------------------");

		LocalDateTime test = LocalDateTime.now();
		test = test.minusHours(0);
		test = test.minusMinutes(t.getMinute());

		// System.out.println("T: " + test);
		scream();
		
		//e3.setNotification(new Alarm(t));
		//e3.playAlarmSound();
		
		sDate = LocalDateTime.parse("2019-06-01T00:00:00");
		LocalDateTime eDate = LocalDateTime.parse("2019-06-02T12:00:00");
		
		Event e4_f = new Event("TEST", sDate, eDate);
		e4_f.setPlace("TU");
		e4_f.setNote("COS");
		e4_f.setPerson(kontakty.get(0));
		e4_f.setNotification(new Alarm(t, sound));
		
		String xmlFile = "test.xml";
		eventy.add(e4_f);
		xPort.xmlExport(xmlFile, eventy);
		
		System.out.println("--------------------");
		eventy.clear();
		
		eventy = xPort.xmlImport(xmlFile, eventy);
		System.out.println(eventy);
		
		

	}
	
	public Meneger(String sound) {}

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

	public void deleteEvent(int numberOfEvent) {
		eventy.remove(numberOfEvent);
	}

	// CONTACTS
	public static void addContact(String name, String company, String email, String phone) {
		Contact temp = new Contact(name);

		if (company != "")
			temp.setCompany(company);
		if (email != "")
			temp.setEmail(email);
		if (phone != "")
			temp.setPhone(phone);

		kontakty.add(temp);
	}

	public void deleteContact(int numberOfContact) {
		kontakty.remove(numberOfContact);
	}

	// FUNCTIONS
	static public void oldEventsGo() {
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

	public static void scream() {
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
	
}
