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
import java.util.ArrayList;

import dane.Event;
import dane.Alarm;
import dane.Contact;

public class Transmiter {

	static String database = "Organizer.accdb";//"Organizer.accdb";
	static Connection conn;
	static java.sql.Statement s;
	static ResultSet rs;
	
	// DATABASE
		public void bdImportKontakty(ArrayList<Contact> kontakty) {
			try {
				conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
				s = conn.createStatement();
				rs = s.executeQuery("SELECT * FROM Kontakty");

				String ret; int id = 0;
				while (rs.next()) {
					Contact temp = new Contact(rs.getString("name"));
					ret = id + ". " + rs.getString("name") + " ";
					if (rs.getString("email") != null) {
						temp.setCompany(rs.getString("company"));
						ret += rs.getString("company") + " ";
					}
					if (rs.getString("email") != null) {
						temp.setEmail(rs.getString("email"));
						ret += rs.getString("email") + " ";
					}
					if (rs.getString("phone") != null) {
						temp.setPhone(rs.getString("phone"));
						ret += rs.getString("phone")+ " ";
					}
					kontakty.add(temp);
					System.out.println(ret);
					id++;
				}

				conn.close();
			} catch (Exception ee) { System.out.println(ee); }
		}

		public void bdExportKontakty(ArrayList<Contact> kontakty) 
		{
			try {
				conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
				s = conn.createStatement();
				s.executeUpdate("DELETE FROM Kontakty");
				
				for(int i = 0; i<kontakty.size(); i++)
				{
					String query = "INSERT INTO Kontakty VALUES (";
					query += "\"" + kontakty.get(i).getName() + "\", ";
					query += "\"" + kontakty.get(i).getCompany() + "\", ";
					query += "\"" + kontakty.get(i).getEmail() + "\", ";
					query += "\"" + kontakty.get(i).getPhone() + "\");";
					
					System.out.println(query);
					s.executeUpdate(query);
				}

				conn.close();
			} catch (Exception ee) { System.out.println(ee); }
		}

		public void bdImportEventy(ArrayList<Event> eventy, ArrayList<Contact> kontakty) 
		{
			try {
				conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
				s = conn.createStatement();
				rs = s.executeQuery("SELECT * FROM Wydarzenia");

				String ret; int id = 0;
				while (rs.next()) {
					
					LocalDateTime start = LocalDateTime.parse(rs.getString("start"));
					LocalDateTime end = LocalDateTime.parse(rs.getString("end"));
					
					Event temp = new Event(rs.getString("tittle"),start, end);
					ret = id + ". " + rs.getString("tittle") + " S-" + start.toString()+ " E-" + end.toString() + " ";
					if (rs.getString("note") != "") {
						temp.setNote(rs.getString("note"));
						ret += rs.getString("note") + " ";
					}
					if (rs.getString("place") != "") {
						temp.setPlace(rs.getString("place"));
						ret += rs.getString("place") + " ";
					}
					if(rs.getString("sound") != "" && rs.getString("before") != ""){
						LocalTime t = LocalTime.parse(rs.getString("before"));
						temp.setNotification(new Alarm(t, rs.getString("sound")));
					}
					if(rs.getInt("contact") != -1){
						temp.setPerson(kontakty.get(rs.getInt("contact")));
						ret += rs.getString("contact")+ " ";
					}
						
					eventy.add(temp);
					System.out.println(ret);
					id++;
				}

				conn.close();
			} catch (Exception ee) { System.out.println(ee); }   
		}
		
		public void bdExportEventy(ArrayList<Contact> kontakty) 
		{
			try {
				conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
				s = conn.createStatement();
				s.executeUpdate("DELETE FROM Wydarzenia");
				
				for(int i = 0; i<kontakty.size(); i++)
				{
					String query = "INSERT INTO Kontakty VALUES (\"" + i + "\", ";
					query += "\" " + kontakty.get(i).getName() + " \", ";
					query += "\" " + kontakty.get(i).getCompany() + " \", ";
					query += "\" " + kontakty.get(i).getEmail() + " \", ";
					query += "\" " + kontakty.get(i).getPhone() + " \");";
					
					System.out.println(query);
					s.executeUpdate(query);
				}

				conn.close();
			} catch (Exception ee) { System.out.println(ee); }
		}
		
		//XML
		public void xmlExport(String file, ArrayList<Event> eventy)
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
		
		public ArrayList<Event> xmlImport(String file, ArrayList<Event> eventy) // nie wiem czemu nie dzia³a bezpoœrednio do zmiennej w Managerze
		{
			try {
				XMLDecoder	d = new XMLDecoder( new BufferedInputStream( new FileInputStream(file)));
				eventy = (ArrayList<Event>) d.readObject();
				d.close();
			} catch (FileNotFoundException e) { e.printStackTrace(); }
			return eventy;
		}
		
}
