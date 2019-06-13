package system;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dane.Event;
import dane.Alarm;
import dane.Contact;

public class Transmiter {

	static String database = "Organizer.accdb";// "Organizer.accdb";
	static Connection conn;
	static java.sql.Statement s;
	static ResultSet rs;

	// DATABASE
	public void bdImportKontakty(ArrayList<Contact> kontakty) {
		System.out.println("----------------------bdImportKontakty:");
		try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
			s = conn.createStatement();
			rs = s.executeQuery("SELECT * FROM Kontakty");

			String ret;
			int id = 0;
			while (rs.next()) {
				Contact temp = new Contact(rs.getString("name"));
				ret = id + ". " + rs.getString("name") + " ";
				if (rs.getString("company") != null) {
					temp.setCompany(rs.getString("company"));
					ret += rs.getString("company") + " ";
				}
				if (rs.getString("email") != null) {
					temp.setEmail(rs.getString("email"));
					ret += rs.getString("email") + " ";
				}
				if (rs.getString("phone") != null) {
					temp.setPhone(rs.getString("phone"));
					ret += rs.getString("phone") + " ";
				}
				kontakty.add(temp);
				System.out.println(ret);
				id++;
			}

			conn.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}

	public void bdExportKontakty(ArrayList<Contact> kontakty) {
		System.out.println("----------------------bdExportKontakty:");
		try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
			s = conn.createStatement();
			s.executeUpdate("DELETE FROM Kontakty");

			for (int i = 0; i < kontakty.size(); i++) {
				String query = "INSERT INTO Kontakty VALUES (";
				query += "\"" + kontakty.get(i).getName() + "\", ";
				query += "\"" + kontakty.get(i).getCompany() + "\", ";
				query += "\"" + kontakty.get(i).getEmail() + "\", ";
				query += "\"" + kontakty.get(i).getPhone() + "\");";

				System.out.println(query);
				s.executeUpdate(query);
			}

			conn.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}

	public void bdImportEventy(ArrayList<Event> eventy, ArrayList<Contact> kontakty) {
		System.out.println("----------------------bdImportEventy:");
		try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
			s = conn.createStatement();
			rs = s.executeQuery("SELECT * FROM Wydarzenia");

			String ret;
			int id = 0;
			while (rs.next()) {

				LocalDateTime start = LocalDateTime.parse(rs.getString("start"));
				LocalDateTime end = LocalDateTime.parse(rs.getString("end"));

				Event temp = new Event(rs.getString("tittle"), start, end);
				ret = id + ". " + rs.getString("tittle") + " S-" + start.toString() + " E-" + end.toString() + " ";
				if (!rs.getString("note").equals("")) {
					
					temp.setNote(rs.getString("note"));
					ret += rs.getString("note") + " ";
				}
				if (!rs.getString("place").equals("")) {
					temp.setPlace(rs.getString("place"));
					ret += rs.getString("place") + " ";
				}
				if (!rs.getString("before").equals("")) { //rs.getString("before") != ""
					LocalTime t = LocalTime.parse(rs.getString("before"));
					temp.setNotification(new Alarm(t));
					temp.getNotification().setSound(rs.getString("sound"));
				}
				if (rs.getInt("contact") != -1) {
					temp.setPerson(kontakty.get(rs.getInt("contact")));
					ret += rs.getString("contact") + " ";
				}

				eventy.add(temp);
				System.out.println(ret);
				id++;
			}

			conn.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}

	public void bdExportEventy(ArrayList<Event> eventy, ArrayList<Contact> kontakty) {
		System.out.println("----------------------bdExportEventy:");
		try {
			conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
			s = conn.createStatement();
			s.executeUpdate("DELETE FROM Wydarzenia");

			for (int i = 0; i < eventy.size(); i++) {
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm");

				String query = "INSERT INTO Wydarzenia (tittle, start, end, note, place, contact, sound, before) VALUES (";
				query += "\"" + eventy.get(i).getTittle() + "\", ";
				query += "\"" + eventy.get(i).getStart().format(dateFormat) + "\", ";
				query += "\"" + eventy.get(i).getEnd().format(dateFormat) + "\", ";
				query += "\"" + eventy.get(i).getNote() + "\", ";
				query += "\"" + eventy.get(i).getPlace() + "\", ";

				String contact = "-1";
				if (eventy.get(i).getPerson() != null) {
					//System.out.println("++++++++++++" + eventy.get(i).getTittle() + ":" + eventy.get(i).getPerson());
					for (int j = 0; j < kontakty.size(); j++)
					{
						//System.out.println(j + " " + kontakty.get(j).getName());
						if (eventy.get(i).getPerson() == kontakty.get(j)) { //System.out.println("JEEEEEEEEEEEEEEEEEEEEJ");
							contact = Integer.toString(j);
							break;
						}
							
					}
						
				}
				query += "\"" + contact + "\", ";

				if (eventy.get(i).getNotification() == null)
					query += "\"\", \"\");";
				else {

					dateFormat = DateTimeFormatter.ofPattern("HH:mm");
					query += "\"" + eventy.get(i).getNotification().getSound() + "\",";
					query += "\"" + eventy.get(i).getNotification().getBefore().format(dateFormat) + "\");";
					
				}
				System.out.println(query);
				s.executeUpdate(query);
			}

			conn.close();
		} catch (Exception ee) {
			System.out.println(ee);
		}
	}

	// XML
	public void xmlExport(String file, ArrayList<Event> eventy) {
		System.out.println("----------------------xmlExport:");
		try {

			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

			e.setPersistenceDelegate(LocalDateTime.class, // Event -- start, end
					new PersistenceDelegate() {
						@Override
						protected Expression instantiate(Object localDateTime, Encoder encdr) {
							return new Expression(localDateTime, LocalDateTime.class, "parse",
									new Object[] { localDateTime.toString() });
						}
					});

			e.setPersistenceDelegate(LocalTime.class, // Alarm -- before
					new PersistenceDelegate() {
						@Override
						protected Expression instantiate(Object localTime, Encoder encdr) {
							return new Expression(localTime, LocalTime.class, "parse",
									new Object[] { localTime.toString() });
						}
					});

			e.writeObject(eventy);
			e.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Event> xmlImport(String file, ArrayList<Event> eventy){  // nie wiem czemu nie dzia³a bezporednio do zmiennej w Managerze
		System.out.println("----------------------xmlImport:");
		try {
			XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
			eventy = (ArrayList<Event>) d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return eventy;
	}

}
