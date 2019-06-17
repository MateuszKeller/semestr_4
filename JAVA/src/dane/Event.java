package dane;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event implements java.io.Serializable {

	String tittle;
	LocalDateTime start;
	LocalDateTime end;
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	String note = "";
	String place = "";
	Alarm notification = null;
	Contact person = null;

	public Event() {}
	public Event(String tittle, LocalDateTime start, LocalDateTime end, String note, String place) 
	{
		this.tittle = tittle;
		this.start = start;
		this.end = end;
		this.note = note;
		this.place = place;
	}
	
	public Event(String tittle, LocalDateTime start, LocalDateTime end) 
	{
		this.tittle = tittle;
		this.start = start;
		this.end = end;
		note = null; 
		place = null;
		notification = null;
	}
	
	
	public Event(String tittle, LocalDateTime start, LocalDateTime end, String note, String place, LocalDateTime notification)
	{
		this.tittle = tittle;
		this.start = start;
		this.end = end;
		this.note = note;
		this.place = place;
		this.notification = new Alarm(notification);
		
	}
	
	public String toString()
	{	
		String ret = "\n\nWydarzenie " + tittle + "\n" +
				start.format(dateFormat) + " - " + end.format(dateFormat);
		
		if(notification != null)	ret += "\n" + notification.toString();
		if(place != "")	ret+= "\n" + place;
		if(note != "")	ret+= "\n" + note;
		
		return ret; 
	}

	// GET/SET-ers
	public String getTittle() { return tittle; }
	public void setTittle(String tittle) { this.tittle = tittle; }

	public LocalDateTime getStart() { return start;	}
	public void setStart(LocalDateTime start) { this.start = start;	}

	public LocalDateTime getEnd() { return end;	}
	public void setEnd(LocalDateTime end) { this.end = end; }

	public String getNote() { return note; }
	public void setNote(String note) { this.note = note; }

	public String getPlace() { return place; }
	public void setPlace(String place) { this.place = place; }

	public Alarm getNotification() { return notification; }
//	public void setNotification(Alarm notification) { this.notification = notification; }
	public void setNotification(LocalDateTime date) {this.notification = new Alarm(date);}

	public Contact getPerson() { return person; }
	public void setPerson(Contact person) { this.person = person; }
	
	public void playAlarmSound() { notification.playSound(); }
	public void stopAlarmSound() { notification.stopSound(); }
		
}
