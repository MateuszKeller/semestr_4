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
import dane.Contact;

public class Transmiter {

	static String database = "Organizer.accdb";
	static Connection conn;
	static java.sql.Statement s;
	static ResultSet rs;
	
	// DATABASE
		public void bdImportKontakty(ArrayList<Contact> kontakty) {
			try {
				conn = DriverManager.getConnection("jdbc:ucanaccess://" + database);
				s = conn.createStatement();
				rs = s.executeQuery("SELECT * FROM Kontakty");

				String ret;
				while (rs.next()) {
					Contact temp = new Contact(rs.getString("name"));
					ret = rs.getString("id") + ". " + rs.getString("name") + " ";
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
		
		public ArrayList<Event> xmlImport(String file, ArrayList<Event> eventy) // nie wiem czemu nie dzia³a przez referencje
		{
			try {
				XMLDecoder	d = new XMLDecoder( new BufferedInputStream( new FileInputStream(file)));
				eventy = (ArrayList<Event>) d.readObject();
				d.close();
			} catch (FileNotFoundException e) { e.printStackTrace(); }
			return eventy;
		}
		
}
