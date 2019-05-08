import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class Event {

	private String Tittle;
	private LocalDateTime Start;
	private LocalDateTime End;
	private DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	private String Note;
	private String Place;
	private Alarm Notification = null;
	
	
	
	
	public Event(String T, LocalDateTime S, LocalDateTime E, String N, String P) // konstruktor bez przypomnienia
	{
		Tittle = T;
		Start = S;
		End = E;
		Note = N;
		Place = P;
	}
	
	public Event(String T, LocalDateTime S, LocalDateTime E, String N, String P, LocalTime B) // konstruktor z przypomnieniem
	{
		Tittle = T;
		Start = S;
		End = E;
		Note = N;
		Place = P;
		Notification = new Alarm(B);
		
	}
	
	public String toString()
	{	
		String RET = "Wydarzenie " + Tittle + "\n" +
				Start.format(DateFormat) + " - " + End.format(DateFormat) + "\n ";
		
		if(Notification != null)
			RET += Notification.toString();
			
		return RET + "\n " + Place + "\n " + Note; 
	}


		
}
