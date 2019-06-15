package system;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dane.AppParameters;
import dane.Event;
import dane.Alarm;
import dane.Contact;

public class Transmiter {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm");

	// DATABASE
	public ArrayList<Contact> bdImportKontakty(String baza) {
		System.out.println("----------------------bdImportKontakty:");
		ArrayList<Contact> contacts = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + baza)){
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Kontakty");

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
				contacts.add(temp);
				System.out.println(ret);
				id++;
			}
			return contacts;
		} catch (Exception ee) {
            throw new RuntimeException(ee);
		}
	}

	public void bdExportKontakty(ArrayList<Contact> kontakty, String baza) {
		System.out.println("----------------------bdExportKontakty:");
		try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + baza)){
			Statement s = conn.createStatement();
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
		} catch (Exception ee) {
            throw new RuntimeException(ee);
		}
	}

	public ArrayList<Event> bdImportEventy(String baza) {
		System.out.println("----------------------bdImportEventy:");
		ArrayList<Event> eventy = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + baza)){
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Wydarzenia");

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
					LocalDateTime t = LocalDateTime.parse(rs.getString("before"));
					String snd = rs.getString("sound");
					if(snd.equals("")) {
						snd = AppParameters.getInstance().getSound();
					}
					temp.setNotification(new Alarm(t, snd));
				}
//				if (rs.getInt("contact") != -1) {
//					temp.setPerson(kontakty.get(rs.getInt("contact")));
//					ret += rs.getString("contact") + " ";
//				}

				eventy.add(temp);
				System.out.println(ret);
				id++;
			}
			return eventy;
		} catch (Exception ee) {
            throw new RuntimeException(ee);
		}
	}

	public void bdExportEventy(ArrayList<Event> eventy, String baza) {
		System.out.println("----------------------bdExportEventy:");
		try (Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + baza)){
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM Wydarzenia");

			for (int i = 0; i < eventy.size(); i++) {

				String query = "INSERT INTO Wydarzenia (tittle, start, end, note, place, contact, sound, before) VALUES (";
				query += "\"" + eventy.get(i).getTittle() + "\", ";
				query += "\"" + eventy.get(i).getStart().format(dateFormat) + "\", ";
				query += "\"" + eventy.get(i).getEnd().format(dateFormat) + "\", ";
				query += "\"" + eventy.get(i).getNote() + "\", ";
				query += "\"" + eventy.get(i).getPlace() + "\", ";

				String contact = "-1";
//				if (eventy.get(i).getPerson() != null) {
//					System.out.println("++++++++++++" + eventy.get(i).getTittle() + ":" + eventy.get(i).getPerson());
//					for (int j = 0; j < kontakty.size(); j++)
//					{
//						System.out.println(j + " " + kontakty.get(j).getName());
//						if (eventy.get(i).getPerson() == kontakty.get(j)) { //System.out.println("JEEEEEEEEEEEEEEEEEEEEJ");
//							contact = Integer.toString(j);
//							break;
//						}
//					}
//
//				}
				query += "\"" + contact + "\", ";

				if (eventy.get(i).getNotification() == null)
					query += "\"\", \"\");";
				else {

					query += "\"" + eventy.get(i).getNotification().getSound() + "\",";
					query += "\"" + eventy.get(i).getNotification().getBefore().format(dateFormat) + "\");";
					
				}
				System.out.println(query);
				s.executeUpdate(query);
			}
		} catch (Exception ee) {
            throw new RuntimeException(ee);
		}
	}

	// XML
	public void xmlExport(File file, List<Event> eventsToExport) {
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

			e.writeObject(eventsToExport);
			e.close();
		} catch (Exception e) {
            throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Event> xmlImport(File file) {
		System.out.println("----------------------xmlImport:");
		try(XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
			return (ArrayList<Event>) d.readObject();
		} catch (Exception e) {
            throw new RuntimeException(e);
		}
	}

}
