package dane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Alarm {
	
	LocalTime before;
	DateTimeFormatter timeFormat; // = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm");
	//DŸwiêki -- private Sound sound = def; ??? //

	public Alarm(LocalTime before) { this.before = before; }
	
	public String toString()
	{
		String ret = "";
		if(before.getHour() == 0)
		{
			timeFormat = DateTimeFormatter.ofPattern("m");
			ret += before.format(timeFormat) + " minut przed"; 
		}
		else
		{
			timeFormat = DateTimeFormatter.ofPattern("H");
			ret += before.format(timeFormat) + " godzin przed";
		}
			
		return ret;
	}

}
