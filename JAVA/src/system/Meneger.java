package system;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.beans.*;
import dane.*;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.beans.XMLEncoder;
//import java.beans.XMLDecoder;
//import java.beans.Encoder;
//import java.beans.Expression;
//import java.beans.PersistenceDelegate;
//import dane.Alarm;
//import dane.Contact;
//import dane.Event;

public class Meneger {

	static ArrayList<Event> eventy = new ArrayList<Event>();
	static ArrayList<Contact> kontakty = new ArrayList<Contact>();
	static Duration whenToRemove = Duration.ofDays(10);

	static String database = "Organizer.accdb";
	static Connection conn;
	static java.sql.Statement s;
	static ResultSet rs;
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
		bdImportKontakty();

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
		xmlExport(xmlFile);
		
		System.out.println("--------------------");
		eventy.clear();
		System.out.println(eventy);
		
		xmlImport(xmlFile);
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

	// DATABASE
	static public void bdImportKontakty() {
		try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
			s = conn.createStatement();
			rs = s.executeQuery("SELECT * FROM Kontakty");

			String ret;
			while (rs.next()) {
				String company = "";
				String email = "";
				String phone = "";
				ret = rs.getString("id") + ". " + rs.getString("name") + " ";
				if (rs.getString("email") != null) {
					company = rs.getString("company");
					ret += company + " ";
				}
				if (rs.getString("email") != null) {
					email = rs.getString("email");
					ret += email + " ";
				}
				if (rs.getString("phone") != null) {
					phone = rs.getString("phone");
					ret += phone + " ";
				}

				addContact(rs.getString("name"), company, email, phone);
				System.out.println(ret);
			}

			conn.close();
		} catch (Exception ee) { System.out.println(ee); }
	}

	public void bdExportKontakty() 
	{

	}

	public void bdImportEventy() 
	{
		        
	}
	
	//XML
	public static void xmlExport(String file)
	{
		try {
			
			XMLEncoder e = new XMLEncoder( new BufferedOutputStream( new FileOutputStream(file)));

			e.setPersistenceDelegate( LocalDateTime.class, // Event -- start, end
					new PersistenceDelegate() { @Override
		            	protected Expression instantiate(Object localDateTime, Encoder encdr){
		                	return new Expression(localDateTime, LocalDateTime.class, "parse",
		                			new Object[]{ localDateTime.toString() });
						}
					});
			 
			e.setPersistenceDelegate( LocalTime.class, // Alarm -- before
					new PersistenceDelegate() { @Override
						protected Expression instantiate(Object localTime, Encoder encdr){
		                	return new Expression(localTime, LocalTime.class, "parse",
		                			new Object[]{ localTime.toString() });
						}
					});
				
			e.writeObject(eventy);
			e.close();
		} catch (FileNotFoundException e1) { e1.printStackTrace(); }
	}
	
	public static void xmlImport(String file)
	{
		try {
			XMLDecoder	d = new XMLDecoder( new BufferedInputStream( new FileInputStream(file)));
			eventy = (ArrayList<Event>) d.readObject();
			d.close();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
	}
	
}
